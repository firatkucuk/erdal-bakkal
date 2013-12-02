
package im.firat.reversi.erdalbakkal.factories;

import im.firat.reversi.erdalbakkal.clients.GameClient;
import im.firat.reversi.erdalbakkal.clients.MaxDummyClient;
import java.util.concurrent.ExecutorService;



public final class MultiThreadedMaxDummyClientFactory {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private MultiThreadedMaxDummyClientFactory() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static GameClient createInstance(final String baseAddress, final int player,
            final ExecutorService executor) {

        MaxDummyClient client = new MaxDummyClient(player, executor);
        client.start();

        return client;
    }
}
