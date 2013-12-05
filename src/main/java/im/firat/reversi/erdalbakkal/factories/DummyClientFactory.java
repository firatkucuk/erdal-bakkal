
package im.firat.reversi.erdalbakkal.factories;

import im.firat.reversi.erdalbakkal.clients.DummyClient;
import im.firat.reversi.erdalbakkal.clients.GameClient;
import im.firat.reversi.erdalbakkal.services.CalculationService;
import java.util.concurrent.ExecutorService;



public final class DummyClientFactory {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private DummyClientFactory() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static GameClient createInstance(final String baseAddress, final int player, final ExecutorService executor,
            final CalculationService calculationService) {

        DummyClient client = new DummyClient(player, executor, calculationService);
        client.start();

        return client;
    }
}
