package servidor.controladores;

import com.google.gson.*;
import dominio.licitacion.Licitacion;
import dominio.licitacion.Presupuesto;
import dominio.licitacion.RepoLicitaciones;
import dominio.licitacion.criterioSeleccion.CriterioMenorPrecio;
import dominio.notificador_suscriptores.NotificadorSuscriptores;
import dominio.operaciones.*;
import dominio.operaciones.medioDePago.Efectivo;
import servicio.ab_licitaciones.ServicioABLicitaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark.*;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class LicitacionController{

    static Gson gson;

    public LicitacionController(){
        gson = new Gson();
        initRepoPrueba();
    }

    public Gson getGson(){
        return gson;
    }

    public Object agregarPresupuesto(Request request, Response response){
        JsonObject jsonObjectArchivo = null;
        JsonArray jsonArrayArchivo = null;

        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try (InputStream is = request.raw().getPart("archivojson").getInputStream()) {
            // Use the input stream to create a file
            String textoArchivo = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().collect(Collectors.joining(""));
            //System.out.println(texto);

            if(textoArchivo.startsWith("[")) {
                jsonArrayArchivo = JsonParser.parseString(textoArchivo).getAsJsonArray();
            }
            else if(textoArchivo.startsWith("{")){
                jsonObjectArchivo = JsonParser.parseString(textoArchivo).getAsJsonObject();
            }
            else{
                // manejar el error con algun response
                System.out.println("Archivo erroneo o no respeta la sintaxis JSON");
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        String egresoId = request.queryParams("egreso_id");
        OperacionEgreso operacionEgresoEncontrada = RepoOperacionesEgreso.getInstance().buscarOperacionEgresoPorIdenticadorOperacionEgreso(egresoId);

        ArrayList<Presupuesto> presupuestos = new ArrayList<>();

        if(jsonArrayArchivo != null){
            jsonArrayArchivo.forEach(jsonElement -> presupuestos.add(jsonAPresupuesto(jsonElement.getAsJsonObject())));
        }
        else if (jsonObjectArchivo != null){
            presupuestos.add(jsonAPresupuesto(jsonObjectArchivo));
        }

        ServicioABLicitaciones servicioABLicitaciones = new ServicioABLicitaciones();
        Licitacion licitacion = servicioABLicitaciones.altaLicitacion(operacionEgresoEncontrada, NotificadorSuscriptores.getInstance());
        presupuestos.forEach(presupuesto -> servicioABLicitaciones.altaPresupuesto(licitacion,presupuesto));
        licitacion.agregarCriterioSeleccionDeProveedor(new CriterioMenorPrecio());
        RepoLicitaciones.getInstance().agregarLicitacion(licitacion);

        response.status(200);
        //response.body("licitacion_id=2");
        //response.type("application/json");

        return licitacion.getIdentificador(); // retorno el id de la licitacion creada
    }

    public Object realizarLicitacion(Request request,Response response){
        String licitacionId = request.queryParams("licitacion_id"); // podria ponerse tambien (como opcion) el id del egreso
        Licitacion licitacionEncontrada = RepoLicitaciones.getInstance().buscarLicitacionPorIdentificador(licitacionId);
        if(licitacionEncontrada == null){
            response.status(404);
            return "Licitacion inexistente";
        }
        licitacionEncontrada.licitar();
        return licitacionEncontrada.getIdentificador();
    }

    public Object resultadoLicitacion(Request request,Response response){
        String licitacionId = request.params("licitacion_id");
        Licitacion licitacionEncontrada = RepoLicitaciones.getInstance().buscarLicitacionPorIdentificador(licitacionId);

        String jsonResultado;
        if(licitacionEncontrada.estaFinalizada()){
            jsonResultado = "{resultado: " + licitacionEncontrada.mensajeTexto() + "}";
        }
        else{
            jsonResultado = "{\"resultado\":\"Licitacion en proceso\"}";
        }

        response.type("application/json");
        //response.body(jsonResultado);
        response.status(200);

        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("resultado",licitacionEncontrada.mensajeTexto());

        return jsonObject;
    }

    public ModelAndView agregarArchivo(Request request,Response response){
        return new ModelAndView(new HashMap<>(),"archivo.hbs");
    }

    public ModelAndView mostrarPresupuestos(Request request,Response response){
        ArrayList<Licitacion> licitaciones = RepoLicitaciones.getInstance().getLicitaciones();

        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("licitaciones", licitaciones);
        //map.put("valorprueba","TIME_IS_RUNNING_OUUUUT");

        return new ModelAndView(map,"presupuestos.hbs");
    }

    public static void initRepoPrueba(){
        OperacionEgresoBuilder builderCompra;
        OperacionEgreso compra;
        Licitacion licitacion1;
        Licitacion licitacion2;
        Presupuesto presup1;
        Presupuesto presup2;
        Presupuesto presup3;
        Presupuesto presup4;
        EntidadOperacion proveedor1;
        EntidadOperacion proveedor2;

        Efectivo pesos = new Efectivo(200000,"Rapipago");
        DocumentoComercial documento = new DocumentoComercial(ETipoDoc.FACTURA, 2000);
        Date fecha = new Date();
        EntidadOperacion origen = new EntidadOperacion("Operacion compra 1","20-40678950-4","Av.Libertador 800");
        EntidadOperacion destino = new EntidadOperacion("Operacion compra 1", "20-40678950-4", "Av.Corrientes 550");

        ArrayList<Item> listaItemsCompra = new ArrayList<Item>();
        listaItemsCompra.add(new Item(50, ETipoItem.ARTICULO, "Item1"));
        listaItemsCompra.add(new Item(100, ETipoItem.ARTICULO, "Item2"));

        builderCompra = new OperacionEgresoBuilder();

        compra = builderCompra.agregarItems(listaItemsCompra)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino)
                .agregarPresupuestosNecesarios(2)
                .build();

        licitacion1 = new Licitacion(compra, NotificadorSuscriptores.getInstance());

        licitacion1.agregarCriterioSeleccionDeProveedor(new CriterioMenorPrecio());

        ArrayList<Item> listaItems1 = new ArrayList<>();
        listaItems1.add(new Item(50, ETipoItem.ARTICULO, "Item1"));
        listaItems1.add(new Item(100, ETipoItem.ARTICULO, "Item2"));

        ArrayList<Item> listaItems2 = new ArrayList<>();
        listaItems2.add(new Item(200, ETipoItem.ARTICULO, "Item3"));
        listaItems2.add(new Item(150, ETipoItem.ARTICULO, "Item4"));

        ArrayList<Item> listaItems3 = new ArrayList<>();
        listaItems3.add(new Item(500, ETipoItem.ARTICULO, "Item1"));
        listaItems3.add(new Item(1020, ETipoItem.ARTICULO, "Item2"));

        ArrayList<Item> listaItems4 = new ArrayList<>();
        listaItems4.add(new Item(10, ETipoItem.ARTICULO, "Item1"));
        listaItems4.add(new Item(30, ETipoItem.ARTICULO, "Item2"));

        proveedor1 = new EntidadOperacion("Operacion compra 1","20-40678950-4","Av.Libertador 800");
        proveedor2 = new EntidadOperacion("Operacion compra 2","20-40678950-3","Av.Libertador 100");

        presup1 = new Presupuesto(proveedor1,listaItems1);
        presup2 = new Presupuesto(proveedor2,listaItems2);
        presup3 = new Presupuesto(proveedor1,listaItems3);
        presup4 = new Presupuesto(proveedor2,listaItems4);

        licitacion1.agregarPresupuesto(presup1);
        licitacion1.agregarPresupuesto(presup3);

        try{
            RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(compra);
            RepoLicitaciones.getInstance().agregarLicitacion(licitacion1);
            //System.out.println(compra.getIdentificador());
            //System.out.println("Estado repo egreso: " + RepoOperacionesEgreso.getInstance().getOperacionesEgreso().size());
        }
        catch (Exception e){

        }
    }

    public static Presupuesto jsonAPresupuesto(JsonObject jsonPresupuesto){
        EntidadOperacion entidadOperacion = gson.fromJson(jsonPresupuesto.get("entidadOperacion"),EntidadOperacion.class);
        JsonArray jsonArrayItems = jsonPresupuesto.get("item").getAsJsonArray();
        ArrayList<Item> items = new ArrayList<>();
        jsonArrayItems.forEach(jsonElement -> items.add(gson.fromJson(jsonElement,Item.class)));
        return new Presupuesto(entidadOperacion,items);
    }
}
