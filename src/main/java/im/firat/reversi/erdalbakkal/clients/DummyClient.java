
package im.firat.reversi.erdalbakkal.clients;


import im.firat.reversi.domain.Authorization;
import im.firat.reversi.domain.Game;
import im.firat.reversi.erdalbakkal.datastore.SingletonGame;
import im.firat.reversi.erdalbakkal.services.AlwaysFirstServiceImpl;
import im.firat.reversi.erdalbakkal.services.CalculationService;
import im.firat.reversi.exceptions.AlreadyStartedException;
import im.firat.reversi.exceptions.IllegalMoveException;
import im.firat.reversi.exceptions.NotStartedException;
import im.firat.reversi.exceptions.WrongOrderException;
import im.firat.reversi.services.GameService;
import java.util.concurrent.ExecutorService;
import javax.ws.rs.PathParam;



public final class DummyClient implements GameClient {



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private final CalculationService calculationService;
    private final ExecutorService    executor;
    private final int                otherPlayer;
    private final int                player;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public DummyClient(final int player, final ExecutorService executor, final CalculationService calculationService) {

        this.player             = player;
        this.executor           = executor;
        this.calculationService = calculationService;
        this.otherPlayer        = player == GameService.BLACK_PLAYER ? GameService.WHITE_PLAYER
                                                                     : GameService.BLACK_PLAYER;
    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public Game cancel(@PathParam("cancellationCode") String cancellationCode) {

        Game        game        = SingletonGame.getInstance();
        GameService gameService = new GameService();

        try {
            gameService.cancel(game);
        } catch (NotStartedException e) {
            e.printStackTrace();
        }

        return game;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    public Game move(@PathParam("authCode") String authCode,
            @PathParam("piece") String piece) {

        Game        game        = SingletonGame.getInstance();
        GameService gameService = new GameService();

        try {
            gameService.move(game, piece, player);
            System.out.println("player: " + player);
            System.out.println("move  : " + piece);
            System.out.println(game);

            if (game.isStarted() && !game.isCancelled()) {

                while (game.getCurrentPlayer() == otherPlayer) {
                    String nextMove = calculationService.computeNextMove(game, otherPlayer, executor);
                    gameService.move(game, nextMove, otherPlayer);
                    System.out.println("player: " + otherPlayer);
                    System.out.println("move  : " + nextMove);
                    System.out.println(game);
                }
            }
        } catch (NotStartedException e) {
            e.printStackTrace();
        } catch (WrongOrderException e) {
            e.printStackTrace();
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }

        return game;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    public Authorization start() {

        try {
            Game        game        = SingletonGame.getInstance();
            GameService gameService = new GameService();

            gameService.start(game);
        } catch (AlreadyStartedException e) {
            e.printStackTrace();
        }

        return null;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    public Game status() {

        return SingletonGame.getInstance();
    }
}
