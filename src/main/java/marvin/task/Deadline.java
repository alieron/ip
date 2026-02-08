package marvin.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import marvin.Parser;

/**
 * The Deadline task.
 */
public class Deadline extends Task {
    private final LocalDate by;

    /**
     * Instantiates a new Deadline.
     *
     * @param desc The description
     * @param by   The date of the deadline
     */
    public Deadline(String desc, String by) {
        super(desc);
        this.by = Parser.parseDate(by);
    }

    /**
     * From storage parts deadline.
     *
     * @param parts      The saved task data
     * @param isComplete Whether the task has already been completed
     * @return the deadline
     */
    public static Deadline fromStorageParts(String[] parts, boolean isComplete) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid storage parts for marvin.task.Deadline: "
                    + String.join(" | ", parts));
        }
        Deadline deadline = new Deadline(parts[2], parts[3]);
        if (isComplete) {
            deadline.mark();
        }
        return deadline;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }

    @Override
    public String toStorageString() {
        return "D | " + super.toStorageString() + " | " + by;
    }
}
