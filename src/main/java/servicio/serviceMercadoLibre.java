package servicio;


import com.mercadolibre.restclient.http.Header;
import com.mercadolibre.restclient.http.Headers;
import dominio.utils.*;

import org.json.JSONArray;
import org.json.JSONObject;


public enum serviceMercadoLibre {

    INSTANCE;

    private RestClientML restClient = RestClientML.INSTANCE;


    public JSONObject getMediosDePago() throws Exception {
        String url = "https://api.mercadopago.com/v1/payment_methods";
        return JsonUtils.getLinkedHashMapToJson(restClient.get(url, headersMeli()));
    }

    public Object getMediosDePago() throws Exception {
        String url = "https://api.mercadopago.com/v1/payment_methods";

        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.getResponseJsonArray(restClient.get(url, new Headers())).toString(), Currency[].class);
    }

    public static Headers headersMeli() throws Exception {
        Headers headers = new Headers();
        headers.add(new Header("Content-Type", "application/json"));
        headers.add(new Header("Authorization", "Basic TEST-5529781788776149-102121-5a1e43ff1a729254de5c242630f84693-440217811"));

        return headers;
    }

}
