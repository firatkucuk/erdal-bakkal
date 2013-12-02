
package im.firat.reversi.erdalbakkal.services;


import im.firat.reversi.domain.Game;
import java.util.concurrent.ExecutorService;



public interface CalculationService {



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public String computeNextMove(final Game game, final int player, final ExecutorService executor);
}
