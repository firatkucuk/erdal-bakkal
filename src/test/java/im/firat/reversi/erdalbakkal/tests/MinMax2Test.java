
package im.firat.reversi.erdalbakkal.tests;


import im.firat.reversi.domain.Game;
import im.firat.reversi.erdalbakkal.algorithms.MinMaxAlgorithm;
import im.firat.reversi.erdalbakkal.algorithms.MinMaxAlgorithm2;
import im.firat.reversi.exceptions.AlreadyStartedException;
import im.firat.reversi.exceptions.IllegalMoveException;
import im.firat.reversi.exceptions.NotStartedException;
import im.firat.reversi.exceptions.WrongOrderException;
import im.firat.reversi.services.GameService;
import java.util.Arrays;
import org.junit.Test;



public class MinMax2Test {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public MinMax2Test() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Test
    public void computeNextMoveScenarioTest() {

        try {
            Game             game             = new Game();
            GameService      gameService      = new GameService();
            MinMaxAlgorithm2 minMax2Algorithm = new MinMaxAlgorithm2();

            gameService.start(game);


            // move c3 score test

            game.setCurrentPlayer(GameService.WHITE_PLAYER);
            game.setBoardState(Arrays.asList(

                    //                     0  1  2  3  4  5  6  7
                    //                     a  b  c  d  e  f  g  h
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 1 0
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 2 1
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 3 2
                    Arrays.<Integer>asList(0, 0, 0, 2, 1, 2, 1, 0), // 4 3
                    Arrays.<Integer>asList(0, 0, 0, 1, 1, 1, 2, 0), // 5 4
                    Arrays.<Integer>asList(0, 0, 0, 0, 1, 1, 1, 0), // 6 5
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 1, 0, 0), // 7 6
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0)  // 8 7
                    ));
            gameService.move(game, "g7", GameService.WHITE_PLAYER);
            System.out.println(minMax2Algorithm.computeNextMove(game, GameService.BLACK_PLAYER, null));
        } catch (AlreadyStartedException e) {
            e.printStackTrace();
        } catch (WrongOrderException e) {
            e.printStackTrace();
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        } catch (NotStartedException e) {
            e.printStackTrace();
        }
    }
}
