package marvin.task;

import marvin.Storable;

public abstract class Task implements Storable {
    protected boolean isComplete = false; // task is incomplete by default
    protected String desc;

    protected Task(String desc) {
        this.desc = desc;
    }

    /**
     * Factory method to parse a storage line and return a marvin.task.Task.
     * Format expected: TYPE | DONE(0/1) | description [| extra...]
     */
    public static Task fromStorageString(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid storage line: " + line);
        }
        String type = parts[0];
        String doneToken = parts[1];
        boolean done = "1".equals(doneToken);

        return switch (type) {
        case "T" -> Todo.fromStorageParts(parts, done);
        case "D" -> Deadline.fromStorageParts(parts, done);
        case "E" -> Event.fromStorageParts(parts, done);
        default -> throw new IllegalArgumentException("Unknown task type: " + type);
        };
    }

    @Override
    public String toString() {
        return (isComplete ? "[X] " : "[ ] ") + desc;
    }

    public void mark() {
        this.isComplete = true;
    }

    public void unmark() {
        this.isComplete = false;
    }

    @Override
    public String toStorageString() {
        return (isComplete ? "1 | " : "0 | ") + desc;
    }

    public boolean checkDescContains(String snippet) {
        return desc.contains(snippet);
    }
}
