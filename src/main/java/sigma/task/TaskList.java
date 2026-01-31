package sigma.task;

import java.util.ArrayList;

/**
 * Stores and manages the list of tasks in Sigma.
 */
public class TaskList {
    private final ArrayList<Task> todo;

    public TaskList() {
        this.todo = new ArrayList<>();
    }

    /**
     * Adds a task into the tasklist.
     *
     * @param task Task to be added into tasklist.
     */
    public void addTask(Task task) {
        this.todo.add(task);
    }

    /**
     * Deletes specific task in the tasklist.
     *
     * @param index The index of the task that is going to be deleted.
     */
    public void deleteTask(int index) {
        this.todo.remove(index);
    }

    /**
     * Marks a specific task in the tasklist as done.
     *
     * @param index The index of the task that is going to be marked.
     */
    public void markTask(int index) {
        Task task = this.todo.get(index);
        task.markAsDone();
    }

    /**
     * Unmarks a specific task in the tasklist as hasn't done.
     *
     * @param index The index of the task that is going to be unmarked.
     */
    public void unmarkTask(int index) {
        Task task = this.todo.get(index);
        task.unmarkDone();
    }

    public ArrayList<Task> lookUp(String keywords) {
        ArrayList<Task> finding = new ArrayList<>();
        for (Task task : todo) {
            String description = task.getDescription();
            if (description.contains(keywords)) {
                finding.add(task);
            }
        }
        return finding;
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
