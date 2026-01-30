package sigma.exception;

/**
 * Signals that the command of user input is unrecognizable.
 */
public class UnknownCommandException extends RuntimeException {
    public UnknownCommandException(String message) {
        super(message);
    }
}
