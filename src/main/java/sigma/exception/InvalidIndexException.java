package sigma.exception;

/**
 * Signals that a provided task index is invalid.
 */
public class InvalidIndexException extends RuntimeException {
    public InvalidIndexException(String message) {
        super(message);
    }
}
