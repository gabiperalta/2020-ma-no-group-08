package dominio.utils;

import com.mercadolibre.restclient.http.Header;
import dominio.utils.*;

import com.mercadolibre.restclient.RESTPool;
import com.mercadolibre.restclient.Response;
import com.mercadolibre.restclient.RestClient;
import com.mercadolibre.restclient.http.Headers;
import com.mercadolibre.restclient.retry.SimpleRetryStrategy;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;


public enum RestClientML {
    INSTANCE;

    private final String RESOURCE_NAME = "generic_restclient";

    private RestClient restClient;
    private RestClient restClientQuery;

    RestClientML() {
        try {
            RESTPool pool = RESTPool.builder().withName(RESOURCE_NAME)
                    .withSocketTimeout(70000)
                    .withConnectionTimeout(25000)
                    .withMaxTotal(1000)
                    .withMaxPerRoute(1000)
                    .build();

            RESTPool poolQuery = RESTPool.builder().withName(RESOURCE_NAME)
                    .withSocketTimeout(70000)
                    .withConnectionTimeout(25000)
                    .withMaxTotal(1000)
                    .withMaxPerRoute(1000)
                    .build();


            restClient = RestClient.builder().withPool(pool).build();
            restClientQuery = RestClient.builder().withPool(poolQuery).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public JSONObject get(String endpoint, Headers headers) throws Exception {

        Response response = restClient.withPool(RESOURCE_NAME)
                .get(endpoint, headers);

        return responseRequest(response);
    }

    private JSONObject responseRequest(Response response) throws Exception {

        JSONObject resp = new JSONObject();
        resp.put("body",response.getData());
        resp.put("status",response.getStatus());
        resp.put("headers",response.getHeaders());
        return resp;
    }

}

