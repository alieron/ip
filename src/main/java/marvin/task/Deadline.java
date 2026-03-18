package marvin.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import marvin.MarvinException;
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
     * Creates a new Deadline from command arguments.
     *
     * @param argString the main argument and description of the deadline
     * @param otherArgs the other args
     * @throws MarvinException if the command is invalid
     */
    public static Deadline fromCommand(String argString, Map<String, String> otherArgs) throws MarvinException {
        if (argString.isBlank()) {
            throw new MarvinException("A deadline for nothing in particular is deeply confusing.");
        }

        String by = Parser.getFlag(otherArgs, "-b", "--by");

        if (by == null) {
            throw new MarvinException("Deadlines tend to require a deadline. Try using -b or --by.");
        }

        return new Deadline(argString, by);
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
