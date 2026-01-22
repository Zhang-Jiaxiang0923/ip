import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Sigma {
    public static void main(String[] args) {
        String logo =
                " ____   ___    ____   __  __     _\n"
                        + "/ ___| |_ _|  / ___| |  \\/  |   / \\\n"
                        + "\\___ \\  | |  | |  _  | |\\/| |  / _ \\\n"
                        + " ___) | | |  | |_| | | |  | | / ___ \\\n"
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
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.trim().split("\\s+", 2);
            String s = parts[0];
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
                            System.out.println(indentation + String.valueOf(num) + "." + task);
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
                    System.out.println(indentation + "  " + task);
                    break;
                }
                case "unmark": {
                    int index = Integer.parseInt(parts[1]) - 1;
                    Task task = todo.get(index);
                    task.unmarkDone();
                    System.out.println(indentation + "Ok, I've marked this task as not done yet:");
                    System.out.println(indentation + "  " + task);
                    break;
                }
                case "todo": {
                    Task task = new ToDos(parts[1]);
                    todo.add(task);
                    System.out.println(indentation + "Got it. I've added this task:");
                    System.out.println(indentation + "  " + task);
                    System.out.println(indentation + String.format("Now you have %d tasks in the list.", todo.size()));
                    break;
                }
                case "deadline": {
                    String[] p2 = parts[1].split("\\s+/by\\s+", 2);
                    Task task = new Deadlines(p2[0], p2[1]);
                    todo.add(task);
                    System.out.println(indentation + "Got it. I've added this task:");
                    System.out.println(indentation + "  " + task);
                    System.out.println(indentation + String.format("Now you have %d tasks in the list.", todo.size()));
                    break;
                }
                case "event": {
                    String[] p2 = parts[1].split("\\s+/from\\s+", 2);
                    String description = p2[0].trim();
                    String[] p3 = p2[1].trim().split("\\s+/to\\s+", 2);
                    String from = p3[0].trim();
                    String to = p3[1].trim();
                    Task task = new Events(description, from, to);
                    todo.add(task);
                    System.out.println(indentation + "Got it. I've added this task:");
                    System.out.println(indentation + "  " + task);
                    System.out.println(indentation + String.format("Now you have %d tasks in the list.", todo.size()));
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
