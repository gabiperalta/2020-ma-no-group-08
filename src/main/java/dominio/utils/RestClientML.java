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
                    .withMaxTotal(Config.getInt("http.max.connections"))
                    .withMaxPerRoute(Config.getInt("http.max.connections.per.route"))
                    .withBaseURL(Config.getString("api.base.url"))
                    .withRetryStrategy(new SimpleRetryStrategy(1, Config.getLong("http.retry.wait")))
                    .build();

            RESTPool poolQuery = RESTPool.builder().withName(RESOURCE_NAME)
                    .withSocketTimeout(70000)
                    .withConnectionTimeout(25000)
                    .withMaxTotal(Config.getInt("http.max.connections"))
                    .withMaxPerRoute(Config.getInt("http.max.connections.per.route"))
                    .withBaseURL(Config.getString("api.base.url"))
                    .withRetryStrategy(new SimpleRetryStrategy(1, Config.getLong("http.retry.wait")))
                    .build();


            restClient = RestClient.builder().withPool(pool).build();
            restClientQuery = RestClient.builder().withPool(poolQuery).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public JSONObject get(String endpoint, JSONObject params) throws Exception {
        Response response = restClient.withPool(RESOURCE_NAME)
                .get(endpoint, getJsonToHeader(params));

        return responseRequest(response);
    }

    public JSONObject get(String endpoint, Headers headers) throws Exception {

        Response response = restClient.withPool(RESOURCE_NAME)
                .get(endpoint, headers);


        return responseRequest(response);
    }

    public JSONObject post(String endpoint, Headers headers, JSONObject body) throws Exception {
        byte[] body2 = body.toString().getBytes();

        Response response = restClient.withPool(RESOURCE_NAME)
                .post(endpoint,headers,body2);


        return responseRequest(response);
    }

    public JSONObject put(String endpoint, Headers headers, JSONObject body) throws Exception {
        byte[] body2 = body.toString().getBytes();


        Response response = restClient.withPool(RESOURCE_NAME)
                .put(endpoint,headers,body2);

        return responseRequest(response);
    }

    private JSONObject responseRequest(Response response) throws Exception {

        JSONObject resp = new JSONObject();
        resp.put("body",response.getData());
        resp.put("status",response.getStatus());
        resp.put("headers",response.getHeaders());
        return resp;
    }


    public static Headers getJsonToHeader(JSONObject headerParam){
        Headers headers = new Headers();

        Iterator<?> keys = headerParam.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String value = headerParam.getString(key);

            headers.add(new Header(key, value));
        }

        return headers;
    }
}

