package sigma.exception;

/**
 * Signals that the stored data file is corrupted or cannot be parsed into valid tasks.
 */
public class CorruptedFileException extends RuntimeException {
    public CorruptedFileException(String message) {
        super(message);
    }
}
