package dominio.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.servlet.http.HttpServletResponse;


import java.sql.Clob;
import java.util.*;
import java.util.Map.Entry;

public class JsonUtils {

    public static JSONObject getLinkedHashMapToJson(JSONObject rest) throws Exception {
        int status = Integer.valueOf(rest.get("status").toString());
        if (status == HttpServletResponse.SC_OK) {
            if(rest.has("body")) {
                if(rest.get("body").getClass().toString().equals("class org.json.JSONObject")){
                    return (JSONObject) rest.get("body");
                }
                return JsonUtils.mapToJSON((LinkedHashMap) rest.get("body"));
            } else return new JSONObject();
        } else if (status == HttpServletResponse.SC_NOT_FOUND) {
            return null;
        } else if (status == HttpServletResponse.SC_BAD_REQUEST){
            return null;
        }else {
            return null; //o poner una api exception
        }

    }

    public static JSONArray getResponseJsonArray(JSONObject rest) throws Exception {
        int status = (int) rest.get("status");
        if (status == HttpServletResponse.SC_OK) {
            JSONArray restJsonArray = isJsonArray(rest);
            return restJsonArray != null ? restJsonArray: JsonUtils.mapListToJSONArray((ArrayList<HashMap>)rest.get("body"));
        } else if (status == HttpServletResponse.SC_NOT_FOUND) {
            return null;
        } else if (status == HttpServletResponse.SC_BAD_REQUEST){
            return null;
        }else {
            return null; //o poner una api exception

        }

    }


    private static JSONArray isJsonArray (Object obj){
        try{
            return ((JSONObject)obj).getJSONArray("body");
        }
        catch (Exception ex){
            return null;
        }
    }


    public static Map<String, Object> jsonToMap(JSONObject o) throws JSONException {
        Iterator ji = o.keys();
        Map<String, Object> b = new LinkedHashMap<String, Object>();
        while (ji.hasNext()) {
            String key = (String) ji.next();
            Object val = o.get(key);
            if (val.getClass() == JSONObject.class)
                b.put(key, jsonToMap((JSONObject) val));
            else if (val.getClass() == JSONArray.class) {
                List<Object> l = new ArrayList<Object>();
                JSONArray arr = (JSONArray) val;
                for (int a = 0; a < arr.length(); a++) {
                    Object element = arr.get(a);
                    if (element instanceof JSONObject)
                        l.add(jsonToMap((JSONObject) element));
                    else if (JSONObject.NULL != element)
                        l.add(element);
                    else
                        l.add(null);
                }
                b.put(key, l);
            } else
                b.put(key, val);
        }
        return b;
    }


    public static JSONObject mapToJSON(Map<?, ?> mapToConvert) throws JSONException, Exception {
        JSONObject result = new JSONObject();

        for (Entry<?, ?> entry : mapToConvert.entrySet()) {
            if (entry.getValue() instanceof Collection) {
                result.put((String) entry.getKey(), mapListToJSONArray((Collection<?>) entry.getValue()));
            } else if (entry.getValue() instanceof Map) {
                result.put((String) entry.getKey(), mapToJSON((Map<?, ?>) entry.getValue()));
            } else if (entry.getValue() instanceof Clob) {
                result.put((String) entry.getKey(), getString(entry.getValue()));
            } else {
                result.put((String) entry.getKey(), entry.getValue());
            }
        }

        return result;
    }

    public static String getString(Object obj) throws Exception {
        if(obj instanceof String){
            return obj.toString();
        }else {
            Clob clob = (Clob) obj;
            return clob.getSubString(1, (int) clob.length());
        }
    }

    public static JSONArray mapListToJSONArray(Collection<?> collection) throws JSONException, Exception {
        JSONArray result = new JSONArray();

        for (Object object : collection) {
            if (object instanceof Map) {
                result.put(mapToJSON((Map<?, ?>) object));
            } else {
                result.put(object);
            }
        }

        return result;
    }

    public static JSONArray listToJSONArray(Collection<?> collection) throws JSONException, Exception {
        JSONArray result = new JSONArray();

        for (Object object : collection) {
            if (object instanceof Map) {
                result.put(mapToJSON((Map<?, ?>) object));
            } else {
                result.put(object);
            }
        }

        return result;
    }
}
