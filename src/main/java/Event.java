public class Event extends Task {
    private String from, to;

    public Event(String desc, String from, String to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
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