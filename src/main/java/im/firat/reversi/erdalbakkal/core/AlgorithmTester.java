
package im.firat.reversi.erdalbakkal.core;


import im.firat.reversi.domain.Game;
import im.firat.reversi.erdalbakkal.algorithms.Algorithm;
import im.firat.reversi.erdalbakkal.datastore.SingletonGame;
import im.firat.reversi.exceptions.AlreadyStartedException;
import im.firat.reversi.exceptions.IllegalMoveException;
import im.firat.reversi.exceptions.NotStartedException;
import im.firat.reversi.exceptions.WrongOrderException;
import im.firat.reversi.services.GameService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public final class AlgorithmTester {



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private final Algorithm player1Algorithm;
    private final Algorithm player2Algorithm;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public AlgorithmTester(final Algorithm player1Algorithm, final Algorithm player2Algorithm) {

        this.player1Algorithm = player1Algorithm;
        this.player2Algorithm = player2Algorithm;
    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public void test(final int iterationCount, final int threadPoolSize) {

        final ExecutorService      executor = Executors.newFixedThreadPool(threadPoolSize);
        final Map<String, Integer> results  = new HashMap<String, Integer>();

        initialize(results, iterationCount, threadPoolSize);

        for (int i = 0; i < iterationCount; i++) {

            simulateGame(executor, results);
            computeCounts(results);

            System.out.print("\n-\n");
        }                            // end for

        showText(results);
        executor.shutdown();
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private void computeCounts(Map<String, Integer> results) {

        final int winner       = whoWinTheMatch(results);
        final int player1Color = results.get("player1Color");
        final int player2Color = results.get("player2Color");

        if (winner == player1Color) {
            results.put("player1WinCount", results.get("player1WinCount") + 1);
        } else if (winner == player2Color) {
            results.put("player2WinCount", results.get("player2WinCount") + 1);
        } else {
            results.put("drawCount", results.get("drawCount") + 1);
        }

        if (player1Color == GameService.BLACK_PLAYER) {
            results.put("player1BlackCount", results.get("player1BlackCount") + 1);
            results.put("player2WhiteCount", results.get("player2WhiteCount") + 1);

            results.put("player1Color", GameService.WHITE_PLAYER);
            results.put("player2Color", GameService.BLACK_PLAYER);

            if (winner == player1Color) {
                results.put("player1WinOnBlackCount", results.get("player1WinOnBlackCount") + 1);
            } else if (winner == results.get("player2Color")) {
                results.put("player2WinOnBlackCount", results.get("player2WinOnBlackCount") + 1);
            }
        } else {
            results.put("player1WhiteCount", results.get("player1WhiteCount") + 1);
            results.put("player2BlackCount", results.get("player2BlackCount") + 1);

            results.put("player1Color", GameService.BLACK_PLAYER);
            results.put("player2Color", GameService.WHITE_PLAYER);
        }
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private void initialize(final Map<String, Integer> results, final int iterationCount, final int threadPoolSize) {

        results.put("iterationCount", iterationCount);
        results.put("threadPoolSize", threadPoolSize);
        results.put("player1BlackCount", 0);
        results.put("player2BlackCount", 0);
        results.put("player1WhiteCount", 0);
        results.put("player2WhiteCount", 0);
        results.put("player1WinCount", 0);
        results.put("player2WinCount", 0);
        results.put("player1WinOnBlackCount", 0);
        results.put("player2WinOnBlackCount", 0);
        results.put("drawCount", 0);
        results.put("player1AverageTime", 0);
        results.put("player2AverageTime", 0);
        results.put("player1Color", GameService.BLACK_PLAYER);
        results.put("player2Color", GameService.WHITE_PLAYER);
        results.put("totalDepth", 0);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private void showText(Map<String, Integer> results) {

        final StringBuilder text                   = new StringBuilder();
        final int           iterationCount         = results.get("iterationCount");
        final int           threadPoolSize         = results.get("threadPoolSize");
        final int           totalDepth             = results.get("totalDepth");
        final int           player1WinCount        = results.get("player1WinCount");
        final int           drawCount              = results.get("drawCount");
        final int           player2WinCount        = results.get("player2WinCount");
        final int           player1WinOnBlackCount = results.get("player1WinOnBlackCount");
        final int           player1BlackCount      = results.get("player1BlackCount");
        final int           player1WhiteCount      = results.get("player1WhiteCount");
        final int           player1AverageTime     = results.get("player1AverageTime");
        final int           player2WinOnBlackCount = results.get("player2WinOnBlackCount");
        final int           player2BlackCount      = results.get("player2BlackCount");
        final int           player2WhiteCount      = results.get("player2WhiteCount");
        final int           player2AverageTime     = results.get("player2AverageTime");

        text.append("\n");
        text.append("------------------------------------------------------------------------------------------\n");
        text.append("                                    R E S U L T S\n");
        text.append("------------------------------------------------------------------------------------------\n");
        text.append("Iteration Count   : " + iterationCount + "\n");
        text.append("Thread Pool Size  : " + threadPoolSize + "\n");
        text.append("Average Depth     : " + (totalDepth / iterationCount) + "\n");

        text.append("\n");
        text.append("### Player 1 #############################################################################\n");
        text.append("Algorithm         : " + player1Algorithm.getClass().getName() + "\n");
        text.append("Win Count         : " + player1WinCount + "\n");
        text.append("Draw Count        : " + drawCount + "\n");
        text.append("Lose Count        : " + player2WinCount + "\n");
        text.append("Win On Black Count: " + player1WinOnBlackCount + "\n");
        text.append("Win On White Count: " + (player1WinCount - player1WinOnBlackCount) + "\n");
        text.append("Black Count       : " + player1BlackCount + "\n");
        text.append("White Count       : " + player1WhiteCount + "\n");
        text.append("Average Time      : " + player1AverageTime + "\n");

        text.append("\n");
        text.append("### Player 2 #############################################################################\n");
        text.append("Algorithm         : " + player2Algorithm.getClass().getName() + "\n");
        text.append("Win Count         : " + player2WinCount + "\n");
        text.append("Draw Count        : " + drawCount + "\n");
        text.append("Lose Count        : " + player1WinCount + "\n");
        text.append("Win On Black Count: " + player2WinOnBlackCount + "\n");
        text.append("Win On White Count: " + (player2WinCount - player2WinOnBlackCount) + "\n");
        text.append("Black Count       : " + player2BlackCount + "\n");
        text.append("White Count       : " + player2WhiteCount + "\n");
        text.append("Average Time      : " + player2AverageTime + "\n");

        System.out.println(text.toString());
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private void simulateGame(final ExecutorService executor, final Map<String, Integer> results) {

        final GameService gameService  = new GameService();
        final Game        game         = SingletonGame.getInstance();
        final int         player1Color = results.get("player1Color");
        final int         player2Color = results.get("player2Color");
        final int         totalDepth   = results.get("totalDepth");

        int depth = 0;

        try {

            gameService.start(game);

            while (game.isStarted()) {

                final String nextMove;

                if (game.getCurrentPlayer() == player1Color) {
                    nextMove = player1Algorithm.computeNextMove(game, player1Color, executor);
                    gameService.move(game, nextMove, player1Color);
                } else {
                    nextMove = player2Algorithm.computeNextMove(game, player2Color, executor);
                    gameService.move(game, nextMove, player2Color);
                }

                depth++;

                System.out.print(".");
            }

            results.put("totalDepth", totalDepth + depth);
        } catch (AlreadyStartedException e) {
            e.printStackTrace();
        } catch (WrongOrderException e) {
            e.printStackTrace();
        } catch (NotStartedException e) {
            e.printStackTrace();
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private int whoWinTheMatch(final Map<String, Integer> results) {

        final int                 player1Color  = results.get("player1Color");
        final int                 player2Color  = results.get("player2Color");
        final Game                game          = SingletonGame.getInstance();
        final List<List<Integer>> boardState    = game.getBoardState();
        int                       player1Result = 0;
        int                       player2Result = 0;

        for (int j = 0, l1 = boardState.size(); j < l1; j++) {
            final List<Integer> row = boardState.get(j);

            for (int k = 0, l2 = row.size(); k < l2; k++) {

                if (row.get(k) == player1Color) {
                    player1Result++;
                } else if (row.get(k) == player2Color) {
                    player2Result++;
                }
            }
        }

        if (player1Result > player2Result) {
            return player1Color;
        } else if (player2Result > player1Result) {
            return player2Color;
        }

        return 0;
    }
}
