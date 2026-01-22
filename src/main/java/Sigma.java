import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Sigma {
    public static void main(String[] args) {
        String logo =
                " ____   ___    ____   __  __     _    \n"
                        + "/ ___| |_ _|  / ___| |  \\/  |   / \\   \n"
                        + "\\___ \\  | |  | |  _  | |\\/| |  / _ \\  \n"
                        + " ___) | | |  | |_| | | |  | | / ___ \\ \n"
                        + "|____/ |___|  \\____| |_|  |_|/_/   \\_\\\n";
        String name = "Sigma";
        int width = 60;
        String indentation = "    ";
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello from\n" + logo);

        List<Task> todo = new ArrayList<Task>();

        System.out.println(indentation + "_".repeat(width));
        System.out.println(indentation + "Hello! I'm " + name);
        System.out.println(indentation + "What can I do for you?");
        System.out.println(indentation + "_".repeat(width));
        Loop:
        while (true) {
            String line = sc.nextLine();
            String[] parts = line.trim().split("\\s+");
            String s = parts[0];
//            Integer index = Integer.valueOf(parts[1]);
            System.out.println(indentation + "_".repeat(width));
            switch (s) {
                case "bye": {
                    break Loop;
                }
                case "list": {
                    Integer num = 1;
                    if (todo.isEmpty()) {
                        System.out.println(indentation + "Todo list is empty :)");
                    } else {
                        System.out.println(indentation + "Here are the tasks in your list:");
                        for (Task task : todo) {
                            System.out.println(indentation + String.valueOf(num) + "." + task.getFullDescription());
                            num++;
                        }
                    }
                    break;
                }
                case "mark": {
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task task = todo.get(index);
                    task.markAsDone();
                    System.out.println(indentation + "Nice! I've marked this task as done:");
                    System.out.println(indentation + "  " + task.getFullDescription());
                    break;
                }
                case "unmark": {
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task task = todo.get(index);
                    task.unmarkDone();
                    System.out.println(indentation + "Ok, I've marked this task as not done yet:");
                    System.out.println(indentation + "  " + task.getFullDescription());
                    break;
                }
                default: {
                    System.out.println(indentation + "added: " + line);
                    Task task = new Task(line);
                    todo.add(task);
                }
            }
            System.out.println(indentation + "_".repeat(width));
        }
        System.out.println(indentation + "Bye. Hope to see you again soon!");
        System.out.println(indentation + "_".repeat(width));
    }
}
