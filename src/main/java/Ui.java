import java.util.ArrayList;
import java.util.Scanner;
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

    public void showWelcome() {
        System.out.println("Hello from\n" + LOGO);
        System.out.println(indentation + "_".repeat(WIDTH));
        System.out.println(indentation + "Hello! I'm " + NAME);
        System.out.println(indentation + "What can I do for you?");
        System.out.println(indentation + "_".repeat(WIDTH));
    }

    public void showDivisionLine() {
        System.out.println(indentation + "_".repeat(WIDTH));
    }

    public void printMessage(String message) {
        System.out.println(indentation + message);
    }

    public void showGoodbye() {
        printMessage("Bye. Hope to see you again soon!");
    }

    public void printTasks(ArrayList<Task> todo) {
        int num = 1;
        if (todo.isEmpty()) {
            printMessage("Todo list is empty :)");
        } else {
            printMessage("Here are the tasks in your list:");
            for (Task task : todo) {
                printMessage(num + "." + task);
                num++;
            }
        }
    }
}
