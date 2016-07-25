package pl.parser.nbp.util;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.*;

/**
 * Created by porwan on 21.07.2016.
 */
public class CountUtils {

    private CountUtils() {

    }

    public static double countMeanFromListWithDouble(List<Double> input) {
        if (!input.isEmpty()) {
            double[] inputToDoubleTab = input.stream().mapToDouble(d -> d).toArray();
            OptionalDouble average = Arrays.stream(inputToDoubleTab).average();
            return average.getAsDouble();
        } else {
            return 0;
        }
    }

    public static double countStandardDeviation(List<Double> input) {
        if (!input.isEmpty()) {
            StandardDeviation st = new StandardDeviation(false);
            double[] arr = input.stream().mapToDouble(d -> d).toArray();
            return st.evaluate(arr, arr.length);
        } else {
            return 0;
        }
    }

}
