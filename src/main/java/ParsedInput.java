public class ParsedInput {
    protected CommandType command;
    protected String description, start, end;
    protected int index;
    public ParsedInput (CommandType command, String description, int index, String start, String end) {
        this.command = command;
        this.description = description;
        this.start = start;
        this.end = end;
        this.index = index;
    }

    public CommandType getCommand() {
        return this.command;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStart() {
        return this.start;
    }

    public String getEnd() {
        return this.end;
    }

    public int getIndex() {
        return this.index;
    }
}
