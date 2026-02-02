package marvin.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoTest {
    @Test
    public void testToString() {
        Todo todo = new Todo("read book");
        assertEquals("[T][ ] read book", todo.toString());

        Todo todoMarked = new Todo("read book");
        todoMarked.mark();
        assertEquals("[T][X] read book", todoMarked.toString());
    }

    @Test
    public void testToStorageString() {
        Todo todo = new Todo("read book");
        assertEquals("T | 0 | read book", todo.toStorageString());

        Todo todoMarked = new Todo("read book");
        todoMarked.mark();
        assertEquals("T | 1 | read book", todoMarked.toStorageString());
    }
}
