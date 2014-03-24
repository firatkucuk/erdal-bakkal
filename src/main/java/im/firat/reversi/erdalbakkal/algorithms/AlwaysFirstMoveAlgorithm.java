
package im.firat.reversi.erdalbakkal.algorithms;


import im.firat.reversi.domain.Game;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;



/**
 * Always first algorithm selects always first available choice. It used for benchmarking.
 */
public final class AlwaysFirstMoveAlgorithm implements Algorithm {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public AlwaysFirstMoveAlgorithm() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public String computeNextMove(final Game game, final int player, final ExecutorService executor) {

        List<String> availableMoves = game.getAvailableMoves();
        Collections.sort(availableMoves);

        return availableMoves.get(0);
    }
}
