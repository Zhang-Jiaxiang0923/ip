package sigma.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import sigma.command.CommandType;

/**
 * Stores the command type and extracted arguments from user input.
 */
public class ParsedInput {
    protected CommandType command;
    protected String description;
    protected LocalDate start, end;
    protected int index;

    public ParsedInput(CommandType command, String description) {
        this.command = command;
        this.description = description;
    }

    public ParsedInput(CommandType command, int index) {
        this.command = command;
        this.index = index;
    }

    public ParsedInput(CommandType command, String description, String end) {
        this.command = command;
        this.description = description;
        this.end = parseTime(end);
    }

    public ParsedInput(CommandType command, String description, String start, String end) {
        this.command = command;
        this.description = description;
        this.start = parseTime(start);
        this.end = parseTime(end);
    }

    public ParsedInput(CommandType command) {
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
        return LocalDate.parse(time);
    }
}
