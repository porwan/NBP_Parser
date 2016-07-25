package pl.parser.nbp;

import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDate;
import pl.parser.nbp.exception.EndDateAfterStartDateException;
import pl.parser.nbp.model.ExchangeRateTable;
import pl.parser.nbp.model.Position;
import pl.parser.nbp.thread.TableCourse;
import pl.parser.nbp.util.CountUtils;
import pl.parser.nbp.util.DateUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

class NBPParser {
    private Map<String, String> NBP_DATA_URL_DATE_HASH_MAP = new TreeMap<>();
    private List<ExchangeRateTable> EXCHANGE_RATES = new ArrayList<>();
    private List<Double> SELLING_RATE = new ArrayList<>();
    private List<Double> BUYING_RATE = new ArrayList<>();

    void handleInputData(String firstDate, String secondDate, String currencyCode) throws ParseException, EndDateAfterStartDateException {
        String inputDateFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(inputDateFormat);
        LocalDate startDate = new LocalDate(dateFormat.parse(firstDate).getTime());
        LocalDate endDate = new LocalDate(dateFormat.parse(secondDate).getTime());
        List<Date> days;
        List<String> nbpDateUrlList;
        if (startDate.isBefore(endDate)) {
            days = DateUtils.getAllDaysBetweenTwoDates(startDate, endDate);
            List<String> years = DateUtils.getAllYearsFromTwoDates(startDate, endDate);
            nbpDateUrlList = DateUtils.getListWithURLsForProvidedYears(years);
        } else {
            throw new EndDateAfterStartDateException();
        }
        nbpDateUrlList.forEach(this::fillMapWithUrlDateAndHashForYear);
        setUpExchangeRateForDays(days);
        fillSellingAndBuyingRateLists(currencyCode);
        System.out.println(Math.round(CountUtils.countMeanFromListWithDouble(BUYING_RATE) * 10000d) / 10000d);
        System.out.println(Math.round(CountUtils.countStandardDeviation(SELLING_RATE) * 10000d) / 10000d);
    }


    /**
     * Create date format needed to get Exchange Rate objects from NBP .xml files.
     * Prepare Executor for multiple thread download. Method ends when all threads will end.
     *
     * @param days List with days for which Exchange Rate will be downloaded.
     */
    void setUpExchangeRateForDays(List<Date> days) {
        String nbpXmlNameDateFormat = "yyMMdd";
        SimpleDateFormat nbpFormat = new SimpleDateFormat(nbpXmlNameDateFormat);
        int threadsNumber = 600;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadsNumber);
        for (Date day : days) {
            String formattedData = nbpFormat.format(day);
            try {
                addExchangeRateToList(formattedData, executor);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        // Wait until all threads are finished
        while (!executor.isTerminated()) {
        }
    }

    /**
     * Run Callable class to get Exchange Rate from NBP for provided date.
     *
     * @param formattedData Date formatted to NBP_DATA_URL_DATE_HASH_MAP key format.
     * @param executor      Executor for multi thread run.
     * @throws InterruptedException
     * @throws ExecutionException
     */
    void addExchangeRateToList(String formattedData, ThreadPoolExecutor executor) throws InterruptedException, ExecutionException {
        if (NBP_DATA_URL_DATE_HASH_MAP.get(formattedData) != null) {
            TableCourse worker = new TableCourse(NBP_DATA_URL_DATE_HASH_MAP.get(formattedData) + formattedData);
            Future<ExchangeRateTable> future = executor.submit(worker);
            // Wait until variable is ready. This way other threads will not be blocked
            while (!future.isDone()) {
            }
            if (future.isDone()) {
                EXCHANGE_RATES.add(future.get());
            }
        }
    }

    /**
     * Get position with selected currency code from exchange rates saved in local variable.
     *
     * @param currencyCode Currency code
     */
    void fillSellingAndBuyingRateLists(String currencyCode) {
        for (ExchangeRateTable exchangeRate : EXCHANGE_RATES) {
            List<Position> positions = exchangeRate.getPosition();
            Position position = positions.stream().filter(p -> currencyCode.equals(p.getCurrencyCode())).findFirst().orElse(null);
            SELLING_RATE.add(Double.valueOf(position.getSellingRate().replace(',', '.')));
            BUYING_RATE.add(Double.valueOf(position.getBuyingRate().replace(',', '.')));
        }
    }

    /**
     * Gets Hash for NBP .xml files from provided url.
     *
     * @param url URL to NBP .txt file
     */
    void fillMapWithUrlDateAndHashForYear(String url) {
        InputStream input = null;
        try {
            input = new URL(url).openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            addNbpUrlHashToMapFromReader(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * Fill local Map with formatted date as key and prefix of .xm file hash as value.
     *
     * @param bufferedReader Reader with hash to .xml NBP .xml files
     * @throws IOException
     */
    void addNbpUrlHashToMapFromReader(BufferedReader bufferedReader) throws IOException {
        String nbpUrlHashSingleLine;
        while ((nbpUrlHashSingleLine = bufferedReader.readLine()) != null) {
            String prefixLetter = nbpUrlHashSingleLine.substring(0, 1);
            if ("c".equals(prefixLetter)) {
                String urlHash = nbpUrlHashSingleLine.substring(0, 5);
                String urlDate = nbpUrlHashSingleLine.substring(5, 11);
                NBP_DATA_URL_DATE_HASH_MAP.put(urlDate, urlHash);
            }
        }
    }
}

