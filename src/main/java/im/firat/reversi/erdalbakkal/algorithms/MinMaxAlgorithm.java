
package im.firat.reversi.erdalbakkal.algorithms;


import im.firat.reversi.domain.Game;
import im.firat.reversi.erdalbakkal.beans.GameNode;
import im.firat.reversi.erdalbakkal.utils.MathUtils;
import im.firat.reversi.exceptions.IllegalMoveException;
import im.firat.reversi.exceptions.NotStartedException;
import im.firat.reversi.exceptions.WrongOrderException;
import im.firat.reversi.services.GameService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;



/**
 * General purpose minmax algoritm
 */
public final class MinMaxAlgorithm implements Algorithm {



    //~ --- [STATIC FIELDS/INITIALIZERS] -------------------------------------------------------------------------------

    private static final int     MAX_DEPTH                          = 5;
    private static final int     SCORE_PENALTY                      = -10000;
    private static final int     SCORE_RESULT_MULTIPLIER            = 10000;
    private static final boolean SELECT_RANDOM_MOVE_FOR_SAME_SCORES = true;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public MinMaxAlgorithm() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public String computeNextMove(final Game game, final int player, final ExecutorService executor) {

        try {
            final List<String> availableMoves = game.getAvailableMoves();

            if (availableMoves != null && !availableMoves.isEmpty()) {
                final List<Integer> moveScores    = new ArrayList<Integer>(availableMoves.size());
                final GameService   gameService   = new GameService();
                final int           currentPlayer = game.getCurrentPlayer();

                Collections.sort(availableMoves); // for same result all time

                String topMove  = "";
                int    topScore = Integer.MIN_VALUE;

                for (final String move : availableMoves) {
                    final GameNode gameNode = new GameNode(game);
                    gameService.move(gameNode, move, currentPlayer);

                    int moveScore = walk(gameNode, 1, MAX_DEPTH, player);
                    moveScores.add(moveScore);

                    if (moveScore > topScore) {
                        topScore = moveScore;
                        topMove  = move;
                    }
                }

                // If all move values are same
                if (SELECT_RANDOM_MOVE_FOR_SAME_SCORES && MathUtils.computeStandardDeviation(moveScores) == 0d) {
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

    public int computeScore(Game game, int me) {

        int totalScore = 0;

        for (int row = 0; row < 8; row++) {
            List<Integer> rowValues = game.getBoardState().get(row);

            for (int col = 0; col < 8; col++) {
                int value = rowValues.get(col);

                if (value != GameService.EMPTY_PLACE) {
                    int cellScore = computeCellScore(row, col);

                    if (me == value) {
                        totalScore += cellScore;
                    } else {
                        totalScore -= cellScore;
                    }
                } // end if
            }     // end for
        }         // end for

        return totalScore;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private int computeCellScore(int row, int col) {

        int cellScore = 0;

        if (row == 0 || row == 7 || col == 0 || col == 7) {
            cellScore = isDiagonal(0, 7) ? 1000 : 40;
        } else if (row == 1 || row == 6 || col == 1 || col == 6) {
            cellScore = isDiagonal(1, 6) ? 31 : 30;
        } else if (row == 2 || row == 5 || col == 2 || col == 5) {
            cellScore = isDiagonal(2, 5) ? 21 : 20;
        } else if (row == 3 || row == 4 || col == 3 || col == 4) {
            cellScore = 11;
        }

        return cellScore;
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

    private boolean isDiagonal(int row, int col) {

        return (row == 0 && col == 0) || (row == 0 && col == 7) || (row == 7 && col == 0) || (row == 7 && col == 7);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private int walk(final GameNode parent, int depth, final int maxDepth, final int me) throws WrongOrderException,
        IllegalMoveException, NotStartedException {

        final int     currentPlayer     = parent.getCurrentPlayer();
        final boolean isCurrentPlayerMe = currentPlayer == me;
        final int     meMultiplier      = isCurrentPlayerMe ? 1 : -1;

        if (!parent.isStarted()) {       // if game is finished
            return SCORE_RESULT_MULTIPLIER * computeResultFactor(parent.getBoardState(), me);
        } else if (++depth < maxDepth) { // if not reached to the depth/leaf, at the same time we increment depth value

            final List<String> availableMoves = parent.getAvailableMoves();

            if (availableMoves != null && !availableMoves.isEmpty()) {
                Collections.sort(availableMoves); // for same result all time

                final GameService gameService = new GameService();

                int score = isCurrentPlayerMe ? Integer.MIN_VALUE : Integer.MAX_VALUE;

                for (final String move : availableMoves) {
                    final GameNode gameNode = new GameNode(parent);
                    gameService.move(gameNode, move, currentPlayer);

                    final int moveScore = walk(gameNode, depth, maxDepth, me);

                    if (isCurrentPlayerMe && moveScore > score) {
                        score = moveScore;
                    } else if (!isCurrentPlayerMe && moveScore < score) {
                        score = moveScore;
                    }
                }

                return score;
            } // end if

            // if there is no available move
            return meMultiplier * SCORE_PENALTY;
        } // end if

        // if reached to depth/leaf
        return computeScore(parent, me);
    }
}
