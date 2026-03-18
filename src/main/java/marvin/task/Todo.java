package marvin.task;

import marvin.MarvinException;

/**
 * The Todo task.
 */
public class Todo extends Task {
    public Todo(String desc) {
        super(desc);
    }

    /**
     * Creates a new Todo from command arguments.
     *
     * @param argString The main argument and description of the deadline
     * @throws MarvinException If the command is invalid
     */
    public static Todo fromCommand(String argString) throws MarvinException {
        if (argString.isBlank()) {
            throw new MarvinException("A todo without a description is rather pointless.");
        }

        return new Todo(argString);
    }

    /**
     * From storage parts deadline.
     *
     * @param parts      The saved task data
     * @param isComplete Whether the task has already been completed
     * @return the deadline
     */
    public static Todo fromStorageParts(String[] parts, boolean isComplete) {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid storage parts for marvin.task.Todo: "
                    + String.join(" | ", parts));
        }
        Todo todo = new Todo(parts[2]);
        if (isComplete) {
            todo.mark();
        }
        return todo;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toStorageString() {
        return "T | " + super.toStorageString();
    }
}
