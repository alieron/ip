package marvin;

/**
 * Interface for objects that can be stored on disk
 */
public interface Storable {
    /**
     * Convert this instance to a single-line storage representation.
     * Example: "T | 1 | read book"
     *
     * @return The string representing the object
     */
    String toStorageString();
}
