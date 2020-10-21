package servidor.controladores;

import dominio.categorizacion.exceptions.CategorizacionException;
import servicio.abm_categorizaciones.ServicioABMCategorizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CategorizacionesController {

    ServicioABMCategorizaciones categorizacionesService;

    private String mensajeError;

    public CategorizacionesController(){
        categorizacionesService = new ServicioABMCategorizaciones();
    }

    public ModelAndView showCategorizacionesPage(Request request, Response response){
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", request.session().attribute("user"));
        parameters.put("id-entidad-categorizable", request.queryParams("id-entidad-categorizable"));
        parameters.put("criterios-de-categorizacion", categorizacionesService.listarCriteriosDeCategorizacion());

        return new ModelAndView(parameters, "categorizacion.hbs");
    }

    public ModelAndView categorizar(Request request, Response response) {
        try{
            String idEntidadCategorizable = request.queryParams("id-entidad-categorizable");
            String nombreCriterioCategorizacion = request.queryParams("criterio");
            String nombreCategoria = request.queryParams("categoria");

            categorizacionesService.asociarCategoriaAEntidadCategorizable(idEntidadCategorizable, nombreCategoria, nombreCriterioCategorizacion);
        }
        catch(CategorizacionException e){
            mensajeError = "Null error: " + e.getMessage();
            return new ModelAndView(this, "fallaCategorizacion.hbs");
        }
        catch (Exception e) {
            mensajeError = "Error desconocido: " + e.getMessage() + request.queryMap();
            return new ModelAndView(this, "fallaCategorizacion.hbs");
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user", request.session().attribute("user"));

        return new ModelAndView(parameters, "exitoCategorizar.hbs");
    }

    public ModelAndView showDescategorizacionesPage(Request request, Response response){
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", request.session().attribute("user"));
        parameters.put("id-entidad-categorizable", request.queryParams("id-entidad-categorizable"));
        parameters.put("criterios-de-categorizacion", categorizacionesService.listarCriteriosDeCategorizacion());
        parameters.put("esDescategorizacion", true);

        return new ModelAndView(parameters, "categorizacion.hbs");
    }

    public ModelAndView descategorizar(Request request, Response response) {
        try{
            String idEntidadCategorizable = request.queryParams("id-entidad-categorizable");
            String nombreCriterioCategorizacion = request.queryParams("criterio");
            String nombreCategoria = request.queryParams("categoria");

            categorizacionesService.desasociarCategoriaAEntidadCategorizable(idEntidadCategorizable, nombreCategoria, nombreCriterioCategorizacion);
        }
        catch(CategorizacionException e){
            mensajeError = "Null error: " + e.getMessage();
            return new ModelAndView(this, "fallaCategorizacion.hbs");
        }
        catch (Exception e) {
            mensajeError = "Error desconocido: " + e.getMessage() + request.queryMap();
            return new ModelAndView(this, "fallaCategorizacion.hbs");
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user", request.session().attribute("user"));

        return new ModelAndView(parameters, "exitoCategorizar.hbs");
    }

    public String getMensajeError() {
        return mensajeError;
    }
}
