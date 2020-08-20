package dominio.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class HttpClient {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(HttpClient.class);


    public static JSONObject execute(Map requestProperties) throws Exception{
        String method = (String) requestProperties.get("method");
        String uriWithQueryString = (String) requestProperties.get("uriWithQueryString");
        JSONObject headersJson = null;
        Object body = requestProperties.get("body");
        JSONObject response = new JSONObject();
        try {
            headersJson = (requestProperties.get("headers") != null && requestProperties.get("headers") instanceof Map) ? JsonUtils.mapToJSON((Map) requestProperties.get("headers")) : new JSONObject();
        } catch (JSONException je) {

        }
        try {
            if (body instanceof Map) {
                if (((Map) body).size() > 0) {
                    body = JsonUtils.mapToJSON((Map) body);
                } else {
                    body = null;
                }

            } else if (body instanceof java.util.List) {
                if (((java.util.List) body).size() > 0) {
                    body = JsonUtils.listToJSONArray((java.util.List) body);
                } else {
                    body = null;
                }

            }

        } catch (JSONException je) {

        }

        try {

            HttpURLConnection conection = null;
            if(requestProperties.get("proxyHost")!=null){
                String proxyHost = requestProperties.get("proxyHost").toString();
                Integer proxyPort = (requestProperties.get("proxyPort")!=null) ? (Integer) requestProperties.get("proxyPort") : 80;
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                if(requestProperties.get("baseUrl") != null){
                    conection = (HttpURLConnection) new URL(requestProperties.get("baseUrl") + uriWithQueryString).openConnection(proxy);
                }else{
                    conection = (HttpURLConnection) new URL(uriWithQueryString).openConnection(proxy);
                }
            }
            else{
                if(requestProperties.get("baseUrl") != null){
                    conection = (HttpURLConnection) new URL(requestProperties.get("baseUrl") + uriWithQueryString).openConnection();
                }else{
                    conection = (HttpURLConnection) new URL(uriWithQueryString).openConnection();
                }
            }

            int socketTimeout = (requestProperties.get("socketTimeout") != null) ? (Integer) requestProperties.get("socketTimeout") : 15000;
            int connectionTimeout = (requestProperties.get("connectionTimeout") != null) ? (Integer) requestProperties.get("connectionTimeout") : 3000;

            conection.setRequestMethod(method);
            conection.setConnectTimeout(connectionTimeout);
            conection.setReadTimeout(socketTimeout);

            Iterator<?> keys = headersJson.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                try {
                    String value = (String) headersJson.get(key);
                    conection.setRequestProperty(key, value);
                } catch (JSONException je) {
                    log("Error writing request header: " + key);
                }
            }

            //conection.setRequestProperty("Accept","*/*");
            if (conection.getRequestProperty("Accept") == null || conection.getRequestProperty("Accept").indexOf("text/html,application/xhtml+xml,application/xml")>=0) {
                conection.setRequestProperty("Accept", "application/json");
            }
            OutputStream os = null;
            if (body != null && body.toString().length() > 0) {
                if (body instanceof JSONObject || body instanceof JSONArray) {
                    conection.setRequestProperty("Accept", "application/json");
                    conection.setRequestProperty("Content-Type", "application/json");
                }

                conection.setRequestProperty("Content-Length", "" + Integer.toString(body.toString().getBytes().length));
                conection.setDoOutput(true);
                os = conection.getOutputStream();
                byte[] outputInBytes = null;
                outputInBytes = body.toString().getBytes("UTF-8");
                os.write(outputInBytes);
            } else {
                //conection.getOutputStream().write("-k --insecure".getBytes());
                conection.connect();
                //os.write("-k --insecure".getBytes());
            }
            int status = conection.getResponseCode();
            StringBuilder responseJSON = new StringBuilder();

            if (status < 400) {
                BufferedReader in = null;
                boolean encoded = false;
                try {
                    if (headersJson.has("Accept-Encoding") && headersJson.get("Accept-Encoding").toString().contains("gzip") && headersJson.has("Content-Encoding") && headersJson.get("Content-Encoding").toString().contains("gzip")) {
                        encoded = true;
                    }
                } catch (JSONException je) {

                }
                if (encoded) {
                    GZIPInputStream gzis = new GZIPInputStream(conection.getInputStream());
                    InputStreamReader reader = new InputStreamReader(gzis);
                    in = new BufferedReader(reader);
                } else {
                    in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                }


                String line = null;
                int l = 0;
                while ((line = in.readLine()) != null) {
                    if(l>0){
                        responseJSON.append("\n" + line);
                    }
                    else{
                        responseJSON.append(line);
                    }

                }

                in.close();

            } else {
                BufferedReader errorIn = null;

                errorIn = new BufferedReader(new InputStreamReader(conection.getErrorStream()));
                String errorLine = null;
                while ((errorLine = errorIn.readLine()) != null) {
                    responseJSON.append(errorLine);
                }
            }


            if (os != null) {
                os.close();
            }

            JSONObject headers = new JSONObject();
            Map headersMap = conection.getHeaderFields();
            Iterator headersMapIterator = headersMap.entrySet().iterator();
            while (headersMapIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) headersMapIterator.next();
                String headerName = (String) pair.getKey();
                Collection headerValue = (Collection) pair.getValue();
                try {
                    if (headerName != null && !"Transfer-Encoding".equals(headerName)) {
                        headers.put(headerName, headerValue.iterator().next());
                    }
                } catch (JSONException je) {

                }

            }

            try {
                if (responseJSON.toString().replaceAll(" ", "").startsWith("[")) {
                    JSONArray respBody = new JSONArray(responseJSON.toString());
                    response.put("body", respBody);
                } else {
                    JSONObject respBody = new JSONObject(responseJSON.toString());
                    //if (responseJSON.toString().replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "").length() > respBody.toString().length()) {
                    //	response.put("body", responseJSON.toString());
                    //} else {
                    response.put("body", respBody);
                    //}

                }

                response.put("status", status);
                response.put("headers", headers);
            } catch (JSONException je) {
                log("Exception converting responseJSON " + je);
                try{
                    response.put("status", status);
                    response.put("headers", headers);
                    response.put("body", responseJSON.toString());
                }catch(Exception ignore){}

            }

        } catch (MalformedURLException e) {
            log(e);
        } catch (IOException e) {
            try{
                log(e);
                response.put("status", 503);
                response.put("headers", new JSONObject());
                JSONObject responseJSON = new JSONObject();
                responseJSON.put("exception", e);
                response.put("body", responseJSON.toString());
            }
            catch(JSONException je){}

        }

        return response;
    }

    private static void log(Object data) {
        log.info(data);
    }

}
