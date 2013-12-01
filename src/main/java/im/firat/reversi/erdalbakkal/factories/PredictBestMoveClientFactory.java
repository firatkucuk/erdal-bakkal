
package im.firat.reversi.erdalbakkal.factories;

import im.firat.reversi.erdalbakkal.clients.GameClient;
import im.firat.reversi.erdalbakkal.clients.PredictBestMoveClient;



public final class PredictBestMoveClientFactory {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private PredictBestMoveClientFactory() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static GameClient createInstance(final String baseAddress, final int player) {

        PredictBestMoveClient client = new PredictBestMoveClient(player);
        client.start();

        return client;
    }
}
