package pl.parser.nbp.exception;

public class EndDateAfterStartDateException extends Exception {
    public EndDateAfterStartDateException() {
        this("Start date is after end date.");
    }

    public EndDateAfterStartDateException(String message) {
        super(message);
    }
}
