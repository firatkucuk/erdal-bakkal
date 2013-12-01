
package im.firat.reversi.erdalbakkal;


import im.firat.reversi.erdalbakkal.clients.GameClient;
import im.firat.reversi.erdalbakkal.factories.PredictBestMoveClientFactory;
import im.firat.reversi.erdalbakkal.factories.RandomDummyClientFactory;
import im.firat.reversi.erdalbakkal.factories.RemoteClientFactory;
import im.firat.reversi.erdalbakkal.services.CalculationService;
import im.firat.reversi.erdalbakkal.services.PBMAlternativeCalculationServiceImpl;
import im.firat.reversi.erdalbakkal.services.PredictBestMoveCalculationServiceImpl;
import im.firat.reversi.erdalbakkal.services.RandomCalculationServiceImpl;
import im.firat.reversi.erdalbakkal.threads.PollerTask;
import im.firat.reversi.services.GameService;
import java.util.Timer;
import java.util.TimerTask;



public final class Main {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private Main() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static void main(String[] args) {

        final String baseAddress = "http://localhost:8080/reversi-stadium/rest/";
        final String authCode    = "jbai4556";
        final int    player      = GameService.BLACK_PLAYER;

        GameClient         client;
        CalculationService service;

        // ---
        // client = RemoteClientFactory.createInstance(baseAddress, player);
        // client = RandomDummyClientFactory.createInstance(baseAddress, player);
        client = PredictBestMoveClientFactory.createInstance(baseAddress, player);

        // ---
        // service = new RandomCalculationServiceImpl();
        // service = new PredictBestMoveCalculationServiceImpl();
        service = new PBMAlternativeCalculationServiceImpl();

        final Timer     timer      = new Timer();
        final TimerTask pollerTask = new PollerTask(authCode, player, client, service, timer);

        timer.scheduleAtFixedRate(pollerTask, 500, 500);
    }
}
