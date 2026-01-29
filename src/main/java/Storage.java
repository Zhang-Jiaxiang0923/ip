import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path target;
    private List<String> lines;
    public Storage(Path target) {
        this.target = target;
    }

    public ArrayList<Task> load() {
        try {
            if (Files.notExists(target)) {
                Files.createDirectories(target.getParent());
                Files.createFile(target);
                return new ArrayList<>();
            } else {
                try {
                    this.lines = Files.readAllLines(this.target);
                    return ReadFromDisk();
                } catch (CorruptedFileException e) {
                    Files.deleteIfExists(target);
                    Files.createDirectories(target.getParent());
                    Files.createFile(target);
                    return new ArrayList<>();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("File does not exist and cannot create file: " + target, e);
        }
    }

    public ArrayList<Task> ReadFromDisk() throws CorruptedFileException{
        ArrayList<Task> todo = new ArrayList<>();
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
        return todo;
    }
}
