
package im.firat.reversi.erdalbakkal.services;


import im.firat.reversi.domain.Game;



public interface CalculationService {



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public String computeNextMove(Game game, int player);
}
