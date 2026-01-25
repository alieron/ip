package marvin;

import marvin.task.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides an abstraction for saving and loading the TaskList.
 */
public class Storage {
    private final Path filePath;

    /**
     * Instantiates a new Storage.
     *
     * @param relativePath The relative path to the storage file
     */
    public Storage(String relativePath) {
        this.filePath = Paths.get(relativePath);
    }

    /**
     * Load task list from storage file.
     *
     * @return The task list
     * @throws MarvinException If there are corrupted lines in the storage file or if the program fails to read the file
     */
    public TaskList load() throws MarvinException {
        List<Task> tasks = new ArrayList<>();
        try {
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                return new TaskList();
            }
            List<String> lines = Files.readAllLines(filePath);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    Task t = Task.fromStorageString(line);
                    tasks.add(t);
                } catch (Exception e) {
                    throw new MarvinException("Warning: skipping corrupted storage line " + (i + 1) + ": " + line);
                }
            }
        } catch (IOException e) {
            throw new MarvinException("Failed to load storage: " + e.getMessage());
        }
        return new TaskList(tasks);
    }

    /**
     * Save list of tasks to storage file.
     *
     * @param tasks The list of tasks
     * @throws MarvinException If the program fails to write to the file
     */
    public void save(List<Task> tasks) throws MarvinException {
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.toStorageString());
        }
        try {
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new MarvinException("Failed to save tasks: " + e.getMessage());
        }
    }

    /**
     * Convenience overload.
     * Save TaskList to storage file.
     *
     * @param taskList The TaskList
     * @throws MarvinException If the program fails to write to the file
     */
// convenience overload so callers can pass marvin.TaskList directly
    public void save(TaskList taskList) throws MarvinException {
        save(taskList.getTasks());
    }
}
