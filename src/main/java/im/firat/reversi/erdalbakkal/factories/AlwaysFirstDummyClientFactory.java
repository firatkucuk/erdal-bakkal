
package im.firat.reversi.erdalbakkal.factories;


import im.firat.reversi.erdalbakkal.clients.AlwaysFirstDummyClient;
import im.firat.reversi.erdalbakkal.clients.GameClient;
import im.firat.reversi.erdalbakkal.clients.RandomDummyClient;
import java.util.concurrent.ExecutorService;



public class AlwaysFirstDummyClientFactory {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private AlwaysFirstDummyClientFactory() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static GameClient createInstance(final String baseAddress, final int player,
            final ExecutorService executor) {

        AlwaysFirstDummyClient client = new AlwaysFirstDummyClient(player, executor);
        client.start();

        return client;
    }
}
