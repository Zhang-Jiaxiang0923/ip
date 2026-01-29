package sigma.ui;

import java.util.ArrayList;
import java.util.Scanner;
import sigma.task.TaskList;

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
}
