package servidor.controladores;

import servicio.abm_categorizaciones.ServicioABMCategorizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CategorizacionesController {

    ServicioABMCategorizaciones categorizacionesService;

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

}
