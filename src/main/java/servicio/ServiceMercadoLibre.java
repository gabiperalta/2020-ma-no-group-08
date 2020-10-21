package servicio;


import com.google.gson.Gson;
import com.mercadolibre.restclient.http.Headers;
import dominio.operaciones.medioDePago.*;
import dominio.utils.*;
import com.mercadolibre.restclient.http.Header;


import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CORBA.OBJ_ADAPTER;
import spark.Request;
import spark.Response;


public enum ServiceMercadoLibre {

    INSTANCE;

    private RestClientML restClient = RestClientML.INSTANCE;



    public Object getMediosDePago() throws Exception {
        String url = "https://api.mercadopago.com/v1/payment_methods";

        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.getResponseJsonArray(restClient.get(url, headersMeli())).toString(), MedioPagoMeli[].class);
    }

    public static Headers headersMeli() throws Exception {
        Headers headers = new Headers();
        headers.add(new Header("Content-Type", "application/json"));
        headers.add(new Header("Authorization", "Bearer TEST-5529781788776149-102121-5a1e43ff1a729254de5c242630f84693-440217811"));

        return headers;
    }



}
