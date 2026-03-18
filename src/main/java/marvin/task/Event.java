package marvin.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import marvin.MarvinException;
import marvin.Parser;

/**
 * The Event task.
 */
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    /**
     * Instantiates a new Deadline.
     *
     * @param desc The description
     * @param from The start date
     * @param to   The end date
     */
    public Event(String desc, String from, String to) {
        super(desc);
        this.from = Parser.parseDate(from);
        this.to = Parser.parseDate(to);
        // Ensure that `from` is earlier than `to`
        if (this.from.isAfter(this.to)) {
            LocalDate temp = this.from;
            this.from = this.to;
            this.to = temp;
        }
        assert this.from.isBefore(this.to) : "`from` should be before `to`";
    }

    /**
     * Creates a new Event from command arguments.
     *
     * @param argString The main argument and description of the deadline
     * @param otherArgs The other args
     * @throws MarvinException If the command is invalid
     */
    public static Event fromCommand(String argString, Map<String, String> otherArgs) throws MarvinException {
        if (argString.isEmpty()) {
            throw new MarvinException("An event with missing details is... incomplete.");
        }

        String from = Parser.getFlag(otherArgs, "-f", "--from");
        if (from == null) {
            throw new MarvinException("An event should probably start at some point. Try using -f or --from.");
        }

        String to = Parser.getFlag(otherArgs, "-t", "--to");
        if (to == null) {
            throw new MarvinException("Events usually end. Try specifying -t or --to.");
        }

        return new Event(argString, from, to);
    }

    /**
     * From storage parts deadline.
     *
     * @param parts      The saved task data
     * @param isComplete Whether the task has already been completed
     * @return the deadline
     */
    public static Event fromStorageParts(String[] parts, boolean isComplete) {
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid storage parts for marvin.task.Event: "
                    + String.join(" | ", parts));
        }
        Event event = new Event(parts[2], parts[3], parts[4]);
        if (isComplete) {
            event.mark();
        }
        return event;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        return "[E]" + super.toString() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
    }

    @Override
    public String toStorageString() {
        return "E | " + super.toStorageString() + " | " + from + " | " + to;
    }
}
