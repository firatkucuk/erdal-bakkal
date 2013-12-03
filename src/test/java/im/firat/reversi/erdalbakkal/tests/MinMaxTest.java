
package im.firat.reversi.erdalbakkal.tests;


import im.firat.reversi.domain.Game;
import im.firat.reversi.erdalbakkal.beans.GameNode;
import im.firat.reversi.erdalbakkal.services.MinMaxCalculationServiceImpl;
import im.firat.reversi.exceptions.AlreadyStartedException;
import im.firat.reversi.exceptions.IllegalMoveException;
import im.firat.reversi.exceptions.NotStartedException;
import im.firat.reversi.exceptions.WrongOrderException;
import im.firat.reversi.services.GameService;
import java.util.Arrays;
import org.junit.Test;



public class MinMaxTest {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public MinMaxTest() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Test
    public void computeScoreScenarioTest() {

        try {
            Game                         game          = new Game();
            GameService                  gameService   = new GameService();
            MinMaxCalculationServiceImpl minMaxService = new MinMaxCalculationServiceImpl();

            gameService.start(game);


            // move c3 score test

            game.setCurrentPlayer(GameService.BLACK_PLAYER);
            game.setBoardState(Arrays.asList(

                    //                     0  1  2  3  4  5  6  7
                    //                     a  b  c  d  e  f  g  h
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 1 0
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 2 1
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 3 2
                    Arrays.<Integer>asList(0, 0, 0, 2, 1, 0, 0, 0), // 4 3
                    Arrays.<Integer>asList(0, 0, 0, 2, 1, 1, 0, 0), // 5 4
                    Arrays.<Integer>asList(0, 0, 0, 2, 0, 0, 0, 0), // 6 5
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 7 6
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0)  // 8 7
                    ));
            gameService.move(game, "c3", GameService.BLACK_PLAYER);
            System.out.println(minMaxService.computeScore(game, GameService.BLACK_PLAYER));

            // move c4 score test

            game.setCurrentPlayer(GameService.BLACK_PLAYER);
            game.setBoardState(Arrays.asList(

                    //                     0  1  2  3  4  5  6  7
                    //                     a  b  c  d  e  f  g  h
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 1 0
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 2 1
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 3 2
                    Arrays.<Integer>asList(0, 0, 0, 2, 1, 0, 0, 0), // 4 3
                    Arrays.<Integer>asList(0, 0, 0, 2, 1, 1, 0, 0), // 5 4
                    Arrays.<Integer>asList(0, 0, 0, 2, 0, 0, 0, 0), // 6 5
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 7 6
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0)  // 8 7
                    ));
            gameService.move(game, "c4", GameService.BLACK_PLAYER);
            System.out.println(minMaxService.computeScore(game, GameService.BLACK_PLAYER));

            // move c4 score test

            game.setCurrentPlayer(GameService.BLACK_PLAYER);
            game.setBoardState(Arrays.asList(

                    //                     0  1  2  3  4  5  6  7
                    //                     a  b  c  d  e  f  g  h
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 1 0
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 2 1
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 3 2
                    Arrays.<Integer>asList(0, 0, 0, 2, 1, 0, 0, 0), // 4 3
                    Arrays.<Integer>asList(0, 0, 0, 2, 1, 1, 0, 0), // 5 4
                    Arrays.<Integer>asList(0, 0, 0, 2, 0, 0, 0, 0), // 6 5
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 7 6
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0)  // 8 7
                    ));
            gameService.move(game, "c5", GameService.BLACK_PLAYER);
            System.out.println(minMaxService.computeScore(game, GameService.BLACK_PLAYER));

            game.setCurrentPlayer(GameService.BLACK_PLAYER);
            game.setBoardState(Arrays.asList(

                    //                     0  1  2  3  4  5  6  7
                    //                     a  b  c  d  e  f  g  h
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 1 0
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 2 1
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 3 2
                    Arrays.<Integer>asList(0, 0, 0, 2, 1, 0, 0, 0), // 4 3
                    Arrays.<Integer>asList(0, 0, 0, 2, 1, 1, 0, 0), // 5 4
                    Arrays.<Integer>asList(0, 0, 0, 2, 0, 0, 0, 0), // 6 5
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 7 6
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0)  // 8 7
                    ));
            gameService.move(game, "c6", GameService.BLACK_PLAYER);
            System.out.println(minMaxService.computeScore(game, GameService.BLACK_PLAYER));

            game.setCurrentPlayer(GameService.BLACK_PLAYER);
            game.setBoardState(Arrays.asList(

                    //                     0  1  2  3  4  5  6  7
                    //                     a  b  c  d  e  f  g  h
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 1 0
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 2 1
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 3 2
                    Arrays.<Integer>asList(0, 0, 0, 2, 1, 0, 0, 0), // 4 3
                    Arrays.<Integer>asList(0, 0, 0, 2, 1, 1, 0, 0), // 5 4
                    Arrays.<Integer>asList(0, 0, 0, 2, 0, 0, 0, 0), // 6 5
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0), // 7 6
                    Arrays.<Integer>asList(0, 0, 0, 0, 0, 0, 0, 0)  // 8 7
                    ));
            gameService.move(game, "c7", GameService.BLACK_PLAYER);
            System.out.println(minMaxService.computeScore(game, GameService.BLACK_PLAYER));
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
