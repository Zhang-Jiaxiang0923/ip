package sigma.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import sigma.command.CommandType;

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


    public CommandType getCommand() {
        return this.command;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getStart() {
        return this.start;
    }

    public LocalDate getEnd() {
        return this.end;
    }

    public int getIndex() {
        return this.index;
    }

    public static LocalDate parseTime(String time) {
        return LocalDate.parse(time);
    }
}
