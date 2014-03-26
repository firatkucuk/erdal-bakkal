
package im.firat.reversi.erdalbakkal;


import im.firat.reversi.erdalbakkal.core.AlgorithmComparator;
import im.firat.reversi.erdalbakkal.exceptions.ConfigurationKeyNotFoundException;
import im.firat.reversi.erdalbakkal.threads.PollerTask;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.codehaus.jackson.map.ObjectMapper;

import static im.firat.reversi.erdalbakkal.utils.ConfigUtils.getMap;
import static im.firat.reversi.erdalbakkal.utils.ConfigUtils.getStrLower;



public final class Main {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private Main() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static void main(final String[] args) {

        if (args.length != 1) {
            System.out.println("Usage:\n  erdalbakkal <configuration file path>");

            return;
        }

        final String configurationPath = args[0];
        final File   configurationFile = new File(configurationPath);

        if (!configurationFile.exists()) {
            System.out.println("No such configuration file.");

            return;
        } else if (!configurationFile.canRead()) {
            System.out.println("Cannot read configuration file.");

            return;
        } else if (!configurationFile.isFile()) {
            System.out.println("Configuration file is not ordinary file.");

            return;
        }

        final ObjectMapper mapper = new ObjectMapper();

        try {
            final Map<String, Object> configuration = mapper.readValue(configurationFile, Map.class);
            final String              mode          = getStrLower(configuration, "mode");
            final Map<String, Object> settings      = getMap(configuration, "settings");

            if (settings == null) {
                System.err.println("No settings found!");

                return;
            }

            if (mode.equals("test")) {
                new AlgorithmComparator(settings).test();
            } else if (mode.equals("stadiumclient")) {

                final Timer     timer      = new Timer();
                final TimerTask pollerTask = new PollerTask(settings, timer);

                timer.scheduleAtFixedRate(pollerTask, 500, 500);
            } else {
                System.err.println("No valid mode found!");
            }                                                  // end if-else
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ConfigurationKeyNotFoundException e) {
            e.printStackTrace();
        }
    }
}
