package marvin.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void testToString() {
        Event event = new Event("team meeting", "2024-11-15", "2024-11-16");
        assertEquals("[E][ ] team meeting (from: Nov 15 2024 to: Nov 16 2024)", event.toString());

        Event eventMarked = new Event("team meeting", "2024-11-15", "2024-11-16");
        eventMarked.mark();
        assertEquals("[E][X] team meeting (from: Nov 15 2024 to: Nov 16 2024)", eventMarked.toString());
    }

    @Test
    public void testToStorageString() {
        Event event = new Event("team meeting", "2024-11-15", "2024-11-16");
        assertEquals("E | 0 | team meeting | 2024-11-15 | 2024-11-16", event.toStorageString());

        Event eventMarked = new Event("team meeting", "2024-11-15", "2024-11-16");
        eventMarked.mark();
        assertEquals("E | 1 | team meeting | 2024-11-15 | 2024-11-16", eventMarked.toStorageString());
    }
}
