
package im.firat.reversi.erdalbakkal;


import im.firat.reversi.erdalbakkal.clients.GameClient;
import im.firat.reversi.erdalbakkal.factories.AlwaysFirstDummyClientFactory;
import im.firat.reversi.erdalbakkal.factories.MaxDummyClientFactory;
import im.firat.reversi.erdalbakkal.factories.RandomDummyClientFactory;
import im.firat.reversi.erdalbakkal.factories.RemoteClientFactory;
import im.firat.reversi.erdalbakkal.services.*;
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

    public static void main(String[] args) {

        // CONFIGURATION SERVICE --------
        final String baseAddress = "http://10.1.35.99:8080/reversi-stadium/rest/";
        final String authCode    = "bnaa0745";
        final int    player      = GameService.BLACK_PLAYER;


        final ExecutorService executor = Executors.newFixedThreadPool(10);
        GameClient            client;
        CalculationService    service;

        // ---
        // client = RemoteClientFactory.createInstance(baseAddress, player);
        client = RandomDummyClientFactory.createInstance(baseAddress, player, executor);
        // client = AlwaysFirstDummyClientFactory.createInstance(baseAddress, player, executor);
        // client = MaxDummyClientFactory.createInstance(baseAddress, player, executor);

        // ---
        // service = new RandomCalculationServiceImpl();
        // service = new AlwaysFirstServiceImpl();
        // service = new MaxCalculationServiceImpl();
        // service = new MultiThreadedMaxCalculationServiceImpl();
        service = new MinMaxCalculationServiceImpl();

        final Timer     timer      = new Timer();
        final TimerTask pollerTask = new PollerTask(authCode, player, client, service, timer, executor);

        timer.scheduleAtFixedRate(pollerTask, 500, 500);
    }
}
