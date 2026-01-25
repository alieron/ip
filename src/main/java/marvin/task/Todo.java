package marvin.task;

public class Todo extends Task {
    public Todo(String desc) {
        super(desc);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toStorageString() {
        return "T | " + super.toStorageString();
    }

    public static Todo fromStorageParts(String[] parts, boolean isComplete) {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid storage parts for marvin.task.Todo: " + String.join(" | ", parts));
        }
        Todo todo = new Todo(parts[2]);
        if (isComplete) {
            todo.mark();
        }
        return todo;
    }
}