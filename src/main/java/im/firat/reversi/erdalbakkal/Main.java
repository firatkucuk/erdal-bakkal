
package im.firat.reversi.erdalbakkal;


import im.firat.reversi.erdalbakkal.algorithms.Algorithm;
import im.firat.reversi.erdalbakkal.core.AlgorithmTester;
import im.firat.reversi.erdalbakkal.core.GameClient;
import im.firat.reversi.erdalbakkal.exceptions.ConfigurationKeyNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;



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
            final String              mode          = configuration.get("mode").toString().toLowerCase(Locale.ENGLISH);
            final Map<String, Object> settings      = getMap(configuration, "settings");

            if (settings == null) {
                System.err.println("No settings found!");

                return;
            }

            if (mode.equals("test")) {
                final int                 iterationCount   = getInt(settings, "iterationCount");
                final int                 threadPoolSize   = getInt(settings, "threadPoolSize");
                final Map<String, Object> player1          = getMap(settings, "player1");
                final Map<String, Object> player2          = getMap(settings, "player2");
                final Algorithm           player1Algorithm;
                final Algorithm           player2Algorithm;

                player1Algorithm = (Algorithm) Class.forName(player1.get("algorithm").toString()).newInstance();
                player2Algorithm = (Algorithm) Class.forName(player2.get("algorithm").toString()).newInstance();

                new AlgorithmTester(player1Algorithm, player2Algorithm).test(iterationCount, threadPoolSize);
            } else if (mode.equals("stadiumClient")) {
                final String                    baseAddress = configuration.get("baseAddress").toString();
                final String                    authCode    = configuration.get("authCode").toString();
                final List<JacksonJsonProvider> providers   = Arrays.asList(new JacksonJsonProvider());
                final GameClient                client      = JAXRSClientFactory.create(baseAddress, GameClient.class,
                    providers);
            } else {
                System.err.println("No valid mode found!");
            }


            //
            //            if (!blackPlayer.get("type").toString().toLowerCase(Locale.ENGLISH).equals("remote")) {
            //                blackClient = DummyClientFactory.createInstance(GameService.BLACK_PLAYER, executor, blackAlgorithm);
            //            } else {
            //                blackClient = RemoteClientFactory.createInstance(baseAddress, GameService.BLACK_PLAYER, blackAlgorithm);
            //            }
            //
            //            if (!whitePlayer.get("type").toString().toLowerCase(Locale.ENGLISH).equals("remote")) {
            //                whiteClient = DummyClientFactory.createInstance(GameService.BLACK_PLAYER, executor, whiteAlgorithm);
            //            } else {
            //                whiteClient = RemoteClientFactory.createInstance(baseAddress, GameService.BLACK_PLAYER, whiteAlgorithm);
            //            }
            //
            //            //            for (int i = 0; i < iterationCount; i++) {
            //            //
            //            //            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ConfigurationKeyNotFoundException e) {
            e.printStackTrace();
        }

        // client = DummyClientFactory.createInstance(player, executor, simulationService);
        // client = RemoteClientFactory.createInstance(baseAddress, player);
        //
        // final Timer     timer      = new Timer();
        // final TimerTask pollerTask = new PollerTask(authCode, player, client, service, timer, executor);
        //
        // timer.scheduleAtFixedRate(pollerTask, 500, 500);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private static int getInt(final Map<String, Object> configuration, final String key)
        throws ConfigurationKeyNotFoundException {

        final Object value = configuration.get(key);

        if (value == null) {
            throw new ConfigurationKeyNotFoundException("Configuration key '" + key + "' not found!");
        }

        return Integer.parseInt(value.toString());
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private static Map<String, Object> getMap(final Map<String, Object> configuration, final String key)
        throws ConfigurationKeyNotFoundException {

        final Object value = configuration.get(key);

        if (value == null) {
            throw new ConfigurationKeyNotFoundException("Configuration key '" + key + "' not found!");
        }

        return (Map<String, Object>) value;
    }
}
