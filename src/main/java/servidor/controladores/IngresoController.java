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

import java.util.*;

public class IngresoController extends Controller{

    ServicioABOperaciones servicioOperaciones = new ServicioABOperaciones();

    public ModelAndView mostrarIngresos(Request req, Response res) {

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", req.session().attribute("user"));

        CuentaUsuario usuario = req.session().attribute("user");

        Organizacion org = usuario.getOrganizacion();

        ArrayList<OperacionIngreso> ingresos = servicioOperaciones.listarIngresosPorOrg(org);

        int ingresosPorPagina = 3;

        String pagina = req.queryParams("pagina");

        if(pagina == null){
            if(ingresos.size() > ingresosPorPagina){ // 3 egresos por pagina
                res.redirect("/ingresos?pagina=1"); // redirecciona a la pagina 1
                return null;
            }

            //OUTPUT
            Map<String, Object> map = new HashMap<>();
            map.put("ingresos",ingresos);
            map.put("user", req.session().attribute("user"));
            return new ModelAndView(map,"ingresos.hbs");
        }
        else{
            int numeroPagina = Integer.parseInt(pagina);
            int indiceInicial = Math.min((numeroPagina - 1) * ingresosPorPagina,ingresos.size());
            int indiceFinal = Math.min(numeroPagina * ingresosPorPagina,ingresos.size());
            List<OperacionIngreso> ingresossSubLista = ingresos.subList(indiceInicial,indiceFinal);

            //OUTPUT
            Map<String, Object> map = new HashMap<>();
            map.put("ingresos",ingresossSubLista);

            int cantidadPaginas = (int) Math.ceil((double)ingresos.size()/ingresosPorPagina);
            ArrayList<Integer> listaCantidadPaginas = new ArrayList<>();
            for(int i = 1;i<=cantidadPaginas; i++){
                listaCantidadPaginas.add(i);
            }
            map.put("cantidad_paginas",listaCantidadPaginas);
            if(numeroPagina > 1)
                map.put("pagina_anterior",numeroPagina - 1);
            if(numeroPagina * ingresosPorPagina < ingresos.size())
                map.put("pagina_siguiente",numeroPagina + 1);

            map.put("user", req.session().attribute("user"));

            return new ModelAndView(map,"ingresos.hbs");
        }
    }


    public ModelAndView showIngreso(Request request, Response response){
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", request.session().attribute("user"));

        return new ModelAndView(parameters, "ingreso.hbs");
    }


}
