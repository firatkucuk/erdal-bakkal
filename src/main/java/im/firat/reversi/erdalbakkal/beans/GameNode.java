
package im.firat.reversi.erdalbakkal.beans;


import im.firat.reversi.domain.Game;
import java.util.ArrayList;
import java.util.List;



public final class GameNode extends Game {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    /**
     * Copy constructor
     *
     * @param  game
     */
    public GameNode(final Game game) {

        List<String> newAvailableMoves = new ArrayList<String>();
        List<String> availableMoves    = game.getAvailableMoves();

        for (String availableMove : availableMoves) {
            newAvailableMoves.add(availableMove);
        }

        List<List<Integer>> newBoardState = new ArrayList<List<Integer>>();
        List<List<Integer>> boardState    = game.getBoardState();

        for (List<Integer> boardRow : boardState) {
            List<Integer> newBoardRow = new ArrayList<Integer>();

            for (Integer cell : boardRow) {
                newBoardRow.add(cell);
            }

            newBoardState.add(newBoardRow);
        }

        setAvailableMoves(newAvailableMoves);
        setBoardState(newBoardState);
        setCancelled(game.isCancelled());
        setCurrentPlayer(game.getCurrentPlayer());
        setStarted(game.isStarted());
    }



    private GameNode() {

    }
}
