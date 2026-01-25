package marvin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StorageTest {
    @Test
    public void loadTasks_emptyStorage_returnsEmptyTaskList() {
        try {
            Storage storage = new Storage("data/empty.txt");
            TaskList taskList = storage.load();
            assertEquals(0, taskList.numTasks());
        } catch (MarvinException e) {
            fail();
        }
    }

    @Test
    public void loadTasks_missingFile_returnsEmptyTaskList() {
        try {
            Storage storage = new Storage("data/invalid_format.txt");
            TaskList taskList = storage.load();
            assertEquals(0, taskList.numTasks());
        } catch (MarvinException e) {
            fail();
        }
    }
}
