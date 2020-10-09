package servidor.controladores;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.entidades.Organizacion;
import dominio.operaciones.*;
import dominio.operaciones.medioDePago.*;
import servicio.abOperaciones.ServicioABOperaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EgresoController extends Controller{

    ServicioABOperaciones servicioOperaciones = new ServicioABOperaciones();

    private String mensajeError;

    public ModelAndView mostrarEgresos(Request req, Response res) {

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", req.session().attribute("user"));

        CuentaUsuario usuario = req.session().attribute("user");

        Organizacion org = usuario.getOrganizacion();

        //parameters.put("egresos", servicioOperaciones.metodoQueHaceFedeParaFiltrarEgresoPorOrganizacion(org));

        ArrayList<OperacionEgreso> egresosPaginados = new ArrayList<>();

        ArrayList<OperacionEgreso> egresos = servicioOperaciones.listarOperaciones(org);


        Integer numeroPagina = req.queryParams("query_num_pagina")!= null ? Integer.valueOf(req.queryParams("query_num_pagina")) : 1;

        for(int i =0; i < egresos.size() ; i++){
            if((numeroPagina*10)-10 < i  && i < numeroPagina*10 ){
                egresosPaginados.add(egresos.get(i));

            }
        }

        parameters.put("egresos", egresosPaginados);


        return new ModelAndView(parameters, "egresos.hbs");
    }


    public ModelAndView showEgreso(Request request, Response response){
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", request.session().attribute("user"));

        return new ModelAndView(parameters, "egreso.hbs");
    }


    public ModelAndView crearEgreso(Request req, Response res) throws Exception {

        try {

            String medioDePago = req.queryParams("query_medio_de_pago");
            MedioDePago medioDePagoFinal;
            String monto;
            String nombre;
            String numero;

            if(req.queryParams("tarjeta-credito-num") != null){
                nombre = req.queryParams("tarjeta-credito-nombre-apellido");
                String cuotas = req.queryParams("tarjeta-credito-cantidad");
                numero = req.queryParams("tarjeta-credito-num");
                medioDePagoFinal = new TarjetaDeCredito(Integer.valueOf(cuotas), nombre, numero);
            }
            else{
                if(req.queryParams("tarjeta-debito-num") != null){
                    nombre = req.queryParams("tarjeta-debito-nombre-apellido");
                    numero = req.queryParams("tarjeta-debito-num");
                    medioDePagoFinal = new TarjetaDeDebito(nombre, numero);
                }
                else {
                    if(req.queryParams("efectivo-monto") != null){
                        monto = req.queryParams("efectivo-monto");
                        String puntoDePago = req.queryParams("efectivo-punto-de-pago");
                        medioDePagoFinal = new Efectivo(Double.valueOf(monto), puntoDePago);
                    }
                    else{
                        if(req.queryParams("dinero-cuenta-monto") != null){
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


            String EONombre = req.queryParams("query_EO_nombre");
            String EOCuil = req.queryParams("query_EO_cuil");
            String EODireccion = req.queryParams("query_EO_direccion");

            EntidadOperacion entidadOrigen = new EntidadOperacion(EONombre, EOCuil, EODireccion);

            // TODO, debe verificarse que la entidad origen sea perteneciente a la org del usuario

            String EDNombre = req.queryParams("query_ED_nombre");
            String EDCuil = req.queryParams("query_ED_cuil");
            String EDDireccion = req.queryParams("query_ED_direccion");

            EntidadOperacion entidadDestino = new EntidadOperacion(EDNombre, EDCuil, EDDireccion);

            String presupuestosNecesarios = req.queryParams("presupuestos-necesarios-num");

            OperacionEgreso egreso = new OperacionEgreso(items,medioDePagoFinal , documento, new Date(fecha), entidadOrigen, entidadDestino, Integer.valueOf(presupuestosNecesarios));

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
    }};

}
