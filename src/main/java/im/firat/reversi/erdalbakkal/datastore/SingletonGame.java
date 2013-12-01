
package im.firat.reversi.erdalbakkal.datastore;

import im.firat.reversi.domain.Game;



public final class SingletonGame {



    //~ --- [STATIC FIELDS/INITIALIZERS] -------------------------------------------------------------------------------

    private static Game game = new Game();



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private SingletonGame() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static Game getInstance() {

        return game;
    }
}
