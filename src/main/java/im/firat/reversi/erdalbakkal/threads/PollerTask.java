
package im.firat.reversi.erdalbakkal.threads;


import im.firat.reversi.domain.Game;
import im.firat.reversi.erdalbakkal.algorithms.Algorithm;
import im.firat.reversi.erdalbakkal.core.GameClient;
import im.firat.reversi.erdalbakkal.exceptions.ConfigurationKeyNotFoundException;
import im.firat.reversi.erdalbakkal.utils.PrintUtils;
import im.firat.reversi.services.GameService;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import static im.firat.reversi.erdalbakkal.utils.ConfigUtils.*;
import static im.firat.reversi.erdalbakkal.utils.ConfigUtils.getInt;
import static im.firat.reversi.erdalbakkal.utils.ConfigUtils.getStr;



public class PollerTask extends TimerTask {



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private Algorithm       algorithm;
    private String          authCode;
    private GameClient      client;
    private ExecutorService executor;
    private int             me;
    private Timer           timer;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public PollerTask(final Map<String, Object> settings, final Timer timer) {

        final List<JacksonJsonProvider> providers = Arrays.asList(new JacksonJsonProvider());

        try {

            if (getStrLower(settings, "player").equals("black")) {
                this.me = GameService.BLACK_PLAYER;
            } else {
                this.me = GameService.WHITE_PLAYER;
            }

            this.timer     = timer;
            this.client    = JAXRSClientFactory.create(getStr(settings, "baseAddress"), GameClient.class, providers);
            this.authCode  = getStr(settings, "authCode");
            this.executor  = Executors.newFixedThreadPool(getInt(settings, "threadPoolSize"));
            this.algorithm = (Algorithm) Class.forName(getStr(settings, "algorithm")).newInstance();
        } catch (ConfigurationKeyNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
        } else if (game.getCurrentPlayer() == me) {
            String nextMove = algorithm.computeNextMove(game, me, executor);

            if (nextMove != null) {
                client.move(authCode, nextMove);
            }
        }
    }
}
