import java.time.LocalDate;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Sigma {
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    public Sigma(Path target) {
        ui = new Ui();
        this.storage = new Storage(target);
        this.taskList = new TaskList();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        this.storage.load(this.taskList);
        this.ui.showWelcome();
        Loop:
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            this.ui.showDivisionLine();
            try {
                ParsedInput input = Parser.parseInput(line);
                CommandType command = input.getCommand();
                switch (command) {
                    case BYE:
                        this.ui.showGoodbye();
                        break Loop;
                    case LIST:
                        this.ui.printTasks(this.taskList);
                        break;
                    case MARK: {
                        int index = input.getIndex();
                        if (index >= taskList.getLength() || index < 0) {
                            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
                        }
                        taskList.markTask(index);
                        this.storage.writeMark(index);
                        this.ui.printMessage("Nice! I've marked this task as done:");
                        this.ui.printMessage("  " + taskList.getTask(index));
                        break;
                    }
                    case UNMARK: {
                        int index = input.getIndex();
                        if (index >= taskList.getLength() || index < 0) {
                            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
                        }
                        taskList.unmarkTask(index);
                        this.storage.writeUnmark(index);
                        this.ui.printMessage("Ok, I've marked this task as not done yet:");
                        this.ui.printMessage("  " + taskList.getTask(index));
                        break;
                    }
                    case DELETE: {
                        int index = input.getIndex();
                        if (index >= taskList.getLength() || index < 0) {
                            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
                        }
                        this.taskList.deleteTask(index);
                        this.storage.writeDelete(index);
                        this.ui.printMessage("Noted. I've removed this task:");
                        this.ui.printMessage("  " + taskList.getTask(index));
                        this.ui.printMessage(String.format("Now you have %d tasks in the list", taskList.getLength()));
                        break;
                    }
                    case TODO: {
                        String description = input.getDescription();
                        Task task = new ToDos(description);
                        this.taskList.addTask(task);
                        this.storage.writeTodo(description);
                        this.ui.printMessage("Got it. I've added this task:");
                        this.ui.printMessage("  " + task);
                        this.ui.printMessage(String.format("Now you have %d tasks in the list.", taskList.getLength()));
                        break;
                    }
                    case DEADLINE: {
                        LocalDate end = input.getEnd();
                        String description = input.getDescription();
                        Task task = new Deadlines(description, end);
                        taskList.addTask(task);
                        this.storage.writeDeadline(description, end);
                        this.ui.printMessage("Got it. I've added this task:");
                        this.ui.printMessage("  " + task);
                        this.ui.printMessage(String.format("Now you have %d tasks in the list.", taskList.getLength()));
                        break;
                    }
                    case EVENT: {
                        LocalDate end = input.getEnd();
                        LocalDate start = input.getStart();
                        String description = input.getDescription();
                        Task task = new Events(description, start, end);
                        this.taskList.addTask(task);
                        this.storage.writeEvent(description, start, end);
                        this.ui.printMessage("Got it. I've added this task:");
                        this.ui.printMessage("  " + task);
                        this.ui.printMessage(String.format("Now you have %d tasks in the list.", taskList.getLength()));
                        break;
                    }
                    default:
                }

            } catch (MissingElementException e) {
                this.ui.printMessage(e.getMessage());
            } catch (UnknownCommandException e) {
                this.ui.printMessage("Sorry, I don't know what that means QAQ");
            } catch (InvalidIndexException | NumberFormatException e) {
                this.ui.printMessage("Oops, need a valid number as task index Ծ‸Ծ");
            } finally {
                this.ui.showDivisionLine();
            }

        }
    }

    public static void main(String[] args) {
        Path target = Paths.get(System.getProperty("user.dir"))
                .resolve(Paths.get("data", "Sigma.txt"));
        new Sigma(target).run();
    }
}
