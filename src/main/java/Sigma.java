import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

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
        ArrayList<Task> todo = new ArrayList<>();
        Path target = Paths.get(System.getProperty("user.dir"))
                           .resolve(Paths.get("data", "Sigma.txt"));
        List<String> lines = new ArrayList<>();
        try {
            if (Files.notExists(target)) {
                Files.createDirectories(target.getParent());
                Files.createFile(target);
            } else {
                try {
                    lines = Files.readAllLines(target);
                    ReadFromDisk(todo, lines);
                } catch (CorruptedFileException e) {
                    Files.deleteIfExists(target);
                    Files.createDirectories(target.getParent());
                    Files.createFile(target);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("File does not exist and cannot create file: " + target, e);
        }

        System.out.println("Hello from\n" + logo);
        System.out.println(indentation + "_".repeat(width));
        System.out.println(indentation + "Hello! I'm " + name);
        System.out.println(indentation + "What can I do for you?");
        System.out.println(indentation + "_".repeat(width));
        Loop:
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            System.out.println(indentation + "_".repeat(width));
            try {
                ParsedInput input = handleCommand(line);
                CommandType command = input.getCommand();
                switch (command) {
                    case BYE:
                        break Loop;
                    case LIST:
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
                    case MARK: {
                        int index = input.getIndex();
                        if (index >= todo.size() || index < 0) {
                            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
                        }
                        Task task = todo.get(index);
                        task.markAsDone();
                        String content = lines.get(index);
                        lines.set(index, setDoneFlag(content));
                        System.out.println(indentation + "Nice! I've marked this task as done:");
                        System.out.println(indentation + "  " + task);
                        break;
                    }
                    case UNMARK: {
                        int index = input.getIndex();
                        if (index >= todo.size() || index < 0) {
                            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
                        }
                        Task task = todo.get(index);
                        task.unmarkDone();
                        String content = lines.get(index);
                        lines.set(index, resetDoneFlag(content));
                        System.out.println(indentation + "Ok, I've marked this task as not done yet:");
                        System.out.println(indentation + "  " + task);
                        break;
                    }
                    case DELETE: {
                        int index = input.getIndex();
                        if (index >= todo.size() || index < 0) {
                            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
                        }
                        Task task = todo.get(index);
                        todo.remove(index);
                        lines.remove(index);
                        System.out.println(indentation + "Noted. I've removed this task:");
                        System.out.println(indentation + "  " + task);
                        System.out.println(
                                indentation + String.format("Now you have %d tasks in the list", todo.size())
                        );
                        break;
                    }
                    case TODO: {
                        String description = input.getDescription();
                        Task task = new ToDos(description);
                        todo.add(task);
                        lines.add("T | 0 | " + description + " | - | -");
                        System.out.println(indentation + "Got it. I've added this task:");
                        System.out.println(indentation + "  " + task);
                        System.out.println(indentation + String.format("Now you have %d tasks in the list.", todo.size()));
                        break;
                    }
                    case DEADLINE: {
                        LocalDate end = input.getEnd();
                        String description = input.getDescription();
                        Task task = new Deadlines(description, end);
                        todo.add(task);
                        lines.add("D | 0 | " + task.getDescription() + " | - | " + end);
                        System.out.println(indentation + "Got it. I've added this task:");
                        System.out.println(indentation + "  " + task);
                        System.out.println(indentation + String.format("Now you have %d tasks in the list.", todo.size()));
                        break;
                    }
                    case EVENT: {
                        LocalDate end = input.getEnd();
                        LocalDate start = input.getStart();
                        String description = input.getDescription();
                        Task task = new Events(description, start, end);
                        todo.add(task);
                        lines.add("E | 0 | " + description + " | " + start + " | " + end);
                        System.out.println(indentation + "Got it. I've added this task:");
                        System.out.println(indentation + "  " + task);
                        System.out.println(indentation + String.format("Now you have %d tasks in the list.", todo.size()));
                        break;
                    }
                    default:
                }
                try {
                    Files.write(target, lines);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to write in the file.");
                }

            } catch (MissingElementException e) {
                System.out.println(indentation + e.getMessage());
            } catch (UnknownCommandException e) {
                System.out.println(indentation + "Sorry, I don't know what that means QAQ");
            } catch (InvalidIndexException | NumberFormatException e) {
                System.out.println(indentation + "Oops, need a valid number as task index Ծ‸Ծ");
            } finally {
                System.out.println(indentation + "_".repeat(width));
            }

        }
        System.out.println(indentation + "Bye. Hope to see you again soon!");
        System.out.println(indentation + "_".repeat(width));
    }

    public static ParsedInput handleCommand(String line) throws MissingElementException, UnknownCommandException, NumberFormatException {
        String[] parts = line.trim().split("\\s+", 2);
        String s = parts[0];
        switch (s) {
            case "bye":{
                return new ParsedInput(CommandType.BYE);
            }
            case "list": {
                return new ParsedInput(CommandType.LIST);
            }
            case "mark": {
                if (parts.length == 1) {
                    throw new MissingElementException("Oops, missing number. Which task do you want to mark?");
                } else {
                    String taskNum = parts[1];
                    int index = Integer.parseInt(taskNum) - 1;
                    return new ParsedInput(CommandType.MARK, index);
                }
            }
            case "unmark": {
                if (parts.length == 1) {
                    throw new MissingElementException("Oops, missing number. Which task do you want to unmark?");
                } else {
                    String taskNum = parts[1];
                    int index = Integer.parseInt(taskNum) - 1;
                    return new ParsedInput(CommandType.UNMARK, index);
                }
            }
            case "delete": {
                if (parts.length == 1) {
                    throw new MissingElementException("Oops, missing number. Which task do you want to delete?");
                } else {
                    String taskNum = parts[1];
                    int index = Integer.parseInt(taskNum) - 1;
                    return new ParsedInput(CommandType.DELETE, index);
                }
            }
            case "todo": {
                if (parts.length == 1) {
                    throw new MissingElementException("Oops, could you give me the todo description?");
                } else {
                    String description = parts[1];
                    return new ParsedInput(CommandType.TODO, description);
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
                        return new ParsedInput(CommandType.DEADLINE, p2[0].trim(), p2[1].trim());
                    }
                }
            }
            case "event": {
                if (parts.length == 1) {
                    throw new MissingElementException(
                            "Could you give me the task description, start time and end time?"
                    );
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
                            return new ParsedInput(
                                    CommandType.EVENT, p2[0].trim(), p3[0].trim(), p3[1].trim()
                            );

                        }
                    }
                }
            }
            default: {
                throw new UnknownCommandException("unknown command");
            }
        }

    }

    public static void ReadFromDisk(ArrayList<Task> todo, List<String> lines) throws CorruptedFileException{
        for (String line: lines) {
            String[] p = line.split("\\|", -1);
            if (p.length != 5) {
                throw new CorruptedFileException("Files are corrupted");
            }
            switch (p[0].trim()) {
                case "T": {
                    Task task = new ToDos(p[2].trim());
                    if (p[1].equals("1")) {
                        task.markAsDone();
                    }
                    todo.add(task);
                    break;
                }
                case "D": {
                    Task task = new Deadlines(p[2].trim(), LocalDate.parse(p[4].trim()));
                    if (p[1].equals("1")) {
                        task.markAsDone();
                    }
                    todo.add(task);
                    break;
                }
                case "E": {
                    Task task = new Events(p[2].trim(), LocalDate.parse(p[3].trim()), LocalDate.parse(p[4].trim()));
                    if (p[1].equals("1")) {
                        task.markAsDone();
                    }
                    todo.add(task);
                    break;
                }
            }
        }
    }

    public static String setDoneFlag(String line) {
        String[] p = line.split("\\|", -1);
        p[1] = " 1 ";
        return String.join("|", p);
    }

    public static String resetDoneFlag(String line) {
        String[] p = line.split("\\|", -1);
        p[1] = " 0 ";
        return String.join("|", p);
    }

}
