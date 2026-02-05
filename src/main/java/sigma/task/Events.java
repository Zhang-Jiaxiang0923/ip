package sigma.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Subclass of Task that has start time and end time.
 */
public class Events extends Task {
    protected LocalDate start;
    protected LocalDate end;

    /**
     * Constructs the Event.
     *
     * @param description Event description.
     * @param start Event start time.
     * @param end Event end time.
     */
    public Events(String description, LocalDate start, LocalDate end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        String startTime = start.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        String endTime = end.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }

    @Override
    public String getSymbol() {
        return "E";
    }
}
