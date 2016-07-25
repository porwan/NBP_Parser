package pl.parser.nbp.exception;

public class DateIsAfterToday extends Exception {
    public DateIsAfterToday() {
        this("End date cannot be later than today.");
    }

    public DateIsAfterToday(String message) {
        super(message);
    }
}
