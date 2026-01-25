public abstract class Task implements Storable {
    protected boolean isComplete = false; // task is incomplete by default
    protected String desc;

    protected Task(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return (isComplete ? "[X] " : "[ ] ") + desc;
    }

    public void mark() {
        this.isComplete = true;
    }

    public void unmark() {
        this.isComplete = false;
    }

    @Override
    public String toStorageString() {
        return isComplete ? "1 | " : "0 | " + desc;
    }
}
