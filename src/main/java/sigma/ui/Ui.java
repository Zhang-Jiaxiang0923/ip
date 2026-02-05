package sigma.ui;

import java.util.ArrayList;

import sigma.task.Task;
import sigma.task.TaskList;


/**
 * Provides the text-based interface for Sigma (prompts, responses, and error messages).
 */
public class Ui {
    private static final String LOGO =
            " ____   ___    ____   __  __     _\n"
                    + "/ ___| |_ _|  / ___| |  \\/  |   / \\\n"
                    + "\\___ \\  | |  | |  _  | |\\/| |  / _ \\\n"
                    + " ___) | | |  | |_| | | |  | | / ___ \\\n"
                    + "|____/ |___|  \\____| |_|  |_|/_/   \\_\\\n";
    private static final String NAME = "Sigma";
    private static final int WIDTH = 60;
    private static final String indentation = "    ";

    public Ui() {

    }

    /**
     * Prints the logo and welcome messages.
     */
    public void showWelcome() {
        System.out.println("Hello from\n" + LOGO);
        System.out.println(indentation + "_".repeat(WIDTH));
        System.out.println(indentation + "Hello! I'm " + NAME);
        System.out.println(indentation + "What can I do for you?");
        System.out.println(indentation + "_".repeat(WIDTH));
    }

    /**
     * Prints the division line between user input and application output.
     */
    public void showDivisionLine() {
        System.out.println(indentation + "_".repeat(WIDTH));
    }

    /**
     * Prints the message with indentations.
     *
     * @param message The message to be printed.
     */
    public void printMessage(String message) {
        System.out.println(indentation + message);
    }

    /**
     * Prints the goodbye message.
     */
    public void showGoodbye() {
        printMessage("Bye. Hope to see you again soon!");
    }

    /**
     * Prints all tasks in the tasklist.
     *
     * @param taskList User's task list to be displayed.
     */
    public void printTasks(TaskList taskList) {
        if (taskList.isEmpty()) {
            printMessage("Todo list is empty :)");
        } else {
            printMessage("Here are the tasks in your list:");
            for (int i = 1; i <= taskList.getLength(); i++) {
                printMessage(i + "." + taskList.getTask(i - 1));
            }
        }
    }

    /**
     * Prints tasks that are found based on the keyword.
     * @param finding An arraylist of tasks that are found.
     */
    public void printFinding(ArrayList<Task> finding) {
        if (finding.isEmpty()) {
            printMessage("No matching task in your list");
        } else {
            printMessage("Here are the matching tasks in your list:");
            for (int i = 1; i <= finding.size(); i++) {
                printMessage(i + "." + finding.get(i - 1));
            }
        }
    }
}
