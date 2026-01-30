package sigma.exception;

/**
 * Signals that required information is missing from the user input.
 */
public class MissingElementException extends RuntimeException {
    public MissingElementException(String message) {
        super(message);
    }
}
