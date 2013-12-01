
package im.firat.reversi.erdalbakkal.threads;


import im.firat.reversi.domain.Game;
import im.firat.reversi.erdalbakkal.clients.GameClient;
import im.firat.reversi.erdalbakkal.core.PrintUtils;
import im.firat.reversi.erdalbakkal.services.CalculationService;
import im.firat.reversi.services.GameService;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class PollerTask extends TimerTask {



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private final String             authCode;
    private final GameClient         client;
    private final int                player;
    private final CalculationService service;
    private final Timer              timer;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public PollerTask(final String authCode, final int player, final GameClient client,
            final CalculationService service, final Timer timer) {

        this.authCode = authCode;
        this.player   = player;
        this.client   = client;
        this.service  = service;
        this.timer    = timer;
    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public void run() {

        Game game = client.status();

        if (game.isCancelled()) {
            timer.cancel();
        } else if (!game.isStarted()) {
            timer.cancel();
            PrintUtils.printScore(game);
        } else if (game.getCurrentPlayer() == player) {
            client.move(authCode, service.computeNextMove(game, player));
            System.out.println(game);
        }
    }
}
