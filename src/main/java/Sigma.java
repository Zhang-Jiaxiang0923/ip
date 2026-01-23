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

        List<Task> todo = new ArrayList<>();

        System.out.println(indentation + "_".repeat(width));
        System.out.println(indentation + "Hello! I'm " + name);
        System.out.println(indentation + "What can I do for you?");
        System.out.println(indentation + "_".repeat(width));
        Loop:
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            System.out.println(indentation + "_".repeat(width));
            try {
                String[] arr = handleCommand(line);
                String command = arr[0];
                switch (command) {
                    case "bye":
                        break Loop;
                    case "list":
                        int num = 1;
                        if (todo.isEmpty()) {
                            System.out.println(indentation + "Todo list is empty :)");
                        } else {
                            System.out.println(indentation + "Here are the tasks in your list:");
                            for (Task task : todo) {
                                System.out.println(indentation + num + "." + task);
                                num++;
                            }
                        }
                        break;
                    case "mark": {
                        int index = Integer.parseInt(arr[2]) - 1;
                        if (index >= todo.size() || index < 0) {
                            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
                        }
                        Task task = todo.get(index);
                        task.markAsDone();
                        System.out.println(indentation + "Nice! I've marked this task as done:");
                        System.out.println(indentation + "  " + task);
                        break;
                    }
                    case "unmark": {
                        int index = Integer.parseInt(arr[2]) - 1;
                        if (index >= todo.size() || index < 0) {
                            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
                        }
                        Task task = todo.get(index);
                        task.unmarkDone();
                        System.out.println(indentation + "Ok, I've marked this task as not done yet:");
                        System.out.println(indentation + "  " + task);
                        break;
                    }
                    case "delete": {
                        int index = Integer.parseInt(arr[2]) - 1;
                        if (index >= todo.size() || index < 0) {
                            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
                        }
                        Task task = todo.get(index);
                        todo.remove(index);
                        System.out.println(indentation + "Noted. I've removed this task:");
                        System.out.println(indentation + "  " + task);
                        System.out.println(
                                indentation + String.format("Now you have %d tasks in the list", todo.size())
                        );
                        break;
                    }
                    case "todo": {
                        Task task = new ToDos(arr[1]);
                        todo.add(task);
                        System.out.println(indentation + "Got it. I've added this task:");
                        System.out.println(indentation + "  " + task);
                        System.out.println(indentation + String.format("Now you have %d tasks in the list.", todo.size()));
                        break;
                    }
                    case "deadline": {
                        Task task = new Deadlines(arr[1], arr[4]);
                        todo.add(task);
                        System.out.println(indentation + "Got it. I've added this task:");
                        System.out.println(indentation + "  " + task);
                        System.out.println(indentation + String.format("Now you have %d tasks in the list.", todo.size()));
                        break;
                    }
                    case "event": {
                        Task task = new Events(arr[1], arr[3], arr[4]);
                        todo.add(task);
                        System.out.println(indentation + "Got it. I've added this task:");
                        System.out.println(indentation + "  " + task);
                        System.out.println(indentation + String.format("Now you have %d tasks in the list.", todo.size()));
                        break;
                    }
                    default:
                }
            } catch (MissingElementException e) {
                System.out.println(indentation + e.getMessage());
            } catch (UnknownCommandException e) {
                System.out.println(indentation + "Sorry, I don't know what that means QAQ");
            } catch (InvalidIndexException e) {
                System.out.println(indentation + e.getMessage());
            } finally {
                System.out.println(indentation + "_".repeat(width));
            }

        }
        System.out.println(indentation + "Bye. Hope to see you again soon!");
        System.out.println(indentation + "_".repeat(width));
    }

    public static String[] handleCommand(String line) throws MissingElementException, UnknownCommandException {
        String[] parts = line.trim().split("\\s+", 2);
        String s = parts[0];
        switch (s) {
            case "bye":{
                return new String[] {"bye", "", "", "", ""};
            }
            case "list": {
                return new String[]{"list", "", "", "", ""};
            }
            case "mark": {
                if (parts.length == 1) {
                    throw new MissingElementException("Oops, missing number. Which task do you want to mark?");
                } else {
                    String taskNum = parts[1];
                    return new String[]{"mark", "", taskNum, "", ""};
                }
            }
            case "unmark": {
                if (parts.length == 1) {
                    throw new MissingElementException("Oops, missing number. Which task do you want to unmark?");
                } else {
                    String taskNum = parts[1];
                    return new String[] {"unmark", "", taskNum, "", ""};
                }
            }
            case "delete": {
                if (parts.length == 1) {
                    throw new MissingElementException("Oops, missing number. Which task do you want to delete?");
                } else {
                    String taskNum = parts[1];
                    return new String[]{"delete", "", taskNum, "", ""};
                }
            }
            case "todo": {
                if (parts.length == 1) {
                    throw new MissingElementException("Oops, could you give me the todo description?");
                } else {
                    String description = parts[1];
                    return new String[]{"todo", description, "", "", ""};
                }
            }
            case "deadline": {
                if (parts.length == 1) {
                    throw new MissingElementException("Could you give me task description and deadline?");
                } else {
                    String[] p2 = parts[1].split("(?:^|\\s+)/by\\s+", 2);
                    if (p2.length == 1) {
                        throw new MissingElementException("Could you give me the deadline?");
                    } else if (p2[0].trim().isEmpty()) {
                        throw new MissingElementException("Could you give me task description?");
                    } else {
                        return new String[]{"deadline", p2[0].trim(), "", "", p2[1].trim()};
                    }
                }
            }
            case "event": {
                if (parts.length == 1) {
                    throw new MissingElementException("Could you give me the task description, start time and end time?");
                } else {
                    String[] p2 = parts[1].split("(?:^|\\s+)/from\\s+", 2);
                    if (p2.length == 1) {
                        throw new MissingElementException("Could you give me the start time?");
                    } else if(p2[0].trim().isEmpty()) {
                        throw new MissingElementException("Could you give me the task description?");
                    } else {
                        String[] p3 = p2[1].trim().split("(?:^|\\s+)/to\\s+", 2);
                        if (p3.length == 1) {
                            throw new MissingElementException("Could you give me the end time");
                        } else if (p3[0].trim().isEmpty()) {
                            throw new MissingElementException("Oops, the start time is empty QAQ");
                        } else if (p3[1].trim().isEmpty()) {
                            throw new MissingElementException("Oops, the end time is empty QAQ");
                        } else {
                            return new String[] {"event", p2[0].trim(), "", p3[0].trim(), p3[1].trim()};
                        }
                    }
                }
            }
            default: {
                throw new UnknownCommandException("unknown command");
            }
        }

    }
}
