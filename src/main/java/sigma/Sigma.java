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
    public Sigma(Path target, Path archivePath) {
        ui = new Ui();
        this.storage = new Storage(target, archivePath);
        this.taskList = new TaskList();
        this.storage.load(this.taskList);
    }

    /**
     * Runs the whole Sigma application.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
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
                    int index = input.getIndex();
                    handleMark(input);
                    this.ui.printMarkMessage(taskList.getTask(index));
                    break;
                }
                case UNMARK: {
                    int index = input.getIndex();
                    handleUnmark(input);
                    this.ui.printUnmarkMessage(taskList.getTask(index));
                    break;
                }
                case DELETE: {
                    int index = input.getIndex();
                    int len = taskList.getLength();
                    this.ui.printDeleteMessage(taskList.getTask(index), len - 1);
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
                case ARCHIVE: {
                    handleArchive(input);
                    this.ui.printArchiveMessage(taskList.getTask(input.getIndex()));
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
            } catch (RuntimeException e) {
                ui.printMessage("Oops, something went wrong: " + e.getMessage());
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
        Path archivePath = Paths.get(System.getProperty("user.dir"))
                .resolve(Paths.get("data", "archive.txt"));
        new Sigma(target, archivePath).run();
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
                handleMark(parsedInput);
                return this.ui.getMarkMessage(taskList.getTask(index));
            }
            case UNMARK: {
                int index = parsedInput.getIndex();
                handleUnmark(parsedInput);
                return this.ui.getUnmarkMessage(taskList.getTask(index));

            }
            case DELETE: {
                int index = parsedInput.getIndex();
                int len = taskList.getLength();
                String message = this.ui.getDeleteMessage(taskList.getTask(index), len);
                handleDelete(parsedInput);
                return message;

            }
            case ARCHIVE: {
                handleArchive(parsedInput);
                return this.ui.getArchiveMessage(taskList.getTask(parsedInput.getIndex()));
            }
            case LOOK: {
                ArrayList<Task> finding = this.taskList.lookUp(parsedInput.getDescription());
                return this.ui.getFinding(finding);
            }
            case TODO: {
                handleTodo(parsedInput);
                return this.ui.getAddMessage(taskList);
            }
            case DEADLINE: {
                handleDeadline(parsedInput);
                return this.ui.getAddMessage(taskList);
            }
            case EVENT: {
                handleEvent(parsedInput);
                return this.ui.getAddMessage(taskList);
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

    public void handleArchive(ParsedInput input) {
        int index = input.getIndex();
        if (index >= taskList.getLength() || index < 0) {
            throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
        }
        this.storage.archiveTask(input.getIndex());
    }

    public String getWelcomeMessage() {
        return this.ui.getWelcomeMessage(); // 如果你 Ui 里叫别的名字，就改成对应的
    }
}
