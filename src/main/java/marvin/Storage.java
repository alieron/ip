package marvin;

import marvin.task.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path filePath = Path.of("data/tasks.txt");

    //    public marvin.Storage(String relativePath) {
    //        this.filePath = Paths.get(relativePath);
    //    }

    public List<Task> load() throws MarvinException {
        List<Task> tasks = new ArrayList<>();
        try {
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                return tasks;
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
        return tasks;
    }

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

    // convenience overload so callers can pass marvin.TaskList directly
    public void save(TaskList taskList) throws MarvinException {
        save(taskList.getTasks());
    }
}
