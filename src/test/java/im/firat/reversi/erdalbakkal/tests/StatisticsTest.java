
package im.firat.reversi.erdalbakkal.tests;


import im.firat.reversi.erdalbakkal.utils.MathUtils;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;



public class StatisticsTest {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public StatisticsTest() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Test
    public void computeMeanTest() {

        List<Integer> values = Arrays.asList(1, 2, 3, 6);
        double        mean   = MathUtils.computeMean(values);

        assert (mean == 3);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Test
    public void computeVarianceTest() {

        List<Integer> values   = Arrays.asList(2, 2, 2, 2);
        double        variance = MathUtils.computeVariance(values);

        assert (variance == 0);
    }
}
