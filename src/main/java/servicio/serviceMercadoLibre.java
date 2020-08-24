package servicio;


import com.mercadolibre.restclient.http.Headers;
import dominio.utils.*;

import org.json.JSONArray;
import org.json.JSONObject;


public enum serviceMercadoLibre {

    INSTANCE;

    private RestClientML restClient = RestClientML.INSTANCE;


    public JSONArray getCountries() throws Exception {
        String url = "https://api.mercadolibre.com/classified_locations/countries";
        return JsonUtils.getResponseJsonArray(restClient.get(url, new Headers()));
    }

    public JSONObject getCountry(String country) throws Exception {
        String url = "https://api.mercadolibre.com/classified_locations/countries/" + country;
        return JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers()));
    }

    public JSONObject getState(String state) throws Exception {
        String url = "https://api.mercadolibre.com/classified_locations/states/" + state;
        return JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers()));
    }

    public JSONObject getCity(String city) throws Exception {
        String url = "https://api.mercadolibre.com/classified_locations/cities/" + city;
        return JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers()));
    }

    public JSONArray getMonedas() throws Exception {
        String url = "https://api.mercadolibre.com/currencies/";
        return JsonUtils.getResponseJsonArray(restClient.get(url, new Headers()));
    }


    public JSONObject getMoneda(String moneda) throws Exception {
        String url = "https://api.mercadolibre.com/currencies/" + moneda;
        return JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers()));
    }

    public JSONObject getConversion(String monedaInicio, String monedaFin) throws Exception {
        String url = "https://api.mercadolibre.com/currency_conversions/search?from="+monedaInicio+"&to="+monedaFin;
        return JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers()));
    }

    public JSONObject getCountryWithZipCode(String country, String zipCode) throws Exception {
        String url = "https://api.mercadolibre.com/countries/"+country+"/zip_codes/"+zipCode;
        return JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers()));
    }

    public JSONObject getCountryWithZipCode(String country, String zipCodeInit, String zipCodeFinal) throws Exception {
        String url = "https://api.mercadolibre.com//country/"+country+"/zip_codes/search_between?zip_code_from="+zipCodeInit+"&zip_code_to="+zipCodeFinal;
        return JsonUtils.getLinkedHashMapToJson(restClient.get(url, new Headers()));
    }

}
