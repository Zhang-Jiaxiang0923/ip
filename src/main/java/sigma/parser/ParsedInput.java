package sigma.parser;

import java.time.LocalDate;

import sigma.command.CommandType;

/**
 * Stores the command type and extracted arguments from user input.
 */
public class ParsedInput {
    protected CommandType command;
    protected String description;
    protected LocalDate start;
    protected LocalDate end;
    protected int index;

    /**
     * Constructs the ParsedInput by CommandType and Task description.
     *
     * @param command The CommandType of the user input.
     * @param description The description of the task.
     */
    public ParsedInput(CommandType command, String description) {
        assert command != null;
        assert description != null;
        this.command = command;
        this.description = description;
    }

    /**
     * Constructs the ParsedInput by CommandType and index of the task.
     *
     * @param command The CommandType of the user input.
     * @param index The index of the task that user want to operate.
     */
    public ParsedInput(CommandType command, int index) {
        assert command != null;
        assert index >= 0;
        this.command = command;
        this.index = index;
    }

    /**
     * Constructs the ParsedInput by CommandType, task description and task deadline.
     *
     * @param command The CommandType of the user input.
     * @param description The description of the task.
     * @param times The time(s) of the task: [end] or [start, end].
     */
    public ParsedInput(CommandType command, String description, String... times) {
        assert command != null;
        assert description != null;
        this.command = command;
        this.description = description;
        if (times.length == 1) {
            this.end = parseTime(times[0]);
        } else if (times.length == 2) {
            this.start = parseTime(times[0]);
            this.end = parseTime(times[1]);
        } else {
            throw new IllegalArgumentException("Expected 1 (deadline) or 2 (event) time values.");
        }
    }

    /**
     * Constructs the ParsedInput by CommandType.
     *
     * @param command The CommandType of the user input.
     */
    public ParsedInput(CommandType command) {
        assert command != null;
        this.command = command;
    }

    /**
     * Returns the command type.
     *
     * @return Command type.
     */
    public CommandType getCommand() {
        return this.command;
    }

    /**
     * Returns the description of the task.
     *
     * @return Description of the task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the start time of task.
     *
     * @return The start time of task.
     */
    public LocalDate getStart() {
        return this.start;
    }

    /**
     * Returns the end time of task.
     *
     * @return The end time of task.
     */
    public LocalDate getEnd() {
        return this.end;
    }

    /**
     * Returns the index of the task that user want to operate.
     *
     * @return The index of the task that user want to operate.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Returns LocalDate of the given time.
     *
     * @param time Time.
     * @return LocalDate of the given time
     */
    public static LocalDate parseTime(String time) {
        assert time != null;
        return LocalDate.parse(time);
    }
}
