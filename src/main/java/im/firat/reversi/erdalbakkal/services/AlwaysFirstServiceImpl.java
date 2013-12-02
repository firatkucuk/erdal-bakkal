
package im.firat.reversi.erdalbakkal.services;


import im.firat.reversi.domain.Game;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;



public final class AlwaysFirstServiceImpl implements CalculationService {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public AlwaysFirstServiceImpl() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public String computeNextMove(final Game game, final int player, final ExecutorService executor) {

        List<String> availableMoves = game.getAvailableMoves();
        Collections.sort(availableMoves);

        return availableMoves.get(0);
    }
}
