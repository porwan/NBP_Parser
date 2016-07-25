package pl.parser.nbp.util;

import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtils {

    private DateUtils() {

    }

    public static List<Date> getAllDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) throws ParseException {
        List<Date> dates = new ArrayList<>();
        int days = Days.daysBetween(startDate, endDate).getDays();
        for (int i = 0; i < days + 1; i++) {
            LocalDate d = startDate.withFieldAdded(DurationFieldType.days(), i);
            dates.add(d.toDate());
        }
        return dates;
    }

    public static List<String> getAllYearsFromTwoDates(LocalDate startDate, LocalDate endDate) throws ParseException {
        List<String> years = new ArrayList<>();
        int numberOfYears = endDate.getYear() - startDate.getYear();
        for (int i = 0; i < numberOfYears + 1; i++) {
            LocalDate d = startDate.withFieldAdded(DurationFieldType.years(), i);
            years.add(String.valueOf(d.getYear()));
        }
        return years;
    }

    public static List<String> getListWithURLsForProvidedYears(List<String> years) {
        List<String> nbpDateUrlList = new ArrayList<>();
        for (String year : years) {
            String urlSuffix = ".txt";
            String urlPrefix = "http://www.nbp.pl/kursy/xml/dir";
            String url = ("2016".equals(year)) ? urlPrefix + urlSuffix : urlPrefix + year + urlSuffix;
            nbpDateUrlList.add(url);
        }
        return nbpDateUrlList;
    }
}
