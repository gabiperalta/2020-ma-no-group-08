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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
        JsonObject jsonObject = null;

        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try (InputStream is = request.raw().getPart("archivojson").getInputStream()) {
            // Use the input stream to create a file
            String texto = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().collect(Collectors.joining(""));
            //System.out.println(texto);
            jsonObject = JsonParser.parseString(texto).getAsJsonObject();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String egresoId = request.queryParams("egreso_id");
        //System.out.println("Egreso ID: " + egresoId);

        OperacionEgreso operacionEgresoEncontrada = RepoOperacionesEgreso.getInstance().buscarOperacionEgresoPorIdenticadorOperacionEgreso(egresoId);
        //System.out.println("Monto total del egreso encontrado: " + operacionEgresoEncontrada.getMontoTotal());

        //ServicioABOperaciones servicioABOperaciones = new ServicioABOperaciones();

        //Gson gson = new Gson();
        //JsonObject jsonEntidadOperacion = jsonObject.get("entidadOperacion").getAsJsonObject();
        EntidadOperacion entidadOperacion = gson.fromJson(jsonObject.get("entidadOperacion"),EntidadOperacion.class);
        //System.out.println(entidadOperacion.getDireccion());
        JsonArray jsonArray = jsonObject.get("item").getAsJsonArray();
        ArrayList<Item> items = new ArrayList<>();
        for(JsonElement i : jsonArray){
            //JsonObject jsonObj = i.getAsJsonObject();
            Item item = gson.fromJson(i,Item.class);
            items.add(item);
            //System.out.println(item.getTipo());
        }

        Presupuesto presupuesto = new Presupuesto(entidadOperacion,items);

        ServicioABLicitaciones servicioABLicitaciones = new ServicioABLicitaciones();
        Licitacion licitacion = servicioABLicitaciones.altaLicitacion(operacionEgresoEncontrada, NotificadorSuscriptores.getInstance());
        servicioABLicitaciones.altaPresupuesto(licitacion,presupuesto);
        licitacion.agregarCriterioSeleccionDeProveedor(new CriterioMenorPrecio());
        RepoLicitaciones.getInstance().agregarLicitacion(licitacion);

        Licitacion licitacionNueva = RepoLicitaciones.getInstance().buscarLicitacionPorOperacionEgreso(egresoId);

        response.status(200);
        //response.body("licitacion_id=2");
        //response.type("application/json");

        return licitacionNueva.getIdentificador(); // retorno el id de la licitacion creada
    }

    public Object realizarLicitacion(Request request,Response response){
        String licitacionId = request.queryParams("licitacion_id"); // podria ponerse tambien (como opcion) el id del egreso
        //System.out.println(licitacionId);
        Licitacion licitacionEncontrada = RepoLicitaciones.getInstance().buscarLicitacionPorIdentificador(licitacionId);
        licitacionEncontrada.licitar();
        //System.out.println(licitacionEncontrada.estaFinalizada());
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
            jsonResultado = "{resultado:\"Licitacion en proceso\"}";
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

    public static void initRepoPrueba(){
        //RepoOperacionesEgreso repoOperacionesEgreso = new RepoOperacionesEgreso();

        OperacionEgresoBuilder builderCompra;
        OperacionEgreso compra;
        //Licitacion licitacion;
        Presupuesto presup1;
        //Presupuesto presup2;
        //Presupuesto presup3;
        //Presupuesto presup4;
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
                .agregarPresupuestosNecesarios(1)
                .build();

        try{
            RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(compra);
            //System.out.println(compra.getIdentificador());
            //System.out.println("Estado repo egreso: " + RepoOperacionesEgreso.getInstance().getOperacionesEgreso().size());
        }
        catch (Exception e){

        }
    }
}
