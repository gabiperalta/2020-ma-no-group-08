package servidor.controladores;

import com.google.gson.*;
//import com.sun.org.apache.regexp.internal.RE;
import datos.RepoOperacionesEgreso;
import datos.RepositorioCategorizacion;
import dominio.categorizacion.EntidadCategorizable;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.licitacion.Licitacion;
import dominio.licitacion.Presupuesto;
import datos.RepoLicitaciones;
import dominio.licitacion.criterioSeleccion.CriterioMenorPrecio;
import dominio.notificador_suscriptores.NotificadorSuscriptores;
import dominio.operaciones.*;
import dominio.operaciones.medioDePago.Efectivo;
import servicio.ab_licitaciones.ServicioABLicitaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class LicitacionController{

    static Gson gson;

    public LicitacionController(){
        gson = new Gson();
    }

    public Gson getGson(){
        return gson;
    }

    public Object agregarPresupuesto(Request request, Response response, EntityManager entityManager){
        JsonObject jsonObjectArchivo = null;
        JsonArray jsonArrayArchivo = null;

        RepoLicitaciones repoLicitaciones = new RepoLicitaciones(entityManager);

        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try (InputStream is = request.raw().getPart("archivojson").getInputStream()) {
            String textoArchivo = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().collect(Collectors.joining(""));

            if(textoArchivo.startsWith("[")) {
                jsonArrayArchivo = JsonParser.parseString(textoArchivo).getAsJsonArray();
            }
            else if(textoArchivo.startsWith("{")){
                jsonObjectArchivo = JsonParser.parseString(textoArchivo).getAsJsonObject();
            }
            else{
                System.out.println("Archivo erroneo o no respeta la sintaxis JSON");
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        String egresoId = request.queryParams("identificador_egreso");
        RepoOperacionesEgreso repoOperacionesEgreso = new RepoOperacionesEgreso(entityManager);
        OperacionEgreso operacionEgresoEncontrada = repoOperacionesEgreso.buscarOperacionEgresoPorIdenticadorOperacionEgreso(egresoId);

        ArrayList<Presupuesto> presupuestos = new ArrayList<>();

        if(jsonArrayArchivo != null){
            jsonArrayArchivo.forEach(jsonElement -> presupuestos.add(jsonAPresupuesto(jsonElement.getAsJsonObject())));
        }
        else if (jsonObjectArchivo != null){
            presupuestos.add(jsonAPresupuesto(jsonObjectArchivo));
        }

        Licitacion licitacion = repoLicitaciones.buscarLicitacionPorOperacionEgreso(egresoId);
        ServicioABLicitaciones servicioABLicitaciones = new ServicioABLicitaciones(entityManager);

        if(licitacion == null){
            licitacion = servicioABLicitaciones.altaLicitacion(operacionEgresoEncontrada, NotificadorSuscriptores.getInstance());
            licitacion.agregarCriterioSeleccionDeProveedor(new CriterioMenorPrecio());
            repoLicitaciones.agregarLicitacion(licitacion);
        }
        Licitacion finalLicitacion = licitacion;

        int cantidadPresupuestosAnterior = licitacion.getPresupuestos().size();
        String urlEgresos = request.queryParams("url_egresos");
        if(urlEgresos.contains("?"))
            urlEgresos = urlEgresos.concat("&");
        else
            urlEgresos = urlEgresos.concat("?");

        if(!licitacion.estaFinalizada()) {

            CuentaUsuario usuario = request.session().attribute("user");

            presupuestos.forEach(presupuesto -> servicioABLicitaciones.altaPresupuesto(finalLicitacion, presupuesto, usuario.getUserName()));
            //response.redirect("/egresos");
            //if(cantidadPresupuestosAnterior == licitacion.getPresupuestos().size())
            //    response.redirect("/egresos?error=presupuestoNoAgregado"); // no se agrego el presupuesto por no coincidir con los items del egreso
            if(cantidadPresupuestosAnterior == licitacion.getPresupuestos().size())
                response.redirect(urlEgresos + "error=presupuestoNoAgregado"); // no se agrego el presupuesto por no coincidir con los items del egreso
            else
                response.redirect(urlEgresos);
        }
        else{
            response.redirect(urlEgresos + "error=licitacionFinalizada");
        }

        response.status(200);

        return null;
    }

    public Object realizarLicitacion(Request request,Response response, EntityManager entityManager){
        String licitacionId = request.queryParams("licitacion_id");
        CuentaUsuario usuario = request.session().attribute("user");
        ServicioABLicitaciones servicioABLicitaciones = new ServicioABLicitaciones(entityManager);
        ArrayList<Licitacion> licitaciones = servicioABLicitaciones.listarLicitacionesOrg(usuario.getOrganizacion());

        Licitacion licitacionEncontrada = null;

        if(licitaciones.stream().anyMatch(licitacion -> licitacion.getIdentificadorConEtiqueta().equals(licitacionId))){
            licitacionEncontrada = licitaciones.stream().filter(licitacion -> licitacion.getIdentificadorConEtiqueta().equals(licitacionId)).findFirst().get();
        }

        if(licitacionEncontrada == null){
            response.status(404);
            return "Licitacion inexistente";
        }

        try{
            //servicioABLicitaciones.licitar(licitacionEncontrada,usuario.getUserName());
            licitacionEncontrada.licitar();
        } catch (Exception e) {
            response.status(401);
            return "El usuario no tiene permiso para licitar";
        }

        return licitacionEncontrada.getIdentificadorConEtiqueta();
    }

    public Object resultadoLicitacion(Request request,Response response, EntityManager entityManager){
        RepoLicitaciones repoLicitaciones = new RepoLicitaciones(entityManager);

        String licitacionId = request.params("licitacion_id");
        Licitacion licitacionEncontrada = repoLicitaciones.buscarLicitacionPorIdentificador(licitacionId);

        if(licitacionEncontrada == null) {
            response.status(404);
            return "Licitacion no encontrada";
        }
        String resultado;
        if(licitacionEncontrada.estaFinalizada())
            resultado = licitacionEncontrada.mensajeTexto();
        else
            resultado = "Aun no se ejecuto la licitacion";

        response.type("application/json");
        response.status(200);

        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("resultado",resultado);

        return jsonObject;
    }

    public ModelAndView mostrarPresupuestos(Request request, Response response, EntityManager entityManager){
        RepositorioCategorizacion repositorioCategorizacion = new RepositorioCategorizacion(entityManager);
        RepoLicitaciones repoLicitaciones = new RepoLicitaciones(entityManager);

        int presupuestosPorPagina = 3;
        long cantidadPresupuestos;
        String href = "/presupuestos";
        Map<String, Object> map = new HashMap<>();

        String pagina = request.queryParams("pagina");
        String filtro = request.queryParams("filtro");
        CuentaUsuario usuario = request.session().attribute("user");
        ServicioABLicitaciones servicioABLicitaciones = new ServicioABLicitaciones(entityManager);
        cantidadPresupuestos = repoLicitaciones.getCantidadPresupuestosPorOrg(usuario.getOrganizacion());

        List<HashMap<String,Object>> presupuestosCompuesto = null;

        if(filtro != null){
            String[] nombreCategoriaCriterio= filtro.split("_");

            try{
                cantidadPresupuestos = repositorioCategorizacion.filtrarPresupuestosDeLaCategoriaCantidad(nombreCategoriaCriterio[1], nombreCategoriaCriterio[0], usuario.getOrganizacion());
                map.put("infoFiltroActual","Filtrado por " + nombreCategoriaCriterio[0] + " - " + nombreCategoriaCriterio[1]);
            }catch (NullPointerException e){
                cantidadPresupuestos = 0;
            }catch (ArrayIndexOutOfBoundsException ignored){
                response.redirect("/presupuestos");
                return null;
            }

            href = href.concat("?filtro=" + filtro);
            map.put("filtroPaginado","&filtro="+filtro);
        }

        if(pagina == null){
            if(cantidadPresupuestos > presupuestosPorPagina){
                if(href.equals("/presupuestos"))
                    href = href.concat("?pagina=1");
                else
                    href = href.concat("&pagina=1");
                response.redirect(href); // redirecciona a la pagina 1
                return null;
            }


            if(filtro != null){
                String[] nombreCategoriaCriterio= filtro.split("_");
                try{
                    presupuestosCompuesto = repositorioCategorizacion.filtrarPresupuestosDeLaCategoria(nombreCategoriaCriterio[1], nombreCategoriaCriterio[0], usuario.getOrganizacion(),presupuestosPorPagina,0);
                }catch (NullPointerException e){
                    presupuestosCompuesto = null;
                }catch (ArrayIndexOutOfBoundsException ignored){

                }
            }
            else{
                presupuestosCompuesto = repoLicitaciones.getPresupuestosPorOrg(usuario.getOrganizacion(),presupuestosPorPagina,0); // falta poner el limite y base
            }

        }
        else{
            int numeroPagina = Integer.parseInt(pagina);
            int indiceInicial = Math.min((numeroPagina - 1) * presupuestosPorPagina,(int)cantidadPresupuestos);

            if(filtro != null){
                String[] nombreCategoriaCriterio= filtro.split("_");
                try{
                    presupuestosCompuesto = repositorioCategorizacion.filtrarPresupuestosDeLaCategoria(nombreCategoriaCriterio[1], nombreCategoriaCriterio[0], usuario.getOrganizacion(),presupuestosPorPagina,indiceInicial);
                }catch (NullPointerException e){
                    presupuestosCompuesto = null;
                }catch (ArrayIndexOutOfBoundsException ignored){

                }
            }
            else{
                presupuestosCompuesto = repoLicitaciones.getPresupuestosPorOrg(usuario.getOrganizacion(),presupuestosPorPagina,indiceInicial);
            }

            int cantidadPaginas = (int) Math.ceil((double)cantidadPresupuestos/presupuestosPorPagina);
            ArrayList<Integer> listaCantidadPaginas = new ArrayList<>();
            for(int i = 1;i<=cantidadPaginas; i++){
                listaCantidadPaginas.add(i);
            }
            map.put("cantidad_paginas",listaCantidadPaginas);
            if(numeroPagina > 1)
                map.put("pagina_anterior",numeroPagina - 1);
            if(numeroPagina * presupuestosPorPagina < cantidadPresupuestos)
                map.put("pagina_siguiente",numeroPagina + 1);

        }
        map.put("presupuestos",presupuestosCompuesto);
        map.put("user", request.session().attribute("user"));

        map.put("criteriosDeCategorizacion",repositorioCategorizacion.getCriteriosDeCategorizacion());

        System.out.println("Size presupuestos: " + repoLicitaciones.getCantidadPresupuestos());
        return new ModelAndView(map,"presupuestos.hbs");
    }

    public Object obtenerLicitacionPorEgreso(Request request,Response response, EntityManager entityManager){
        RepoLicitaciones repoLicitaciones = new RepoLicitaciones(entityManager);

        Licitacion licitacionEncontrada = repoLicitaciones.buscarLicitacionPorOperacionEgreso(request.queryParams("idEgreso"));
        if(licitacionEncontrada == null)
            return "";
        else
            return licitacionEncontrada.getIdentificadorConEtiqueta();
    }

    public static Presupuesto jsonAPresupuesto(JsonObject jsonPresupuesto){
        EntidadOperacion entidadOperacion = gson.fromJson(jsonPresupuesto.get("entidadOperacion"),EntidadOperacion.class);
        JsonArray jsonArrayItems = jsonPresupuesto.get("item").getAsJsonArray();
        ArrayList<Item> items = new ArrayList<>();
        jsonArrayItems.forEach(jsonElement -> items.add(gson.fromJson(jsonElement,Item.class)));
        return new Presupuesto(entidadOperacion,items);
    }
}