package servidor.controladores;

import componenteVinculador.criterio.GeneradorCriterio;
import componenteVinculador.criterio.vinculacion.CriterioVinculacion;
import componenteVinculador.vinculable.GeneradorVinculable;
import componenteVinculador.vinculable.OperacionVinculable;
import componenteVinculador.vinculador.Vinculador;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.operaciones.*;
import org.apache.commons.collections.map.HashedMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.*;

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

        String[] ingresosIds = request.queryParams("ingresosId").split(",", 99);
        String[] egresosIds = request.queryParams("egresosId").split(",", 99);

        //TODO: cambiar por un array de criterios
        CriterioVinculacion criterio = generarCriterio(request);
        ArrayList<CriterioVinculacion> criterios = new ArrayList<>();
        criterios.add(criterio);

        Vinculador vinculador = new Vinculador();
        vinculador.vincular(generarOperacionesVinculables(ingresosIds, true),
                            generarOperacionesVinculables(egresosIds, false),
                            criterios);

        //        TODO: falta descargar el json

        response.redirect("/home", 302);
        return response;
    }

    private CriterioVinculacion generarCriterio (Request request) throws Exception {
        String tipoCriterio = request.queryParams("criterio");
        Integer rangoDias = Integer.valueOf(request.queryParams("rangoDias"));
        return GeneradorCriterio.generarCriterio(tipoCriterio,rangoDias);
    }

    private ArrayList<OperacionVinculable> generarOperacionesVinculables (String[] ingresosIds , boolean esIngreso) {
        ArrayList<Operacion> operaciones = new ArrayList<>();
        for (String opId: ingresosIds) {
            Operacion operacion;
            if (esIngreso) {
                operacion = RepoOperacionesIngreso.getInstance().buscarOperacionEgresoPorIdentificador(opId);
            } else {
                operacion = RepoOperacionesEgreso.getInstance().buscarOperacionEgresoPorIdentificador(opId);
            }
            operaciones.add(operacion);
        }

        ArrayList<OperacionVinculable> operacionesVinculables =  new ArrayList<>();
        for (Operacion op: operaciones) {
            operacionesVinculables.add(GeneradorVinculable.generarVinculable(op.getMontoTotal(), op.getFecha(),esIngreso));
        }
        return operacionesVinculables;
    }

}