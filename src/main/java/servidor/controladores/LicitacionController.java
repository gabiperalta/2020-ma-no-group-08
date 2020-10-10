package servidor.controladores;

import com.google.gson.*;
import com.sun.tools.javac.file.SymbolArchive;
import dominio.categorizacion.CriterioDeCategorizacion;
import dominio.categorizacion.RepositorioCategorizacion;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.entidades.Organizacion;
import dominio.licitacion.Licitacion;
import dominio.licitacion.Presupuesto;
import dominio.licitacion.RepoLicitaciones;
import dominio.licitacion.criterioSeleccion.CriterioMenorPrecio;
import dominio.notificador_suscriptores.NotificadorSuscriptores;
import dominio.operaciones.*;
import dominio.operaciones.medioDePago.Efectivo;
import servicio.ab_licitaciones.ServicioABLicitaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark.*;

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
        //initRepoPrueba();
    }

    public Gson getGson(){
        return gson;
    }

    public Object agregarPresupuesto(Request request, Response response){
        JsonObject jsonObjectArchivo = null;
        JsonArray jsonArrayArchivo = null;

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
        OperacionEgreso operacionEgresoEncontrada = RepoOperacionesEgreso.getInstance().buscarOperacionEgresoPorIdenticadorOperacionEgreso(egresoId);

        ArrayList<Presupuesto> presupuestos = new ArrayList<>();

        if(jsonArrayArchivo != null){
            jsonArrayArchivo.forEach(jsonElement -> presupuestos.add(jsonAPresupuesto(jsonElement.getAsJsonObject())));
        }
        else if (jsonObjectArchivo != null){
            presupuestos.add(jsonAPresupuesto(jsonObjectArchivo));
        }

        Licitacion licitacion = RepoLicitaciones.getInstance().buscarLicitacionPorOperacionEgreso(egresoId);
        ServicioABLicitaciones servicioABLicitaciones = new ServicioABLicitaciones();
        if(licitacion == null){
            licitacion = servicioABLicitaciones.altaLicitacion(operacionEgresoEncontrada, NotificadorSuscriptores.getInstance());
            licitacion.agregarCriterioSeleccionDeProveedor(new CriterioMenorPrecio());
            RepoLicitaciones.getInstance().agregarLicitacion(licitacion);
        }
        Licitacion finalLicitacion = licitacion;

        if(!licitacion.estaFinalizada()) {
            presupuestos.forEach(presupuesto -> servicioABLicitaciones.altaPresupuesto(finalLicitacion, presupuesto));
            response.redirect("/egresos");
        }
        else{
            response.redirect("/egresos?error=licitacionFinalizada");
        }

        response.status(200);
        response.redirect("/egresos");

        return null;
    }

    public Object realizarLicitacion(Request request,Response response){
        String licitacionId = request.queryParams("licitacion_id");
        Licitacion licitacionEncontrada = RepoLicitaciones.getInstance().buscarLicitacionPorIdentificador(licitacionId);
        if(licitacionEncontrada == null){
            response.status(404);
            return "Licitacion inexistente";
        }
        licitacionEncontrada.licitar();
        return licitacionEncontrada.getIdentificador();
    }

    public Object resultadoLicitacion(Request request,Response response){
        String licitacionId = request.params("licitacion_id");
        Licitacion licitacionEncontrada = RepoLicitaciones.getInstance().buscarLicitacionPorIdentificador(licitacionId);

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

    public ModelAndView mostrarPresupuestos(Request request,Response response){
        int presupuestosPorPagina = 3;
        String href = "/presupuestos";
        Map<String, Object> map = new HashMap<>();

        String pagina = request.queryParams("pagina");
        String filtro = request.queryParams("filtro");
        ArrayList<Licitacion> licitaciones = RepoLicitaciones.getInstance().getLicitaciones();
        List<Presupuesto> presupuestos = licitaciones.stream().flatMap(licitacion -> licitacion.getPresupuestos().stream()).collect(Collectors.toList());

        if(filtro != null){
            String[] nombreCategoriaCriterio= filtro.split("_");
            CuentaUsuario usuario = request.session().attribute("user");

            try{
                presupuestos = RepositorioCategorizacion.getInstance().filtrarPresupuestosDeLaCategoria(nombreCategoriaCriterio[1],nombreCategoriaCriterio[0], usuario.getOrganizacion()).stream().map(entidadCategorizable -> (Presupuesto)entidadCategorizable.getOperacion()).collect(Collectors.toList());
                map.put("infoFiltroActual","Filtrado por " + nombreCategoriaCriterio[0] + " - " + nombreCategoriaCriterio[1]);
            }catch (NullPointerException e){
                presupuestos = null;
            }catch (ArrayIndexOutOfBoundsException ignored){

            }

            href = href.concat("?filtro=" + filtro);
            map.put("filtroPaginado","&filtro="+filtro);
        }

        if(pagina == null){
            if(presupuestos.size() > presupuestosPorPagina){
                if(href.equals("/presupuestos"))
                    href = href.concat("?pagina=1");
                else
                    href = href.concat("&pagina=1");
                response.redirect(href); // redirecciona a la pagina 1
                return null;
            }
            map.put("presupuestos",presupuestos);
        }
        else{
            int numeroPagina = Integer.parseInt(pagina);
            int indiceInicial = Math.min((numeroPagina - 1) * presupuestosPorPagina,presupuestos.size());
            int indiceFinal = Math.min(numeroPagina * presupuestosPorPagina,presupuestos.size());
            List<Presupuesto> presupuestosSubLista = presupuestos.subList(indiceInicial,indiceFinal);

            map.put("presupuestos",presupuestosSubLista);
            int cantidadPaginas = (int) Math.ceil((double)presupuestos.size()/presupuestosPorPagina);
            ArrayList<Integer> listaCantidadPaginas = new ArrayList<>();
            for(int i = 1;i<=cantidadPaginas; i++){
                listaCantidadPaginas.add(i);
            }
            map.put("cantidad_paginas",listaCantidadPaginas);
            if(numeroPagina > 1)
                map.put("pagina_anterior",numeroPagina - 1);
            if(numeroPagina * presupuestosPorPagina < presupuestos.size())
                map.put("pagina_siguiente",numeroPagina + 1);

        }
        map.put("user", request.session().attribute("user"));

        map.put("criteriosDeCategorizacion",RepositorioCategorizacion.getInstance().getCriteriosDeCategorizacion());
        return new ModelAndView(map,"presupuestos.hbs");
    }

    public Object obtenerLicitacionPorEgreso(Request request,Response response){
        Licitacion licitacionEncontrada = RepoLicitaciones.getInstance().buscarLicitacionPorOperacionEgreso(request.queryParams("idEgreso"));
        if(licitacionEncontrada == null)
            return "";
        else
            return licitacionEncontrada.getIdentificador();
    }

    public static Presupuesto jsonAPresupuesto(JsonObject jsonPresupuesto){
        EntidadOperacion entidadOperacion = gson.fromJson(jsonPresupuesto.get("entidadOperacion"),EntidadOperacion.class);
        JsonArray jsonArrayItems = jsonPresupuesto.get("item").getAsJsonArray();
        ArrayList<Item> items = new ArrayList<>();
        jsonArrayItems.forEach(jsonElement -> items.add(gson.fromJson(jsonElement,Item.class)));
        return new Presupuesto(entidadOperacion,items);
    }

    public static void initRepoPrueba(){
        OperacionEgresoBuilder builderCompra;
        OperacionEgreso compra;
        Licitacion licitacion1;
        Licitacion licitacion2;
        Licitacion licitacion3;
        Presupuesto presup1;
        Presupuesto presup2;
        Presupuesto presup3;
        Presupuesto presup4;
        EntidadOperacion proveedor1;
        EntidadOperacion proveedor2;

        Efectivo pesos = new Efectivo(200000,"Rapipago", "Efectivo");
        DocumentoComercial documento = new DocumentoComercial(ETipoDoc.FACTURA, 2000);
        Date fecha = new Date();
        EntidadOperacion origen = new EntidadOperacion("Operacion compra 1","20-40678950-4","Av.Libertador 800");
        EntidadOperacion destino = new EntidadOperacion("Operacion compra 1", "20-40678950-4", "Av.Corrientes 550");

        ArrayList<Item> listaItemsCompra = new ArrayList<Item>();
        listaItemsCompra.add(new Item(50, ETipoItem.ARTICULO, "Item1"));
        listaItemsCompra.add(new Item(100, ETipoItem.ARTICULO, "Item2"));

        builderCompra = new OperacionEgresoBuilder();

        compra = builderCompra.agregarItems(listaItemsCompra)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino)
                .agregarPresupuestosNecesarios(2)
                .build();

        licitacion1 = new Licitacion(compra, NotificadorSuscriptores.getInstance());
        licitacion2 = new Licitacion(compra, NotificadorSuscriptores.getInstance());
        licitacion3 = new Licitacion(compra, NotificadorSuscriptores.getInstance());

        licitacion1.agregarCriterioSeleccionDeProveedor(new CriterioMenorPrecio());
        licitacion2.agregarCriterioSeleccionDeProveedor(new CriterioMenorPrecio());
        licitacion3.agregarCriterioSeleccionDeProveedor(new CriterioMenorPrecio());

        ArrayList<Item> listaItems1 = new ArrayList<>();
        listaItems1.add(new Item(50, ETipoItem.ARTICULO, "Item1"));
        listaItems1.add(new Item(100, ETipoItem.ARTICULO, "Item2"));

        ArrayList<Item> listaItems2 = new ArrayList<>();
        listaItems2.add(new Item(200, ETipoItem.ARTICULO, "Item3"));
        listaItems2.add(new Item(150, ETipoItem.ARTICULO, "Item4"));

        ArrayList<Item> listaItems3 = new ArrayList<>();
        listaItems3.add(new Item(500, ETipoItem.ARTICULO, "Item1"));
        listaItems3.add(new Item(1020, ETipoItem.ARTICULO, "Item2"));

        ArrayList<Item> listaItems4 = new ArrayList<>();
        listaItems4.add(new Item(10, ETipoItem.ARTICULO, "Item1"));
        listaItems4.add(new Item(30, ETipoItem.ARTICULO, "Item2"));

        proveedor1 = new EntidadOperacion("Operacion compra 1","20-40678950-4","Av.Libertador 800");
        proveedor2 = new EntidadOperacion("Operacion compra 2","20-40678950-3","Av.Libertador 100");

        presup1 = new Presupuesto(proveedor1,listaItems1);
        presup2 = new Presupuesto(proveedor2,listaItems2);
        presup3 = new Presupuesto(proveedor1,listaItems3);
        presup4 = new Presupuesto(proveedor2,listaItems4);

        licitacion1.agregarPresupuesto(presup1);
        licitacion1.agregarPresupuesto(presup3);
        licitacion2.agregarPresupuesto(presup1);
        licitacion2.agregarPresupuesto(presup3);
        licitacion3.agregarPresupuesto(presup1);
        licitacion3.agregarPresupuesto(presup3);

        try{
            RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(compra);
            RepoLicitaciones.getInstance().agregarLicitacion(licitacion1);
            RepoLicitaciones.getInstance().agregarLicitacion(licitacion2);
            RepoLicitaciones.getInstance().agregarLicitacion(licitacion3);
            //System.out.println(compra.getIdentificador());
            //System.out.println("Estado repo egreso: " + RepoOperacionesEgreso.getInstance().getOperacionesEgreso().size());
        }
        catch (Exception e){

        }
    }
}