package pl.parser.nbp.util;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CountUtilsTest {
    public static final List<Double> NUMBERS_FOR_TEST = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        NUMBERS_FOR_TEST.add(1d);
        NUMBERS_FOR_TEST.add(2d);
        NUMBERS_FOR_TEST.add(3d);
        NUMBERS_FOR_TEST.add(4d);
    }

    @Test
    public void testCountMean() throws Exception {
        double mean = CountUtils.countMeanFromListWithDouble(NUMBERS_FOR_TEST);
        assertThat(2.5d).isEqualTo(mean);
    }

    @Test
    public void testCountMean_withEmptyList() throws Exception {
        double mean = CountUtils.countMeanFromListWithDouble(Collections.emptyList());
        assertThat(0d).isEqualTo(mean);
    }

    @Test
    public void testStandardDeviation() throws Exception {
        double standardDeviation = CountUtils.countStandardDeviation(NUMBERS_FOR_TEST);
        assertThat(1.118033988749895).isEqualTo(standardDeviation);
    }

    @Test
    public void testStandardDeviation_withEmptyList() throws Exception {
        double standardDeviation = CountUtils.countStandardDeviation(Collections.emptyList());
        assertThat(0d).isEqualTo(standardDeviation);
    }
}
