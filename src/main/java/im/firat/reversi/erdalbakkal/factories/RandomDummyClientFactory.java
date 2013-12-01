
package im.firat.reversi.erdalbakkal.factories;


import im.firat.reversi.erdalbakkal.clients.GameClient;
import im.firat.reversi.erdalbakkal.clients.RandomDummyClient;



public class RandomDummyClientFactory {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private RandomDummyClientFactory() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static GameClient createInstance(final String baseAddress, final int player) {

        RandomDummyClient client = new RandomDummyClient(player);
        client.start();

        return client;
    }
}
