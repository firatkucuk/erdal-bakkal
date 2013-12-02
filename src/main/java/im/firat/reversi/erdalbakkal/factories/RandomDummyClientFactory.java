
package im.firat.reversi.erdalbakkal.factories;


import im.firat.reversi.erdalbakkal.clients.GameClient;
import im.firat.reversi.erdalbakkal.clients.RandomDummyClient;
import java.util.concurrent.ExecutorService;



public class RandomDummyClientFactory {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private RandomDummyClientFactory() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static GameClient createInstance(final String baseAddress, final int player,
            final ExecutorService executor) {

        RandomDummyClient client = new RandomDummyClient(player, executor);
        client.start();

        return client;
    }
}
