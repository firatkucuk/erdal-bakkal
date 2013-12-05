
package im.firat.reversi.erdalbakkal;


import im.firat.reversi.erdalbakkal.clients.GameClient;
import im.firat.reversi.erdalbakkal.factories.DummyClientFactory;
import im.firat.reversi.erdalbakkal.services.CalculationService;
import im.firat.reversi.erdalbakkal.services.MinMaxCalculationServiceImpl;
import im.firat.reversi.erdalbakkal.services.RandomCalculationServiceImpl;
import im.firat.reversi.erdalbakkal.threads.PollerTask;
import im.firat.reversi.services.GameService;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public final class Main {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private Main() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static void main(final String[] args) {

        // CONFIGURATION SERVICE --------
        final String baseAddress = "http://localhost:8080/reversi-stadium/rest/";
        final String authCode    = "nabr7272";
        final int    player      = GameService.WHITE_PLAYER;

        final ExecutorService executor          = Executors.newFixedThreadPool(10);
        GameClient            client;
        CalculationService    service;
        CalculationService    simulationService;

        // ---
        service = new MinMaxCalculationServiceImpl();
        // service = new AlwaysFirstServiceImpl();
        // service = new MaxCalculationServiceImpl();
        // service = new MultiThreadedMaxCalculationServiceImpl();
        // service = new MinMaxCalculationServiceImpl();
        // service = new MultiThreadedMinMaxCalculationServiceImpl();

        // ---
        simulationService = new RandomCalculationServiceImpl();
        client            = DummyClientFactory.createInstance(baseAddress, player, executor, simulationService);
        // client = RemoteClientFactory.createInstance(baseAddress, player);

        final Timer     timer      = new Timer();
        final TimerTask pollerTask = new PollerTask(authCode, player, client, service, timer, executor);

        timer.scheduleAtFixedRate(pollerTask, 500, 500);
    }
}
