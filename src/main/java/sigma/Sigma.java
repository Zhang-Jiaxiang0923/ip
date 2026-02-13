package sigma;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import sigma.command.CommandType;
import sigma.exception.InvalidIndexException;
import sigma.exception.MissingElementException;
import sigma.exception.UnknownCommandException;
import sigma.parser.ParsedInput;
import sigma.parser.Parser;
import sigma.storage.Storage;
import sigma.task.Deadlines;
import sigma.task.Events;
import sigma.task.Task;
import sigma.task.TaskList;
import sigma.task.ToDos;
import sigma.ui.Ui;


/**
 * Runs the Sigma task manager application. Coordinates with Ui,
 * storage, and parser etc. for Sigma.
 */
public class Sigma {
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Constructs the Sigma class by the target path
     * @param target The path where the Sigma where store the data in.
     */
    public Sigma(Path target) {
        ui = new Ui();
        this.storage = new Storage(target);
        this.taskList = new TaskList();
    }

    /**
     * Runs the whole Sigma application.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        this.storage.load(this.taskList);
        this.ui.showWelcome();
        Loop:
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            this.ui.showDivisionLine();
            try {
                if (line.isEmpty()) {
                    throw new MissingElementException("Oops, the input is empty.");
                }
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
                    handleMark(input);
                    this.ui.printMarkMessage(taskList.getTask(input.getIndex()));
                    break;
                }
                case UNMARK: {
                    handleUnmark(input);
                    this.ui.printUnmarkMessage(taskList.getTask(input.getIndex()));
                    break;
                }
                case DELETE: {
                    this.ui.printDeleteMessage(taskList.getTask(input.getIndex()), taskList.getLength()-1);
                    handleDelete(input);
                    break;
                }
                case LOOK: {
                    ArrayList<Task> finding = this.taskList.lookUp(input.getDescription());
                    this.ui.printFinding(finding);
                    break;
                }
                case TODO: {
                    handleTodo(input);
                    this.ui.printAddMessage(taskList);
                    break;
                }
                case DEADLINE: {
                    handleDeadline(input);
                    this.ui.printAddMessage(taskList);
                    break;
                }
                case EVENT: {
                    handleEvent(input);
                    this.ui.printAddMessage(taskList);
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

    /**
     * Starts the Sigma application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        Path target = Paths.get(System.getProperty("user.dir"))
                .resolve(Paths.get("data", "Sigma.txt"));
        new Sigma(target).run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        assert !input.isEmpty() : "Input of GUI is empty";
        try {
            ParsedInput parsedInput = Parser.parseInput(input);
            CommandType command = parsedInput.getCommand();
            switch (command) {
            case BYE:
                return this.ui.getGoodbye();
            case LIST:
                return this.ui.getTasks(this.taskList);
            case MARK: {
                int index = parsedInput.getIndex();
                if (index >= taskList.getLength() || index < 0) {
                    throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
                }
                taskList.markTask(index);
                this.storage.writeMark(index);
                return "Nice! I've marked this task as done:" + "\n"
                        + "  " + taskList.getTask(index);
            }
            case UNMARK: {
                int index = parsedInput.getIndex();
                if (index >= taskList.getLength() || index < 0) {
                    throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
                }
                taskList.unmarkTask(index);
                this.storage.writeUnmark(index);
                return "Nice! I've marked this task as hasn't done:" + "\n"
                        + "  " + taskList.getTask(index);
            }
            case DELETE: {
                int index = parsedInput.getIndex();
                if (index >= taskList.getLength() || index < 0) {
                    throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
                }
                this.taskList.deleteTask(index);
                this.storage.writeDelete(index);
                return "Noted. I've removed this task:\n"
                        + "  " + taskList.getTask(index)
                        + String.format("Now you have %d tasks in the list", taskList.getLength());

            }
            case LOOK: {
                ArrayList<Task> finding = this.taskList.lookUp(parsedInput.getDescription());
                return this.ui.getFinding(finding);
            }
            case TODO: {
                String description = parsedInput.getDescription();
                Task task = new ToDos(description);
                taskList.addTask(task);
                this.storage.writeTodo(description);
                return "Got it. I've added this task:\n"
                        + "  " + task + "\n"
                        + String.format("Now you have %d tasks in the list.", taskList.getLength());
            }
            case DEADLINE: {
                LocalDate end = parsedInput.getEnd();
                String description = parsedInput.getDescription();
                Task task = new Deadlines(description, end);
                taskList.addTask(task);
                this.storage.writeDeadline(description, end);
                return "Got it. I've added this task:\n"
                        + "  " + task + "\n"
                        + String.format("Now you have %d tasks in the list.", taskList.getLength());
            }
            case EVENT: {
                LocalDate end = parsedInput.getEnd();
                LocalDate start = parsedInput.getStart();
                String description = parsedInput.getDescription();
                Task task = new Events(description, start, end);
                this.taskList.addTask(task);
                this.storage.writeEvent(description, start, end);
                return "Got it. I've added this task:\n"
                        + "  " + task + "\n"
                        + String.format("Now you have %d tasks in the list.", taskList.getLength());
            }
            default:
                return "";
            }

        } catch (MissingElementException e) {
            return e.getMessage();
        } catch (UnknownCommandException e) {
            return "Sorry, I don't know what that means QAQ";
        } catch (InvalidIndexException | NumberFormatException e) {
            return "Oops, need a valid number as task index Ծ‸Ծ";
        } finally {

        }
    }


    public void handleMark(ParsedInput input) {
        int index = input.getIndex();
        if (index >= taskList.getLength() || index < 0) {
            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
        }
        taskList.markTask(index);
        this.storage.writeMark(index);
    }

    public void handleUnmark(ParsedInput input) {
        int index = input.getIndex();
        if (index >= taskList.getLength() || index < 0) {
            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
        }
        taskList.unmarkTask(index);
        this.storage.writeUnmark(index);
    }

    public void handleDelete(ParsedInput input) {
        int index = input.getIndex();
        if (index >= taskList.getLength() || index < 0) {
            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
        }
        this.taskList.deleteTask(index);
        this.storage.writeDelete(index);
    }

    public void handleTodo(ParsedInput input) {
        String description = input.getDescription();
        Task task = new ToDos(description);
        this.taskList.addTask(task);
        this.storage.writeTodo(description);
    }

    public void handleDeadline(ParsedInput input) {
        LocalDate end = input.getEnd();
        String description = input.getDescription();
        Task task = new Deadlines(description, end);
        taskList.addTask(task);
        this.storage.writeDeadline(description, end);
    }

    public void handleEvent(ParsedInput input) {
        LocalDate end = input.getEnd();
        LocalDate start = input.getStart();
        String description = input.getDescription();
        Task task = new Events(description, start, end);
        this.taskList.addTask(task);
        this.storage.writeEvent(description, start, end);
    }
}
