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
    private static final String INDENTATION = "    ";

    public Ui() {

    }

    /**
     * Prints the logo and welcome messages.
     */
    public void showWelcome() {
        System.out.println("Hello from\n" + LOGO);
        System.out.println(INDENTATION + "_".repeat(WIDTH));
        System.out.println(INDENTATION + "Hello! I'm " + NAME);
    System.out.println(INDENTATION + "What can I do for you?");
        System.out.println(INDENTATION + "_".repeat(WIDTH));
    }

    /**
     * Prints the division line between user input and application output.
     */
    public void showDivisionLine() {
        System.out.println(INDENTATION + "_".repeat(WIDTH));
    }

    /**
     * Prints the message with indentations.
     *
     * @param message The message to be printed.
     */
    public void printMessage(String message) {
        System.out.println(INDENTATION + message);
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
                printMessage(i + "." + taskList.getTask(i-1));
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

    /**
     * Gets the String of tasks that are found based on the keyword.
     * @param finding An arraylist of tasks that are found.
     * @return String of tasks that are found based on the keyword.
     */
    public String getFinding(ArrayList<Task> finding) {
        if (finding.isEmpty()) {
            return "No matching task in your list";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Here are the matching tasks in your list:\n");
            for (int i = 1; i <= finding.size(); i++) {
                sb.append(i + "." + finding.get(i - 1) + "\n");
            }
            return sb.toString();
        }
    }

    public String getTasks(TaskList taskList) {
        if (taskList.isEmpty()) {
            return "Todo list is empty :)";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Here are the tasks in your list:\n");
            for (int i = 1; i <= taskList.getLength(); i++) {
                sb.append(i + "." + taskList.getTask(i - 1) + "\n");
            }
            return sb.toString();
        }
    }

    public String getGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    public String getArchiveMessage(Task task) {
        return "Nice! I've archived this task:\n" + "  " + task;
    }

    public String getMarkMessage(Task task) {
        return "Nice! I've marked this task as done:" + "\n"
                + "  " + task;
    }

    public String getUnmarkMessage(Task task) {
        return "Nice! I've marked this task as hasn't done:" + "\n"
                + "  " + task;
    }

    public String getDeleteMessage(Task task, int len) {
        return "Noted. I've removed this task:\n"
                + "  " + task
                + String.format("Now you have %d tasks in the list", len);
    }

    public String getAddMessage(TaskList taskList) {
        int len = taskList.getLength();
        Task task = taskList.getTask(len-1);
        return "Got it. I've added this task:\n"
                + "  " + task + "\n"
                + String.format("Now you have %d tasks in the list.", len);
    }

    public String getWelcomeMessage() {
        return "Hello! I'm Sigma. \n"
            + "What can I do for you?" + "\n";
    }

    public void printMarkMessage(Task task) {
        printMessage("Nice! I've marked this task as done:");
        printMessage("  " + task);
    }

    public void printUnmarkMessage(Task task) {
        printMessage("Ok, I've marked this task as not done yet:");
        printMessage("  " + task);
    }

    public void printDeleteMessage(Task task, int length) {
        printMessage("Noted. I've removed this task:");
        printMessage("  " + task);
        printMessage(String.format("Now you have %d tasks in the list", length));

    }

    public void printAddMessage(TaskList tasklist) {
        int length = tasklist.getLength();
        printMessage("Got it. I've added this task:");
        printMessage("  " + tasklist.getTask(length-1));
        printMessage(String.format("Now you have %d tasks in the list.", length));
    }

    public void printArchiveMessage(Task task) {
        printMessage("Nice! I've archived this task:");
        printMessage("  " + task);
    }

}
