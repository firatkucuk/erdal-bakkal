
package im.firat.reversi.erdalbakkal.algorithms;


import im.firat.reversi.beans.Path;
import im.firat.reversi.beans.Position;
import im.firat.reversi.domain.Game;
import im.firat.reversi.erdalbakkal.beans.GameNode;
import im.firat.reversi.erdalbakkal.utils.MathUtils;
import im.firat.reversi.exceptions.IllegalMoveException;
import im.firat.reversi.exceptions.NotStartedException;
import im.firat.reversi.exceptions.WrongOrderException;
import im.firat.reversi.services.GameService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;



/**
 * General purpose minmax algoritm
 */
public final class MinMaxAlgorithm3 implements Algorithm {



    //~ --- [STATIC FIELDS/INITIALIZERS] -------------------------------------------------------------------------------

    private static final int     MAX_DEPTH                          = 4; // should be even
    private static final int     SCORE_PENALTY                      = -10000;
    private static final int     SCORE_RESULT_MULTIPLIER            = 10000;
    private static final int     SCORE_PATTERN_MULTIPLIER           = 5000;
    private static final boolean SELECT_RANDOM_MOVE_FOR_SAME_SCORES = true;
    private static final int[][] SCORE_MATRIX                       = {
        { 1000, 130, 100, 90, 90, 100, 130, 1000 },
        { 130, 80, 50, 40, 40, 50, 80, 130 },
        { 100, 50, 20, 10, 10, 20, 50, 100 },
        { 90, 40, 10, 0, 0, 10, 40, 90 },
        { 90, 40, 10, 0, 0, 10, 40, 90 },
        { 100, 50, 20, 10, 10, 20, 50, 100 },
        { 130, 80, 50, 40, 40, 50, 80, 130 },
        { 1000, 130, 100, 90, 90, 100, 130, 1000 },
    };



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private final GameService gameService = new GameService();



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public MinMaxAlgorithm3() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public String computeNextMove(final Game game, final int me, final ExecutorService executor) {

        try {
            final List<String> availableMoves = game.getAvailableMoves();

            if (availableMoves != null && !availableMoves.isEmpty()) {
                final List<Integer> stdDevScores  = new ArrayList<Integer>(availableMoves.size()); // for standard dev.
                final int           currentPlayer = game.getCurrentPlayer();

                // Collections.sort(availableMoves); // for same result all time, only debug purposes

                String topMove  = "";
                int    topScore = Integer.MIN_VALUE;

                for (final String move : availableMoves) {
                    final GameNode gameNode = new GameNode(game);
                    gameService.move(gameNode, move, currentPlayer);

                    int moveScore = walk(gameNode, 1, MAX_DEPTH, me);

                    //                    System.out.println("depth: " + 1);
                    //                    System.out.println("[" + moveScore + "]");
                    //                    System.out.println(gameNode);
                    stdDevScores.add(moveScore);

                    if (moveScore > topScore) {
                        topScore = moveScore;
                        topMove  = move;
                    }
                }

                //                System.out.println("x-x");
                //                System.out.println(topScore);

                // If all move values are same
                if (SELECT_RANDOM_MOVE_FOR_SAME_SCORES && MathUtils.computeStandardDeviation(stdDevScores) == 0d) {
                    final Random random    = new Random();
                    final int    randomInt = random.nextInt(availableMoves.size());

                    topMove = availableMoves.get(randomInt);
                }

                return topMove;
            } // end if
        } catch (WrongOrderException e) {
            e.printStackTrace();
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        } catch (NotStartedException e) {
            e.printStackTrace();
        }

        return null;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public int computeScore(final Game game, final int me) {

        final List<List<Integer>> boardState                = game.getBoardState();
        final int                 criticalPatternMultiplier;

        int totalScore = 0;

        if ((criticalPatternMultiplier = findCriticalPatterns(game, me)) != 0) {
            totalScore += criticalPatternMultiplier * SCORE_PATTERN_MULTIPLIER;
        }

        for (int row = 0; row < 8; row++) {
            final List<Integer> rowValues = boardState.get(row);

            for (int col = 0; col < 8; col++) {
                final int value = rowValues.get(col);

                if (value == me) {
                    totalScore += SCORE_MATRIX[row][col];
                } else if (value != GameService.EMPTY_PLACE) {
                    totalScore -= SCORE_MATRIX[row][col];
                }
            }
        }

        return totalScore;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private int computeResultFactor(final List<List<Integer>> boardState, final int me) {

        int meTotal    = 0;
        int otherTotal = 0;

        for (int row = 0; row < 8; row++) {
            List<Integer> rowValues = boardState.get(row);

            for (int col = 0; col < 8; col++) {
                Integer value = rowValues.get(col);

                if (value == me) {
                    meTotal++;
                } else if (value != GameService.EMPTY_PLACE) {
                    otherTotal++;
                }
            }
        }

        if (meTotal > otherTotal) {
            return 1;
        } else if (meTotal == otherTotal) {
            return 0;
        }

        return -1;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private int findCriticalPatterns(final Game game, final int me) {

        final List<List<Integer>> boardState  = game.getBoardState();
        final int                 otherPlayer = me == GameService.BLACK_PLAYER ? GameService.WHITE_PLAYER
                                                                               : GameService.BLACK_PLAYER;
        /*
            x 0 1 2 3 4 5 6 7 x
            0 A x         x B 0
            1 x x         x x 1
            2                 2
            3                 3
            4                 4
            5                 5
            6 x x         x x 6
            7 C x         x D 7
            x 0 1 2 3 4 5 6 7 x
         */

        Map<Position, List<Path>> availablePaths;

        if (boardState.get(0).get(0) == GameService.EMPTY_PLACE) {
            final int val01 = boardState.get(0).get(1);
            final int val10 = boardState.get(1).get(0);
            final int val11 = boardState.get(1).get(1);

            if (val01 == me || val10 == me || val11 == me) {
                availablePaths = gameService.findAvailablePaths(game, new Position(0, 0), me);

                if (!availablePaths.isEmpty()) {
                    return 1;
                }
            }

            if (val01 == otherPlayer || val10 == otherPlayer || val11 == otherPlayer) {
                availablePaths = gameService.findAvailablePaths(game, new Position(0, 0), otherPlayer);

                if (!availablePaths.isEmpty()) {
                    return -1;
                }
            }


        }

        if (boardState.get(0).get(7) == GameService.EMPTY_PLACE) {
            final int val06 = boardState.get(0).get(6);
            final int val16 = boardState.get(1).get(6);
            final int val17 = boardState.get(1).get(7);

            if (val06 == me || val16 == me || val17 == me) {
                availablePaths = gameService.findAvailablePaths(game, new Position(0, 7), me);

                if (!availablePaths.isEmpty()) {
                    return 1;
                }
            }

            if (val06 == otherPlayer || val16 == otherPlayer || val17 == otherPlayer) {
                availablePaths = gameService.findAvailablePaths(game, new Position(0, 7), otherPlayer);

                if (!availablePaths.isEmpty()) {
                    return -1;
                }
            }
        }

        if (boardState.get(7).get(0) == GameService.EMPTY_PLACE) {
            final int val60 = boardState.get(6).get(0);
            final int val61 = boardState.get(6).get(1);
            final int val71 = boardState.get(7).get(1);

            if (val60 == me || val61 == me || val71 == me) {
                availablePaths = gameService.findAvailablePaths(game, new Position(7, 0), me);

                if (!availablePaths.isEmpty()) {
                    return 1;
                }
            }

            if (val60 == otherPlayer || val61 == otherPlayer || val71 == otherPlayer) {
                availablePaths = gameService.findAvailablePaths(game, new Position(7, 0), otherPlayer);

                if (!availablePaths.isEmpty()) {
                    return -1;
                }
            }
        }

        if (boardState.get(7).get(7) == GameService.EMPTY_PLACE) {
            final int val66 = boardState.get(6).get(6);
            final int val67 = boardState.get(6).get(7);
            final int val76 = boardState.get(7).get(6);

            if (val66 == me || val67 == me || val76 == me) {
                availablePaths = gameService.findAvailablePaths(game, new Position(7, 7), me);

                if (!availablePaths.isEmpty()) {
                    return 1;
                }
            }

            if (val66 == otherPlayer || val67 == otherPlayer || val76 == otherPlayer) {
                availablePaths = gameService.findAvailablePaths(game, new Position(7, 7), otherPlayer);

                if (!availablePaths.isEmpty()) {
                    return -1;
                }
            }
        }

        return 0;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private int walk(final GameNode node, int depth, final int maxDepth, final int me) throws WrongOrderException,
        IllegalMoveException, NotStartedException {

        if (!node.isStarted()) {         // if game is finished
            return SCORE_RESULT_MULTIPLIER * computeResultFactor(node.getBoardState(), me);
        } else if (depth++ < maxDepth) { // if not reached to the depth/leaf, at the same time we increment depth value

            final int          currentPlayer     = node.getCurrentPlayer();
            final boolean      isCurrentPlayerMe = currentPlayer == me;
            final int          meMultiplier      = isCurrentPlayerMe ? 1 : -1;
            final List<String> availableMoves    = node.getAvailableMoves();

            if (availableMoves != null && !availableMoves.isEmpty()) {
                // Collections.sort(availableMoves); // for same result all time only debugging purposes

                int score = isCurrentPlayerMe ? Integer.MIN_VALUE : Integer.MAX_VALUE;

                for (final String move : availableMoves) {
                    final GameNode childNode = new GameNode(node);
                    gameService.move(childNode, move, currentPlayer);

                    final int moveScore = walk(childNode, depth, maxDepth, me);
                    //                    System.out.println("depth: " + depth);
                    //                    System.out.println("[" + moveScore + "]");
                    //                    System.out.println(childNode);

                    if (isCurrentPlayerMe && moveScore > score) {         // MAX
                        score = moveScore;
                    } else if (!isCurrentPlayerMe && moveScore < score) { // MIN
                        score = moveScore;
                    }
                }

                return score;
            } // end if

            // if there is no available move
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx");

            int temp = meMultiplier * SCORE_PENALTY;
            System.out.println(temp);

            return temp;
        } // end if

        // if reached to depth/leaf
        return computeScore(node, me);
    }
}
