package pl.parser.nbp;

import pl.parser.nbp.exception.EndDateAfterStartDateException;
import pl.parser.nbp.model.ExchangeRateTable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainClass {

    private static Map<String, String> NBP_DATA_URL_DATE_HASH_MAP = new TreeMap<>();
    private static List<ExchangeRateTable> RATES = new ArrayList<>();
    private static List<Double> SELLING_RATE = new ArrayList<>();
    private static List<Double> BUYING_RATE = new ArrayList<>();
    private static final int THREADS_NUMBER = 600;

    public static void main(String... args) throws ParseException, EndDateAfterStartDateException {
        NBPParser parser = new NBPParser();
        String[] enterValues = {"EUR", "2013-01-28", "2013-01-31"};

        parser.handleInputData(enterValues[1], enterValues[2], enterValues[0]);
    }


}

