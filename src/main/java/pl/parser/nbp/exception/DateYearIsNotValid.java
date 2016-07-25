package pl.parser.nbp.exception;

public class DateYearIsNotValid extends Exception {
    public DateYearIsNotValid() {
        this("Year of this date is not valid. Should be between 2002 and 2016");
    }

    public DateYearIsNotValid(String message) {
        super(message);
    }
}
