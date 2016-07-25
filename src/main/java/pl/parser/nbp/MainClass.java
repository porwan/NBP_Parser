package pl.parser.nbp;

import org.joda.time.LocalDate;
import pl.parser.nbp.exception.DateIsAfterToday;
import pl.parser.nbp.exception.DateYearIsNotValid;
import pl.parser.nbp.exception.EndDateAfterStartDateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainClass {

    public static void main(String... args) throws ParseException, EndDateAfterStartDateException, DateYearIsNotValid, DateIsAfterToday {
        String inputDateFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(inputDateFormat);
        LocalDate startDate = new LocalDate(dateFormat.parse(args[1]).getTime());
        LocalDate endDate = new LocalDate(dateFormat.parse(args[2]).getTime());
        checkIfDatesAreCorrect(startDate, endDate);
        NBPParser parser = new NBPParser();
        parser.handleInputData(startDate, endDate, args[0]);
    }

    private static void checkIfDatesAreCorrect(LocalDate startDate, LocalDate endDate) throws DateYearIsNotValid, DateIsAfterToday, EndDateAfterStartDateException {
        if (startDate.getYear() < 2002) {
            throw new DateYearIsNotValid();
        }
        if (endDate.isAfter(LocalDate.now())) {
            throw new DateIsAfterToday();
        }
        if (startDate.isAfter(endDate)) {
            throw new EndDateAfterStartDateException();
        }
    }

}

