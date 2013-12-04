
package im.firat.reversi.erdalbakkal.utils;


import im.firat.reversi.domain.Game;
import im.firat.reversi.services.GameService;
import java.util.List;



public final class PrintUtils {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    private PrintUtils() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static void printScore(final Game game) {

        List<List<Integer>> boardState = game.getBoardState();
        int                 blackTotal = 0;
        int                 whiteTotal = 0;

        for (int row = 0; row < 8; row++) {
            List<Integer> rowValues = boardState.get(row);

            for (int col = 0; col < 8; col++) {
                Integer value = rowValues.get(col);

                if (value == GameService.BLACK_DISK) {
                    blackTotal++;
                } else if (value == GameService.WHITE_DISK) {
                    whiteTotal++;
                }
            }
        }

        System.out.println(game);
        System.out.println("Black: " + blackTotal);
        System.out.println("White: " + whiteTotal);
    }

}
