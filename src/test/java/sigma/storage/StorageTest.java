package sigma.storage;

import java.nio.file.Path;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.io.TempDir;
import sigma.task.ToDos;
import sigma.task.TaskList;
import sigma.task.Deadlines;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    void saveAndLoadTest() throws Exception {
        Path file = tempDir.resolve("sigma.txt");
        Storage storage = new Storage(file);

        TaskList original = new TaskList();
        original.addTask(new ToDos("read books"));
        storage.writeTodo("read books");
        original.addTask(new Deadlines("do homework", LocalDate.parse("2026-01-30")));
        storage.writeDeadline("do homework", LocalDate.parse("2026-01-30"));

        TaskList loaded = new TaskList();
        storage.load(loaded);


        assertEquals(original.getLength(), loaded.getLength());
        assertEquals(original.getTask(0).toString(), loaded.getTask(0).toString());
        assertEquals(original.getTask(1).toString(), loaded.getTask(1).toString());
    }

    @Test
    void load_fileMissing_returnsEmptyList() throws Exception {
        Path file = tempDir.resolve("missing.txt");
        Storage storage = new Storage(file);

        TaskList loaded = new TaskList();
        storage.load(loaded);

        assertEquals(0, loaded.getLength());
    }


}
