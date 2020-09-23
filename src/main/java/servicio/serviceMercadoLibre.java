package servicio;


import com.google.gson.Gson;
import com.mercadolibre.restclient.http.Headers;
import dominio.modelosMeli.*;
import dominio.utils.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CORBA.OBJ_ADAPTER;

//TODO: ver como si viene un state dentro de otra api eso se arma como un modelo state
//TODO: ver como hacer con country porque en una api viene con mas info que en otra


public enum serviceMercadoLibre {

    INSTANCE;

    private RestClientML restClient = RestClientML.INSTANCE;


    public Object getCountries() throws Exception {
        String url = "https://api.mercadolibre.com/classified_locations/countries";

        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.getResponseJsonArray(restClient.get(url, new Headers())).toString(), Country[].class);    }

    public Object getCountry(String country) throws Exception {
        String url = "https://api.mercadolibre.com/classified_locations/countries/" + country;

        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers())).toString(), Country[].class);
    }

    public Object getState(String state) throws Exception {
        String url = "https://api.mercadolibre.com/classified_locations/states/" + state;
        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers())).toString(), State[].class);
    }

    public Object getCity(String city) throws Exception {
        String url = "https://api.mercadolibre.com/classified_locations/cities/" + city;
        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers())).toString(), City[].class);    }

    public Object getMonedas() throws Exception {
        String url = "https://api.mercadolibre.com/currencies/";

        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.getResponseJsonArray(restClient.get(url, new Headers())).toString(), Currency[].class);
    }


    public Object getMoneda(String moneda) throws Exception {
        String url = "https://api.mercadolibre.com/currencies/" + moneda;
        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers())).toString(), Currency[].class);    }

    public Object getConversion(String monedaInicio, String monedaFin) throws Exception {
        String url = "https://api.mercadolibre.com/currency_conversions/search?from="+monedaInicio+"&to="+monedaFin;
        Gson gson = new Gson();
        return gson.fromJson(JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers())).toString(), Conversion[].class);    }

    public JSONObject getCountryWithZipCode(String country, String zipCode) throws Exception {
        String url = "https://api.mercadolibre.com/countries/"+country+"/zip_codes/"+zipCode;
        return JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers()));
    }

    public JSONObject getCountryWithZipCode(String country, String zipCodeInit, String zipCodeFinal) throws Exception {
        String url = "https://api.mercadolibre.com/country/"+country+"/zip_codes/search_between?zip_code_from="+zipCodeInit+"&zip_code_to="+zipCodeFinal;
        return JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers()));
    }

}
