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

public class IngresoController extends Controller{

    ServicioABOperaciones servicioOperaciones = new ServicioABOperaciones();

    public ModelAndView mostrarIngresos(Request req, Response res) {

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", req.session().attribute("user"));

        CuentaUsuario usuario = req.session().attribute("user");

        Organizacion org = usuario.getOrganizacion();

        ArrayList<OperacionIngreso> ingresos = servicioOperaciones.listarIngresosPorOrg(org);

        ArrayList<OperacionIngreso> ingresosPaginados = new ArrayList<>();

        Integer numeroPagina = req.queryParams("query_num_pagina")!= null ? Integer.valueOf(req.queryParams("query_num_pagina")) : 1;

        for(int i =0; i < ingresos.size() ; i++){
            if((numeroPagina*10)-10 < i  && i < numeroPagina*10 ){
                ingresosPaginados.add(ingresos.get(i));

            }
        }

        parameters.put("ingresos", ingresosPaginados);

        return new ModelAndView(parameters, "ingresos.hbs");
    }


    public ModelAndView showIngreso(Request request, Response response){
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", request.session().attribute("user"));

        return new ModelAndView(parameters, "ingreso.hbs");
    }


}
