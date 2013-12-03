
package im.firat.reversi.erdalbakkal.services;


import im.firat.reversi.domain.Game;
import im.firat.reversi.erdalbakkal.beans.GameNode;
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
 * Bu algoritmada belirli bir derinliğe kadar tüm ağaç taranır ve etkin kullanıcı için en iyi skora sahip yol seçilir.
 */
public final class MinMaxCalculationServiceImpl implements CalculationService {



    //~ --- [STATIC FIELDS/INITIALIZERS] -------------------------------------------------------------------------------

    private static final int MAX_DEPTH               = 4;
    private static final int SCORE_MAX_PENALTY       = -10000;
    private static final int SCORE_RESULT_MULTIPLIER = 10000;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public MinMaxCalculationServiceImpl() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static double computeMean(List<Integer> values) {

        double sum = 0d;

        for (int value : values) {
            sum += value;
        }

        return sum / values.size();
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static double computeVariance(List<Integer> values) {

        double mean  = computeMean(values);
        double total = 0;

        for (int value : values) {
            total += Math.sqrt(mean - value);
        }

        return total / values.size();
    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public String computeNextMove(final Game game, final int player, final ExecutorService executor) {

        try {
            final List<String>  availableMoves = game.getAvailableMoves();
            final List<Integer> moveScores     = new ArrayList<Integer>(availableMoves.size());

            Collections.sort(availableMoves); // for same result all time

            String topMove  = "";
            int    topScore = Integer.MIN_VALUE;


            for (String move : availableMoves) {
                final GameNode gameNode = new GameNode(game);

                int moveScore = maxWalk(gameNode, 0, MAX_DEPTH, player);
                moveScores.add(moveScore);
                System.out.println(moveScore + " " + move);

                if (moveScore > topScore) {
                    topMove  = move;
                    topScore = moveScore;
                }
            }

            if (computeVariance(moveScores) == 0d) { // If all move values are same

                final Random random    = new Random();
                final int    randomInt = random.nextInt(availableMoves.size());

                topMove = availableMoves.get(randomInt);
            }

            return topMove;
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

    private int computeScore(Game game, int me) {

        if (!game.isStarted()) {
            return SCORE_RESULT_MULTIPLIER * computeResultFactor(game.getBoardState(), me);
        }

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

    private boolean isDiagonal(int row, int col) {

        return (row == 0 && col == 0) || (row == 0 && col == 7) || (row == 7 && col == 0) || (row == 7 && col == 7);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private int maxWalk(final GameNode parent, int depth, final int maxDepth, final int me) throws WrongOrderException,
        IllegalMoveException, NotStartedException {

        if (++depth < maxDepth) { // if not reached to the depth/leaf, at the same time we increment depth value

            final List<String> availableMoves = parent.getAvailableMoves();

            if (availableMoves != null && !availableMoves.isEmpty()) {
                Collections.sort(availableMoves); // for same result all time

                final GameService gameService = new GameService();

                int score = Integer.MIN_VALUE;

                for (String move : availableMoves) {
                    final GameNode gameNode      = new GameNode(parent);
                    final int      currentPlayer = parent.getCurrentPlayer();

                    gameService.move(gameNode, move, currentPlayer);

                    int moveScore = minWalk(gameNode, depth, maxDepth, me);

                    if (moveScore > score) {
                        score = moveScore;
                    }
                }

                return score;
            } // end if

            // if there is no available move
            return SCORE_MAX_PENALTY;
        } // end if

        // if reached to depth/leaf
        return computeScore(parent, me);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private int minWalk(final GameNode parent, int depth, final int maxDepth, final int me) throws WrongOrderException,
        IllegalMoveException, NotStartedException {

        if (++depth < maxDepth) { // if not reached to the depth/leaf, at the same time we increment depth value

            final List<String> availableMoves = parent.getAvailableMoves();

            if (availableMoves != null && !availableMoves.isEmpty()) {
                Collections.sort(availableMoves); // for same result all time

                final GameService gameService = new GameService();

                int score = Integer.MAX_VALUE;

                for (String move : availableMoves) {
                    final GameNode gameNode      = new GameNode(parent);
                    final int      currentPlayer = parent.getCurrentPlayer();

                    gameService.move(gameNode, move, currentPlayer);

                    int moveScore = maxWalk(gameNode, depth, maxDepth, currentPlayer);

                    if (moveScore < score) {
                        score = moveScore;
                    }
                }

                return score;
            } // end if

            // if there is no available move
            return SCORE_MAX_PENALTY;
        } // end if

        // if reached to depth/leaf
        return computeScore(parent, me);
    }
}
