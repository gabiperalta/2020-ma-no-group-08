package dominio.utils;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Config {
    private static final String SEP = "-";

    private static Configuration config;
    public static final String SCOPE;

    static {
        try {
            CompositeConfiguration configuration = new CompositeConfiguration();

            PropertiesConfiguration props = new PropertiesConfiguration();
            props.read(
                    new BufferedReader(
                        new InputStreamReader(
                                Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")
                        )
                    )
            );

            configuration.addConfiguration(props);
            config = configuration;

            SCOPE = getScope();

        } catch (ConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static <T> T getConfig(String key, String scope, Function<String,T> f) {
        if (!config.containsKey(key)) {
            key += SEP + scope;
        }

        return f.apply(key);
    }


    protected static <T> T getConfig(String key , Function<String,T> f) {
        return getConfig(key,SCOPE,f);
    }

    public static Set<String> groupKey(String key) {
        Set<String> output = new HashSet<>();
        Iterator<String> it = config.getKeys();
        while (it.hasNext()) {
            String k = it.next();
            if (k.startsWith(key)) {
                String[] split = k.split("-");
                output.add(split[split.length - 1]);
            }
        }
        return output;
    }

    public static Set<String> getValues(String keyPrefix) {
        Set<String> output = new HashSet<>();
        Iterator<String> it = config.getKeys();
        while (it.hasNext()) {
            String k = it.next();
            if (k.startsWith(keyPrefix)) {
                output.addAll(config.getList(k).stream().map(Object::toString).collect(Collectors.toList()));
            }
        }
        return output;
    }

    public static int getInt(String key) {
        return getConfig(key,config::getInt);
    }

    public static String getString(String key){
        return getConfig(key,config::getString);
    }

    public static List<Object> getList (String key){
        return getConfig(key,config::getList);
    }

    public static long getLong(String key) {
        return getConfig(key,config::getLong);
    }

    public static boolean getBoolean(String key) {
        return getConfig(key,config::getBoolean);
    }

    private static String getScope() {
        return System.getenv("SCOPE") != null ? System.getenv("SCOPE") : "";
    }

    public static String getFromMap(String configKey, String mapKey) {
        List<Object> values = getConfig(configKey,config::getList);
        return values != null ? values.stream().map(pair -> pair.toString().split(":", 2)).filter(kv -> kv[0].startsWith(mapKey)).findFirst().map(kv -> kv[1]).orElse(null) : null;
    }

    public static Boolean has(String key){
        return config.containsKey(key);
    }

}