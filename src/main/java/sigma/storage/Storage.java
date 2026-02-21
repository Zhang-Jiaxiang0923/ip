package sigma.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sigma.exception.CorruptedFileException;
import sigma.exception.InvalidIndexException;
import sigma.task.Deadlines;
import sigma.task.Events;
import sigma.task.Task;
import sigma.task.TaskList;
import sigma.task.ToDos;

/**
 * Class that deals with loading and writing into specific file.
 */
public class Storage {
    private final Path target;
    private List<String> lines;
    private final Path archivePath;
    private List<String> archiveLines;

    /**
     * Constructs the storage object.
     * @param target The file path that the storage object will load and write to.
     */
    public Storage(Path target, Path archivePath) {
        this.lines = new ArrayList<>();
        this.target = target;
        this.archivePath = archivePath;
        this.archiveLines = new ArrayList<>();
    }

    /**
     * Loads tasks form local memory.
     * If file not exists, trys to create the file.
     * If file corrupted, trys to delete and new a file.
     *
     * @param taskList The tasklist where the local memory will be loaded to.
     */
    public void load(TaskList taskList) {
        try {
            if (Files.notExists(target)) {
                Files.createDirectories(target.getParent());
                Files.createFile(target);
            }

            if (Files.notExists(archivePath)) {
                Files.createDirectories(archivePath.getParent());
                Files.createFile(archivePath);
            }
            try {
                this.lines = Files.readAllLines(this.target);
                this.archiveLines = Files.readAllLines(this.archivePath);
                readFromDisk(taskList);
            } catch (CorruptedFileException e) {
                Files.deleteIfExists(target);
                Files.createDirectories(target.getParent());
                Files.createFile(target);
            }

        } catch (IOException e) {
            throw new RuntimeException("File does not exist and cannot create file: " + target, e);
        }
    }

    /**
     * Reads the content in disk and write into the tasklist.
     *
     * @param taskList The tasklist where tasks read from disk will write to.
     * @throws CorruptedFileException If contents in the file that it trys to read from is corrupted.
     */
    private void readFromDisk(TaskList taskList) throws CorruptedFileException {
        for (String line : this.lines) {
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
                taskList.addTask(task);
                break;
            }
            case "D": {
                Task task = new Deadlines(p[2].trim(), LocalDate.parse(p[4].trim()));
                if (p[1].equals("1")) {
                    task.markAsDone();
                }
                taskList.addTask(task);
                break;
            }
            case "E": {
                Task task = new Events(p[2].trim(), LocalDate.parse(p[3].trim()), LocalDate.parse(p[4].trim()));
                if (p[1].equals("1")) {
                    task.markAsDone();
                }
                taskList.addTask(task);
                break;
            }
            default:
            }
        }
    }

    /**
     * Marks specific task in disk as done.
     *
     * @param index The index of the task that will be marked.
     */
    public void writeMark(int index) {
        try {
            if (index < 0 || index >= lines.size()) {
                throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
            }
            String content = lines.get(index);
            String[] p = content.split("\\|", -1);
            p[1] = " 1 ";
            lines.set(index, String.join("|", p));
            Files.write(target, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write in the file.");
        }
    }

    /**
     * Unmarks specific task in disk as hasn't done.
     *
     * @param index The index of the task that will be unmarked.
     */
    public void writeUnmark(int index) {
        try {
            if (index < 0 || index >= lines.size()) {
                throw new InvalidIndexException("Oops, the index of task is invalid •﹏•");
            }
            String content = lines.get(index);
            String[] p = content.split("\\|", -1);
            p[1] = " 0 ";
            lines.set(index, String.join("|", p));
            Files.write(target, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write in the file.");
        }
    }

    /**
     * Deletes specific task in disk.
     *
     * @param index The index of the task that will be deleted.
     */
    public void writeDelete(int index) {
        try {
            if (index < 0 || index >= lines.size()) {
                throw new InvalidIndexException("Invalid task index: " + index);
            }
            lines.remove(index);
            Files.write(target, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write in the file.");
        }
    }

    /**
     * Writes a Todo task into the disk.
     *
     * @param description The description of the ToDo task.
     */
    public void writeTodo(String description) {
        writeTask('T', description);
    }

    /**
     * Writes a Deadline into the disk.
     *
     * @param description The description of the Deadline.
     * @param end         The end time of the Deadline.
     */
    public void writeDeadline(String description, LocalDate end) {
        writeTask('D', description, end);
    }

    /**
     * Writes an Event task into the disk.
     *
     * @param description The description of the Deadline task.
     * @param start       The start time of the Event.
     * @param end         The end time of the Event.
     */
    public void writeEvent(String description, LocalDate start, LocalDate end) {
        writeTask('E', description, start, end);
    }

    public void writeTask(char type, String description, LocalDate... dates) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(type).append(" | 0 | ").append(description).append(" | ");

            if (dates.length == 0) {
                sb.append("- | -");
            } else if (dates.length == 1) {
                sb.append("- | ").append(dates[0]);
            } else {
                sb.append(dates[0]).append(" | ").append(dates[1]);
            }

            lines.add(sb.toString());
            Files.write(target, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write in the file.", e);
        }
    }

    public void archiveTask(int index) {
        try {
            if (index < 0 || index >= lines.size()) {
                throw new InvalidIndexException("Invalid task index: " + index);
            }
            String line = lines.get(index);
            archiveLines.add(line);
            Files.write(archivePath, archiveLines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to archive into the file.");
        }
    }

}
