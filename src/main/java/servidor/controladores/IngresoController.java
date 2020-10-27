package servidor.controladores;

import datos.RepositorioCategorizacion;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.entidades.Organizacion;
import dominio.operaciones.*;
import servicio.abOperaciones.ServicioABOperaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.*;
import java.util.stream.Collectors;

public class IngresoController extends Controller{

    ServicioABOperaciones servicioOperaciones = new ServicioABOperaciones();

    public ModelAndView mostrarIngresos(Request req, Response res, EntityManager entityManager) {
        RepositorioCategorizacion repositorioCategorizacion = new RepositorioCategorizacion(entityManager);

        String href = "/ingresos";
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", req.session().attribute("user"));

        CuentaUsuario usuario = req.session().attribute("user");

        Organizacion org = usuario.getOrganizacion();

        ArrayList<OperacionIngreso> ingresos = servicioOperaciones.listarIngresosPorOrg(org);

        int ingresosPorPagina = 3;

        String pagina = req.queryParams("pagina");

        if(pagina == null){
            if(ingresos.size() > ingresosPorPagina){ // 3 egresos por pagina
                if(href.equals("/ingresos"))
                    href = href.concat("?pagina=1");
                else
                    href = href.concat("&pagina=1");
                res.redirect(href); // redirecciona a la pagina 1
                return null;
            }

            parameters.put("ingresos",ingresos);
            parameters.put("user", req.session().attribute("user"));
        }
        else{
            int numeroPagina = Integer.parseInt(pagina);
            int indiceInicial = Math.min((numeroPagina - 1) * ingresosPorPagina,ingresos.size());
            int indiceFinal = Math.min(numeroPagina * ingresosPorPagina,ingresos.size());
            List<OperacionIngreso> ingresossSubLista = ingresos.subList(indiceInicial,indiceFinal);

            parameters.put("ingresos",ingresossSubLista);

            int cantidadPaginas = (int) Math.ceil((double)ingresos.size()/ingresosPorPagina);
            ArrayList<Integer> listaCantidadPaginas = new ArrayList<>();
            for(int i = 1;i<=cantidadPaginas; i++){
                listaCantidadPaginas.add(i);
            }
            parameters.put("cantidad_paginas",listaCantidadPaginas);
            if(numeroPagina > 1)
                parameters.put("pagina_anterior",numeroPagina - 1);
            if(numeroPagina * ingresosPorPagina < ingresos.size())
                parameters.put("pagina_siguiente",numeroPagina + 1);

            parameters.put("user", req.session().attribute("user"));
        }
        
        parameters.put("criteriosDeCategorizacion",repositorioCategorizacion.getCriteriosDeCategorizacion());
        return new ModelAndView(parameters,"ingresos.hbs");
    }


    public ModelAndView showIngreso(Request request, Response response){
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", request.session().attribute("user"));

        return new ModelAndView(parameters, "ingreso.hbs");
    }


}
