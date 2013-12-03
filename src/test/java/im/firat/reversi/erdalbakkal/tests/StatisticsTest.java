
package im.firat.reversi.erdalbakkal.tests;


import im.firat.reversi.erdalbakkal.services.MinMaxCalculationServiceImpl;
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
        double        mean   = MinMaxCalculationServiceImpl.computeMean(values);

        assert (mean == 3);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Test
    public void computeVarianceTest() {

        List<Integer> values   = Arrays.asList(2, 2, 2, 2);
        double        variance = MinMaxCalculationServiceImpl.computeVariance(values);

        assert (variance == 0);
    }
}
