
package im.firat.reversi.erdalbakkal.services;


import im.firat.reversi.domain.Game;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;



public final class RandomCalculationServiceImpl implements CalculationService {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public RandomCalculationServiceImpl() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public String computeNextMove(final Game game, final int player, final ExecutorService executor) {

        final List<String> availableMoves = game.getAvailableMoves();
        final Random       random         = new Random();
        final int          randomInt      = random.nextInt(availableMoves.size());
        final String       nextMove       = availableMoves.get(randomInt);

        return nextMove;
    }
}
