
package im.firat.reversi.erdalbakkal.threads;


import im.firat.reversi.domain.Game;
import im.firat.reversi.erdalbakkal.algorithms.Algorithm;
import im.firat.reversi.erdalbakkal.core.GameClient;
import im.firat.reversi.erdalbakkal.utils.PrintUtils;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;



public class PollerTask extends TimerTask {



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private final String          authCode;
    private final GameClient      client;
    private final ExecutorService executor;
    private final int             player;
    private final Algorithm       service;
    private final Timer           timer;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public PollerTask(final String authCode, final int player, final GameClient client, final Algorithm service,
            final Timer timer, final ExecutorService executor) {

        this.authCode = authCode;
        this.player   = player;
        this.client   = client;
        this.service  = service;
        this.timer    = timer;
        this.executor = executor;
    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public void run() {

        Game game = client.status();

        if (game.isCancelled()) {
            timer.cancel();
            executor.shutdown();
        } else if (!game.isStarted()) {
            timer.cancel();
            executor.shutdown();
            PrintUtils.printScore(game);
        } else if (game.getCurrentPlayer() == player) {
            String nextMove = service.computeNextMove(game, player, executor);

            if (nextMove != null) {
                client.move(authCode, nextMove);
            }
        }
    }
}
