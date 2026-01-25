package marvin;

public interface Storable {
    /**
     * Convert this instance to a single-line storage representation.
     * Example: "T | 1 | read book"
     */
    String toStorageString();
}