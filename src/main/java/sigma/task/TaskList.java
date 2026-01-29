package sigma.task;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> todo;
    public TaskList() {
        this.todo = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.todo.add(task);
    }

    public void deleteTask(int index) {
        this.todo.remove(index);
    }

    public void markTask(int index) {
        Task task = this.todo.get(index);
        task.markAsDone();
    }

    public void unmarkTask(int index) {
        Task task = this.todo.get(index);
        task.unmarkDone();
    }

    public Task getTask(int index) {
        return todo.get(index);
    }

    public boolean isEmpty() {
        return this.todo.isEmpty();
    }

    public int getLength() {
        return this.todo.size();
    }

}
