
package im.firat.reversi.erdalbakkal.tests;


import im.firat.reversi.domain.Game;
import im.firat.reversi.erdalbakkal.algorithms.MinMaxAlgorithm;
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
            Game            game          = new Game();
            GameService     gameService   = new GameService();
            MinMaxAlgorithm minMaxService = new MinMaxAlgorithm();

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



    //~ ----------------------------------------------------------------------------------------------------------------

    @Test
    public void test123() {

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                double min = Double.MAX_VALUE;

                double corner00 = Math.sqrt(Math.pow((i - 0), 2) + Math.pow((j - 0), 2));
                double corner07 = Math.sqrt(Math.pow((i - 0), 2) + Math.pow((j - 7), 2));
                double corner70 = Math.sqrt(Math.pow((i - 7), 2) + Math.pow((j - 0), 2));
                double corner77 = Math.sqrt(Math.pow((i - 7), 2) + Math.pow((j - 7), 2));

                if (corner00 < min) {
                    min = corner00;
                }

                if (corner07 < min) {
                    min = corner00;
                }

                if (corner70 < min) {
                    min = corner00;
                }

                if (corner77 < min) {
                    min = corner00;
                }

                System.out.print(min + " ");
            } // end for

            System.out.println();
        } // end for
    }
}
