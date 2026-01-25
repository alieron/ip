import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDate from, to;

    public Event(String desc, String from, String to) {
        super(desc);
        this.from = Parser.parseDate(from);
        this.to = Parser.parseDate(to);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("MMM d yyyy");
        return "[E]" + super.toString() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
    }

    @Override
    public String toStorageString() {
        return "E | " + super.toStorageString() + " | " + from + " | " + to;
    }

    public static Event fromStorageParts(String[] parts, boolean isComplete) {
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid storage parts for Event: " + String.join(" | ", parts));
        }
        Event event = new Event(parts[2], parts[3], parts[4]);
        if (isComplete) {
            event.mark();
        }
        return event;
    }
}