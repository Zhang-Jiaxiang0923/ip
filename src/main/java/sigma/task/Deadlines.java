package sigma.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Subclass of Task that has task description and a deadline.
 */
public class Deadlines extends Task {
    protected LocalDate by;

    /**
     * Constructs the Deadline based on description and deadline.
     * @param description The description of the Deadline task.
     * @param by The deadline of the Deadline task.
     */
    public Deadlines(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        String endTime = by.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        return "[D]" + super.toString() + " (by: " + endTime + ")";
    }

    @Override
    public String getSymbol() {
        return "D";
    }
}
