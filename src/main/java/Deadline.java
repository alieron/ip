public class Deadline extends Task {
    private String by;

    public Deadline(String desc, String by) {
        super(desc);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String toStorageString() {
        return "D | " + super.toStorageString() + " | " + by;
    }

    public static Deadline fromStorageParts(String[] parts, boolean isComplete) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid storage parts for Deadline: " + String.join(" | ", parts));
        }
        Deadline deadline = new Deadline(parts[2], parts[3]);
        if (isComplete) {
            deadline.mark();
        }
        return deadline;
    }
}
