package sigma.task;

/**
 * Represents tasks that user of Sigma can operate.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs the Task based on description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getSymbol() {
        return " ";
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "]" + " " + this.getDescription();
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Unmarks the task as hasn't done.
     */
    public void unmarkDone() {
        this.isDone = false;
    }

}
