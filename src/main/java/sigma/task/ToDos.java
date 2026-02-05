package sigma.task;

/**
 * Subclass of Task that only have description.
 */
public class ToDos extends Task {
    public ToDos(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String getSymbol() {
        return "T";
    }
}
