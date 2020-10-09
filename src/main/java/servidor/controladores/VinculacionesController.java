package servidor.controladores;

import componenteVinculador.criterio.GeneradorCriterio;
import componenteVinculador.criterio.vinculacion.CriterioVinculacion;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionIngreso;
import dominio.operaciones.RepoOperacionesEgreso;
import dominio.operaciones.RepoOperacionesIngreso;
import org.apache.commons.collections.map.HashedMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VinculacionesController {

    public ModelAndView seleccionarOperaciones(Request request, Response response) {

        CuentaUsuario usuario = request.session().attribute("user");

        ArrayList<OperacionIngreso> ingresos = RepoOperacionesIngreso.getInstance().getIngresos();
        ArrayList<OperacionEgreso> egresos = RepoOperacionesEgreso.getInstance().getOperacionesEgreso();
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", usuario);
        parameters.put("ingresos", ingresos);
        parameters.put("egresos", egresos);

        return new ModelAndView(parameters, "vinculaciones.hbs");
    }

    public Response vincular(Request request, Response response) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        Set<String> queryP = request.queryParams();
        String tipoCriterio = request.queryParams("criterio");
        Integer rangoDias = Integer.valueOf(request.queryParams("rangoDias"));
//        TODO: falta descargar el json
        CriterioVinculacion criterio = GeneradorCriterio.generarCriterio(tipoCriterio,rangoDias);
        response.redirect("/home", 302);
        return response;
    }

}