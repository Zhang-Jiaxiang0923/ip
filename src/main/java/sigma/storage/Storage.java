package sigma.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sigma.exception.CorruptedFileException;
import sigma.task.ToDos;
import sigma.task.TaskList;
import sigma.task.Task;
import sigma.task.Events;
import sigma.task.Deadlines;

public class Storage {
    private final Path target;
    private List<String> lines;
    public Storage(Path target) {
        this.lines = new ArrayList<>();
        this.target = target;
    }

    public void load(TaskList taskList) {
        try {
            if (Files.notExists(target)) {
                Files.createDirectories(target.getParent());
                Files.createFile(target);
            } else {
                try {
                    this.lines = Files.readAllLines(this.target);
                    ReadFromDisk(taskList);
                } catch (CorruptedFileException e) {
                    Files.deleteIfExists(target);
                    Files.createDirectories(target.getParent());
                    Files.createFile(target);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("File does not exist and cannot create file: " + target, e);
        }
    }

    private void ReadFromDisk(TaskList taskList) throws CorruptedFileException{
        for (String line: this.lines) {
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
            }
        }
    }

    public void writeMark(int index) {
        try {
            String content = lines.get(index);
            String[] p = content.split("\\|", -1);
            p[1] = " 1 ";
            lines.set(index, String.join("|", p));
            Files.write(target, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write in the file.");
        }
    }

    public void writeUnmark(int index) {
        try {
            String content = lines.get(index);
            String[] p = content.split("\\|", -1);
            p[1] = " 0 ";
            lines.set(index, String.join("|", p));
            Files.write(target, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write in the file.");
        }
    }

    public void writeDelete(int index) {
        try {
            lines.remove(index);
            Files.write(target, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write in the file.");
        }
    }

    public void writeTodo(String description) {
        try {
            lines.add("T | 0 | " + description + " | - | -");
            Files.write(target, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write in the file.");
        }
    }

    public void writeDeadline(String description, LocalDate end) {
        try {
            lines.add("D | 0 | " + description + " | - | " + end);
            Files.write(target, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write in the file.");
        }
    }

    public void writeEvent(String description, LocalDate start, LocalDate end) {
        try {
            lines.add("E | 0 | " + description + " | " + start + " | " + end);
            Files.write(target, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write in the file.");
        }
    }

}
