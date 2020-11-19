package servidor.controladores;

import datos.RepoLicitaciones;
import datos.RepoOperacionesEgreso;
import datos.RepositorioCategorizacion;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.entidades.Empresa;
import dominio.entidades.EntidadJuridica;
import dominio.entidades.Organizacion;
import dominio.operaciones.*;
import dominio.operaciones.medioDePago.*;
import dominio.utils.RestClientML;
import servicio.abOperaciones.ServicioABOperaciones;
import servicio.ServiceMercadoLibre;

import servidor.Router;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;

import java.util.*;

import java.util.stream.Collectors;


public class EgresoController extends Controller{

    ServicioABOperaciones servicioOperaciones = new ServicioABOperaciones();

    ServiceMercadoLibre serviceMeli = ServiceMercadoLibre.INSTANCE;


    private String mensajeError;

    public ModelAndView mostrarEgresos(Request req, Response res, EntityManager entityManager) {
        String href = "/egresos";

        RepositorioCategorizacion repositorioCategorizacion = new RepositorioCategorizacion(entityManager);

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", req.session().attribute("user"));

        CuentaUsuario usuario = req.session().attribute("user");

        Organizacion org = usuario.getOrganizacion();


        ArrayList<OperacionEgreso> egresosPaginados = new ArrayList<>();

        ArrayList<OperacionEgreso> egresos = servicioOperaciones.listarOperaciones(org);


        int egresosPorPagina = 3;

        String pagina = req.queryParams("pagina");
        String filtro = req.queryParams("filtro");
        String mensajeE = Objects.toString(req.queryParams("error"),"");

        if(filtro != null){
            String[] nombreCategoriaCriterio= filtro.split("_");

            try{
                egresos = repositorioCategorizacion.filtrarEgresosDeLaCategoria(nombreCategoriaCriterio[1],nombreCategoriaCriterio[0], org);
                parameters.put("infoFiltroActual","Filtrado por " + nombreCategoriaCriterio[0] + " - " + nombreCategoriaCriterio[1]);
            }catch (NullPointerException e){
                egresos = null;
            }catch (ArrayIndexOutOfBoundsException e){
                //egresos = servicioOperaciones.listarOperaciones();
            }

            href = href.concat("?filtro=" + filtro);
            parameters.put("filtroPaginado","&filtro="+filtro);
        }

        if(mensajeE.length() > 0){
            if(href.equals("/egresos"))
                href = href.concat("?error=" + mensajeE);
            else
                href = href.concat("&error=" + mensajeE);
        }

        if(pagina == null){
            if(egresos.size() > egresosPorPagina){ // 3 egresos por pagina
                if(href.equals("/egresos"))
                    href = href.concat("?pagina=1");
                else
                    href = href.concat("&pagina=1");
                res.redirect(href); // redirecciona a la pagina 1
                return null;
            }

            parameters.put("egresos",egresos);
            parameters.put("user", req.session().attribute("user"));
        }
        else{
            int numeroPagina = Integer.parseInt(pagina);
            int indiceInicial = Math.min((numeroPagina - 1) * egresosPorPagina,egresos.size());
            int indiceFinal = Math.min(numeroPagina * egresosPorPagina,egresos.size());
            List<OperacionEgreso> egresosSubLista = egresos.subList(indiceInicial,indiceFinal);

            parameters.put("egresos",egresosSubLista);

            int cantidadPaginas = (int) Math.ceil((double)egresos.size()/egresosPorPagina);
            ArrayList<Integer> listaCantidadPaginas = new ArrayList<>();
            for(int i = 1;i<=cantidadPaginas; i++){
                listaCantidadPaginas.add(i);
            }
            parameters.put("cantidad_paginas",listaCantidadPaginas);
            if(numeroPagina > 1)
                parameters.put("pagina_anterior",numeroPagina - 1);
            if(numeroPagina * egresosPorPagina < egresos.size())
                parameters.put("pagina_siguiente",numeroPagina + 1);

            parameters.put("user", req.session().attribute("user"));
        }

        switch (mensajeE){
            case "licitacionFinalizada":
                parameters.put("error","No puede agregarse el presupuesto debido a que la licitacion a finalizado");
                break;
            case "presupuestoNoAgregado":
                parameters.put("error","Los items del presupuesto no coinciden con los items del egreso");
            case "egresoNoBorrado":
                parameters.put("error","El egreso no se pudo borrar debido a que tiene presupuestos asociados");
            default:
                break;
        }

        parameters.put("criteriosDeCategorizacion",repositorioCategorizacion.getCriteriosDeCategorizacion());
        return new ModelAndView(parameters,"egresos2.hbs");
    }


    public ModelAndView showEgreso(Request request, Response response) throws  Exception{
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", request.session().attribute("user"));

        parameters.put("medioDePagoMeli", serviceMeli.getMediosDePago());

        return new ModelAndView(parameters, "egreso.hbs");
    }


    public Object deleteEgreso(Request request, Response response, EntityManager entityManager){
        String identificador = request.params("identificador");
        RepoLicitaciones repoLicitaciones = new RepoLicitaciones(entityManager);

        if(repoLicitaciones.buscarLicitacionPorOperacionEgreso(identificador)!=null){
            response.status(406);
            return "No se puede eliminar";
        }

        CuentaUsuario usuario = request.session().attribute("user");

        RepoOperacionesEgreso repoOperacionesEgreso = new RepoOperacionesEgreso(entityManager);
        repoOperacionesEgreso.eliminarOperacionEgresoPorIdentificador(identificador, usuario.getUserName(), Router.getDatastore());
        response.status(200);

        return "Eliminacion existosa";
    }


    public ModelAndView crearEgreso(Request req, Response res, EntityManager entityManager) throws Exception {

        try {
            MedioDePago medioDePagoFinal;
            String monto;
            String nombre;
            String numero;

            if(!req.queryParams("tarjeta-credito-num").isEmpty()){
                nombre = req.queryParams("tarjeta-credito-nombre-apellido");
                String cuotas = req.queryParams("tarjeta-credito-cantidad");
                numero = req.queryParams("tarjeta-credito-num");
                String entidadDePago = req.queryParams("banco-elegido");

                medioDePagoFinal = new TarjetaDeCredito(Integer.valueOf(cuotas), nombre, numero, entidadDePago);
            }
            else{
                if(!req.queryParams("tarjeta-debito-num").isEmpty()){
                    nombre = req.queryParams("tarjeta-debito-nombre-apellido");
                    numero = req.queryParams("tarjeta-debito-num");
                    String entidadDePago = req.queryParams("banco-elegido");

                    medioDePagoFinal = new TarjetaDeDebito(nombre, numero, entidadDePago);
                }
                else {
                    if(!req.queryParams("efectivo-monto").isEmpty()){
                        monto = req.queryParams("efectivo-monto");
                        String puntoDePago = req.queryParams("efectivo-punto-de-pago");
                        String entidadDePago = req.queryParams("banco-elegido");
                        medioDePagoFinal = new Efectivo(Double.valueOf(monto), puntoDePago, "Efectivo", entidadDePago);
                    }
                    else{
                        if(!req.queryParams("dinero-cuenta-monto").isEmpty()){
                            monto = req.queryParams("dinero-cuenta-monto");
                            nombre = req.queryParams("dinero-cuenta-nombre-apellido");
                            medioDePagoFinal = new DineroEnCuenta(Double.valueOf(monto), nombre);
                        }
                        else{
                            throw new Exception("no hay medio de pago válido");
                        }
                    }
                }
            }



            String documentoComercialNumero = req.queryParams("documento-num");

            ETipoDoc Etipo =  tipoDeDocumento.get(req.queryParams("tipo-documento"));


            DocumentoComercial documento = new DocumentoComercial(Etipo, Integer.valueOf(documentoComercialNumero));


            String fecha = req.queryParams("operacion-egreso-date");

            String cantItems = req.queryParams("cantidad-items");

            ArrayList<Item> items = new ArrayList<>();
            ETipoItem itemTipoEnum;
            String itemNum;

            for (int i = 0; i < Integer.valueOf(cantItems); i++) {

                itemNum = String.valueOf(i);

                String itemValor = req.queryParams("item_monto" + itemNum);
                String itemDescripcion = req.queryParams("item_descripcion" + itemNum);
                itemTipoEnum =  tipoDeItem.get(req.queryParams("item_tipo" + itemNum));


                items.add(new Item(Integer.valueOf(itemValor), itemTipoEnum, itemDescripcion));

            }

            CuentaUsuario usuario = req.session().attribute("user");

            Organizacion org = usuario.getOrganizacion();


            String EONombre = req.queryParams("query_EO_nombre");

            Empresa entidadJuridica = org.buscarEntidad(EONombre);

            String EOCuil = entidadJuridica.getCuit();
            String EODireccion = entidadJuridica.getDireccionPostal();

            EntidadOperacion entidadOrigen = new EntidadOperacion(EONombre, EOCuil, EODireccion);


            String EDNombre = req.queryParams("query_ED_nombre");
            String EDCuil = req.queryParams("query_ED_cuil");
            String EDDireccion = req.queryParams("query_ED_direccion");

            EntidadOperacion entidadDestino = new EntidadOperacion(EDNombre, EDCuil, EDDireccion);

            String presupuestosNecesarios = req.queryParams("presupuestos-necesarios-num");

            Date parsed=new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

            OperacionEgreso egreso = new OperacionEgreso(items,medioDePagoFinal , documento, parsed, entidadOrigen, entidadDestino, Integer.valueOf(presupuestosNecesarios));
            RepoOperacionesEgreso repoOperacionesEgreso = new RepoOperacionesEgreso(entityManager);
            repoOperacionesEgreso.agregarOperacionEgreso(egreso, usuario.getUserName(), Router.getDatastore());


        }
        catch(NullPointerException e){
            mensajeError = "Null error: " + e.getMessage();
            return new ModelAndView(this, "fallaCreacionEgreso.hbs"); }
        catch (Exception e) {
            mensajeError = "Error desconocido: " + e.getMessage();
            return new ModelAndView(this, "fallaCreacionEgreso.hbs");
        }

        /* Si no se pone el redirect, igual va a ir a esa uri por que esta en la action de la form. Pero el metodo va a ser post, entonces cada vez que se recargue la pagina se vuelve a agregar la prenda. El redirect es un get de la uri.*/
        res.redirect("/egresos");

        return null;


    }

    public ModelAndView showModificarEgreso(Request req, Response res) throws Exception{
        Map<String, Object> parameters = new HashMap<>();
        OperacionEgreso egreso = servicioOperaciones.buscarEgreso(req.params("id"));
        Date fecha = egreso.getFecha();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = formateador.format(fecha);

        parameters.put("user", req.session().attribute("user"));
        parameters.put("egreso", egreso);
        parameters.put("fecha", fechaFormateada);

        parameters.put("medioDePagoMeli", serviceMeli.getMediosDePago());

        return new ModelAndView(parameters, "egreso.hbs");
    }

    public ModelAndView modificarEgreso(Request req, Response res){

        try {

            MedioDePago medioDePagoFinal;
            String monto;
            String nombre;
            String numero;

            if(!req.queryParams("tarjeta-credito-num").isEmpty()){
                nombre = req.queryParams("tarjeta-credito-nombre-apellido");
                String cuotas = req.queryParams("tarjeta-credito-cantidad");
                numero = req.queryParams("tarjeta-credito-num");
                String entidadDePago = req.queryParams("banco-elegido");

                medioDePagoFinal = new TarjetaDeCredito(Integer.valueOf(cuotas), nombre, numero, entidadDePago);
            }
            else{
                if(!req.queryParams("tarjeta-debito-num").isEmpty()){
                    nombre = req.queryParams("tarjeta-debito-nombre-apellido");
                    numero = req.queryParams("tarjeta-debito-num");
                    String entidadDePago = req.queryParams("banco-elegido");

                    medioDePagoFinal = new TarjetaDeDebito(nombre, numero, entidadDePago);
                }
                else {
                    if(!req.queryParams("efectivo-monto").isEmpty()){
                        monto = req.queryParams("efectivo-monto");
                        String puntoDePago = req.queryParams("efectivo-punto-de-pago");
                        String entidadDePago = req.queryParams("banco-elegido");

                        medioDePagoFinal = new Efectivo(Double.valueOf(monto), puntoDePago, "Efectivo",entidadDePago);
                    }
                    else{
                        if(!req.queryParams("dinero-cuenta-monto").isEmpty()){
                            monto = req.queryParams("dinero-cuenta-monto");
                            nombre = req.queryParams("dinero-cuenta-nombre-apellido");
                            medioDePagoFinal = new DineroEnCuenta(Double.valueOf(monto), nombre);
                        }
                        else{
                            throw new Exception("no hay medio de pago válido");
                        }
                    }
                }
            }

            String documentoComercialNumero = req.queryParams("documento-num");

            String tipoDocumento = req.queryParams("tipo-documento");

            ETipoDoc Etipo =  tipoDeDocumento.get(req.queryParams("tipo-documento"));


            DocumentoComercial documento = new DocumentoComercial(Etipo, Integer.valueOf(documentoComercialNumero));


            String fecha = req.queryParams("operacion-egreso-date");

            String EONombre = req.queryParams("query_EO_nombre");
            String EOCuil = req.queryParams("query_EO_cuil");
            String EODireccion = req.queryParams("query_EO_direccion");

            EntidadOperacion entidadOrigen = new EntidadOperacion(EONombre, EOCuil, EODireccion);

            // TODO, debe verificarse que la entidad origen sea perteneciente a la org del usuario

            String EDNombre = req.queryParams("query_ED_nombre");
            String EDCuil = req.queryParams("query_ED_cuil");
            String EDDireccion = req.queryParams("query_ED_direccion");

            EntidadOperacion entidadDestino = new EntidadOperacion(EDNombre, EDCuil, EDDireccion);

            OperacionEgreso egreso = servicioOperaciones.buscarEgreso(req.params("id"));

            Date parsed=new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

            CuentaUsuario usuario = req.session().attribute("user");

            // Modificar Egreso
            new RepoOperacionesEgreso(null).modificarEgreso(egreso, medioDePagoFinal , documento, parsed, entidadOrigen, entidadDestino, usuario.getUserName(), Router.getDatastore());
        }
        catch(NullPointerException e){
            mensajeError = "Null error: " + e.getMessage();
            return new ModelAndView(this, "fallaCreacionEgreso.hbs"); }
        catch (Exception e) {
            mensajeError = "Error desconocido: " + e.getMessage() + req.queryMap();
            return new ModelAndView(this, "fallaCreacionEgreso.hbs");
        }

        /* Si no se pone el redirect, igual va a ir a esa uri por que esta en la action de la form. Pero el metodo va a ser post, entonces cada vez que se recargue la pagina se vuelve a agregar la prenda. El redirect es un get de la uri.*/
        res.redirect("/egresos");
        return null;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    private HashMap<String, ETipoItem> tipoDeItem = new HashMap<String, ETipoItem>() {{
        put("articulo", ETipoItem.ARTICULO);
        put("servicio", ETipoItem.SERVICIO);
    }};

    private HashMap<String, ETipoDoc> tipoDeDocumento = new HashMap<String, ETipoDoc>() {{
        put("ticket", ETipoDoc.TICKET);
        put("factura", ETipoDoc.FACTURA);
        put("TICKET", ETipoDoc.TICKET);
        put("FACTURA", ETipoDoc.FACTURA);
    }};

}
