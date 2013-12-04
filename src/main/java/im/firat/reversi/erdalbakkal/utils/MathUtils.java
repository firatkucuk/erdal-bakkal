
package im.firat.reversi.erdalbakkal.utils;


import java.util.List;



public class MathUtils {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public MathUtils() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static double computeMean(final List<Integer> values) {

        double sum = 0d;

        for (int value : values) {
            sum += value;
        }

        return sum / values.size();
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static double computeStandardDeviation(final List<Integer> values) {

        return Math.sqrt(computeVariance(values));
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public static double computeVariance(final List<Integer> values) {

        final double mean  = computeMean(values);
        double       total = 0;

        for (int value : values) {
            total += (mean - value) * (mean - value);
        }

        return total / values.size();
    }
}
