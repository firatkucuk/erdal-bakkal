
package im.firat.reversi.erdalbakkal.algorithms;


import im.firat.reversi.domain.Game;
import java.util.concurrent.ExecutorService;



public interface Algorithm {



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public String computeNextMove(final Game game, final int player, final ExecutorService executor);
}
