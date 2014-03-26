
package im.firat.reversi.erdalbakkal.utils;


import im.firat.reversi.erdalbakkal.exceptions.ConfigurationKeyNotFoundException;
import java.util.Locale;
import java.util.Map;



public final class ConfigUtils {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private ConfigUtils() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static int getInt(final Map<String, Object> configuration, final String key)
        throws ConfigurationKeyNotFoundException {

        return Integer.parseInt(getStr(configuration, key));
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static Map<String, Object> getMap(final Map<String, Object> configuration, final String key)
        throws ConfigurationKeyNotFoundException {

        final Object value = configuration.get(key);

        if (value == null) {
            throw new ConfigurationKeyNotFoundException("Configuration key '" + key + "' not found!");
        }

        return (Map<String, Object>) value;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static String getStr(Map<String, Object> configuration, String key)
        throws ConfigurationKeyNotFoundException {

        final Object value = configuration.get(key);

        if (value == null) {
            throw new ConfigurationKeyNotFoundException("Configuration key '" + key + "' not found!");
        }

        return value.toString();
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static String getStrLower(Map<String, Object> configuration, String key)
        throws ConfigurationKeyNotFoundException {

        return getStr(configuration, key).toLowerCase(Locale.ENGLISH);
    }
}
