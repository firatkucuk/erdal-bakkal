
package im.firat.reversi.erdalbakkal.services;


import im.firat.reversi.domain.Game;
import im.firat.reversi.erdalbakkal.beans.GameNode;
import im.firat.reversi.exceptions.IllegalMoveException;
import im.firat.reversi.exceptions.NotStartedException;
import im.firat.reversi.exceptions.WrongOrderException;
import im.firat.reversi.services.GameService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;



/**
 * Bu algoritmada belirli bir derinliğe kadar tüm ağaç taranır ve etkin kullanıcı için en iyi skora sahip yol seçilir.
 */
public final class MaxCalculationServiceImpl implements CalculationService {



    //~ --- [STATIC FIELDS/INITIALIZERS] -------------------------------------------------------------------------------

    private static final int MAX_DEPTH = 4;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public MaxCalculationServiceImpl() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public String computeNextMove(final Game game, final int player, final ExecutorService executor) {

        try {
            ConcurrentHashMap<String, List<Integer>> scoreMap       = new ConcurrentHashMap<String, List<Integer>>();
            List<String>                             availableMoves = game.getAvailableMoves();

            for (String availableMove : availableMoves) {
                GameNode gameNode = new GameNode(game);

                scoreMap.put(availableMove, new ArrayList<Integer>());
                walk(gameNode, 0, MAX_DEPTH, availableMove, player, scoreMap);
            }

            String topMove  = "";
            int    topScore = 0;

            for (String move : scoreMap.keySet()) {
                List<Integer> moveScores = scoreMap.get(move);

                for (Integer moveScore : moveScores) {

                    if (topMove.isEmpty() || moveScore > topScore) {
                        topMove  = move;
                        topScore = moveScore;
                    }
                }
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

    private int computeGameResultFactor(final List<List<Integer>> boardState, final int player) {

        int playerTotal = 0;
        int otherTotal  = 0;

        for (int row = 0; row < 8; row++) {
            List<Integer> rowValues = boardState.get(row);

            for (int col = 0; col < 8; col++) {
                Integer value = rowValues.get(col);

                if (value == player) {
                    playerTotal++;
                } else if (value != GameService.EMPTY_PLACE) {
                    otherTotal++;
                }
            }
        }

        if (playerTotal > otherTotal) {
            return 10;
        } else if (playerTotal == otherTotal) {
            return 0;
        }

        return -1;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private int computeScore(List<List<Integer>> boardState, int me) {

        int totalScore = 0;

        for (int row = 0; row < 8; row++) {
            List<Integer> rowValues = boardState.get(row);

            for (int col = 0; col < 8; col++) {
                int value = rowValues.get(col);

                if (value != GameService.EMPTY_PLACE) {
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

    /**
     * <pre>
     X 4 4 4 4 4 4 X
     4 3 3 3 3 3 3 4
     4 3 2 2 2 2 3 4
     4 3 2 1 1 2 3 4
     4 3 2 1 1 2 3 4
     4 3 2 2 2 2 3 4
     4 3 3 3 3 3 3 4
     x 4 4 4 4 4 4 X


     score 50 * (50 - depth) * (regionFactor)
     yenme: (50 - depth) * 100000
     yenilgi: (50 - depth) * -10000
     </pre>
     *
     * @param   parent
     * @param   depth
     * @param   maxDepth
     * @param   key
     * @param   player
     *
     * @throws  WrongOrderException
     * @throws  IllegalMoveException
     * @throws  NotStartedException
     */
    private void walk(final GameNode parent, final int depth, final int maxDepth, final String key, final int player,
            final ConcurrentHashMap<String, List<Integer>> scoreMap) throws WrongOrderException, IllegalMoveException,
        NotStartedException {

        List<String> availableMoves = parent.getAvailableMoves();
        GameService  gameService    = new GameService();

        //        System.out.println("-------------------------------------");
        //        System.out.println("depth: " + depth);
        //        System.out.println(parent);
        //        System.out.println("available moves: " + availableMoves);
        //        System.out.println("next move player: " + (parent.getCurrentPlayer() == 1 ? "X" : "O"));

        if (availableMoves != null && !availableMoves.isEmpty()) {

            for (String availableMove : availableMoves) {
                GameNode gameNode = new GameNode(parent);

                gameService.move(gameNode, availableMove, parent.getCurrentPlayer());

                List<Integer> moveScores = scoreMap.get(key);

                if (!gameNode.isStarted()) {
                    moveScores.add(10000 * computeGameResultFactor(gameNode.getBoardState(), player));
                } else if (depth < maxDepth) {
                    walk(gameNode, depth + 1, maxDepth, key, player, scoreMap);
                } else { // depth reached
                    moveScores.add(computeScore(gameNode.getBoardState(), player));
                }
            }
        }
    }
}
