package marvin.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void testToString() {
        Deadline deadline = new Deadline("submit report", "2024-12-01");
        assertEquals("[D][ ] submit report (by: Dec 1 2024)", deadline.toString());

        Deadline deadlineMarked = new Deadline("submit report", "2024-12-01");
        deadlineMarked.mark();
        assertEquals("[D][X] submit report (by: Dec 1 2024)", deadlineMarked.toString());
    }

    @Test
    public void testToStorageString() {
        Deadline deadline = new Deadline("submit report", "2024-12-01");
        assertEquals("D | 0 | submit report | 2024-12-01", deadline.toStorageString());

        Deadline deadlineMarked = new Deadline("submit report", "2024-12-01");
        deadlineMarked.mark();
        assertEquals("D | 1 | submit report | 2024-12-01", deadlineMarked.toStorageString());
    }
}
