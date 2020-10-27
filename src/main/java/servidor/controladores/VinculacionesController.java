package servidor.controladores;

import componenteVinculador.criterio.GeneradorCriterio;
import componenteVinculador.criterio.vinculacion.CriterioVinculacion;
import componenteVinculador.vinculable.GeneradorVinculable;
import componenteVinculador.vinculable.OperacionVinculable;
import componenteVinculador.vinculador.Vinculador;
import datos.RepoOperacionesEgreso;
import datos.RepoOperacionesIngreso;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.entidades.Organizacion;
import dominio.operaciones.*;
import servicio.abOperaciones.ServicioABOperaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.*;

public class VinculacionesController {

    public ModelAndView seleccionarOperaciones(Request request, Response response) {

        CuentaUsuario usuario = request.session().attribute("user");

        ServicioABOperaciones servicioABOperaciones = new ServicioABOperaciones();
        ArrayList<OperacionIngreso> ingresos = servicioABOperaciones.listarIngresosPorOrg(usuario.getOrganizacion());
        ArrayList<OperacionEgreso> egresos = servicioABOperaciones.listarOperaciones(usuario.getOrganizacion());
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", usuario);
        parameters.put("ingresos", ingresos);
        parameters.put("egresos", egresos);

        return new ModelAndView(parameters, "vinculaciones.hbs");
    }

    public String vincular(Request request, Response response) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        Set<String> queryP = request.queryParams();

        CuentaUsuario usuario = request.session().attribute("user");

        ArrayList ingresosIds = generateIds("OI-", request.queryParams("ingresosIds").split(" ", 99));

        ArrayList egresosIds = generateIds("OE-", request.queryParams("egresosIds").split(" ", 99));

        CriterioVinculacion criterio = generarCriterio(request);
        ArrayList<CriterioVinculacion> criterios = new ArrayList<>();
        criterios.add(criterio);

        Vinculador vinculador = new Vinculador();
        vinculador.vincular(generarOperacionesVinculables(ingresosIds, true, usuario.getOrganizacion()),
                            generarOperacionesVinculables(egresosIds, false, usuario.getOrganizacion()),
                            criterios);

        response.type("application/json");

        return vinculador.getVinculacionJsonString();
    }

    private CriterioVinculacion generarCriterio (Request request) throws Exception {
        String tipoCriterio = request.queryParams("criterio");
        Integer rangoDias = Integer.valueOf(request.queryParams("rangoDias"));
        return GeneradorCriterio.generarCriterio(tipoCriterio,rangoDias);
    }

    private ArrayList<OperacionVinculable> generarOperacionesVinculables (ArrayList ingresosIds , boolean esIngreso, Organizacion organizacion) {
        ArrayList<Operacion> operaciones = new ArrayList<>();
        for (Object opId: ingresosIds) {
            Operacion operacion;
            if (esIngreso) {
                operacion = RepoOperacionesIngreso.getInstance().buscarOperacionIngresoPorIdentificador((String) opId);
            } else {
                operacion = RepoOperacionesEgreso.getInstance().buscarOperacionEgresoPorIdenticadorOperacionEgreso((String) opId);
            }

            if (operacion != null) {
                operaciones.add(operacion);
            }
        }

        ArrayList<OperacionVinculable> operacionesVinculables =  new ArrayList<>();
        for (Operacion op: operaciones) {
            operacionesVinculables.add(GeneradorVinculable.generarVinculable(op.getMontoTotal(), op.getFecha(),esIngreso));
        }
        return operacionesVinculables;
    }

    private ArrayList generateIds (String sufijo, String[] ids) {
        ArrayList auxIds = new ArrayList();
        for (String id:ids) {
            auxIds.add(sufijo + id);
        }
        return auxIds;
    }
}