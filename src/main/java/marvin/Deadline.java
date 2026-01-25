package marvin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDate by;

    public Deadline(String desc, String by) {
        super(desc);
        this.by = Parser.parseDate(by);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("MMM d yyyy");
        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }

    @Override
    public String toStorageString() {
        return "D | " + super.toStorageString() + " | " + by;
    }

    public static Deadline fromStorageParts(String[] parts, boolean isComplete) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid storage parts for marvin.Deadline: " + String.join(" | ", parts));
        }
        Deadline deadline = new Deadline(parts[2], parts[3]);
        if (isComplete) {
            deadline.mark();
        }
        return deadline;
    }
}
