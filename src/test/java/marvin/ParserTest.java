package marvin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    public void parseCommand_validCommand_success() {
        try {
            assertEquals("ListCommand", Parser.parseCommand("list").getClass().getSimpleName());
            assertEquals("AddTaskCommand", Parser.parseCommand("todo read book").getClass().getSimpleName());
            assertEquals("AddTaskCommand", Parser.parseCommand("deadline submit report /by 2024-12-01").getClass().getSimpleName());
            assertEquals("AddTaskCommand", Parser.parseCommand("event team meeting /from 2024-11-15 /to 2024-11-16").getClass().getSimpleName());
            assertEquals("MarkCommand", Parser.parseCommand("mark 1").getClass().getSimpleName());
            assertEquals("UnmarkCommand", Parser.parseCommand("unmark 1").getClass().getSimpleName());
            assertEquals("DeleteCommand", Parser.parseCommand("delete 1").getClass().getSimpleName());
            assertEquals("FindCommand", Parser.parseCommand("find book").getClass().getSimpleName());
            assertEquals("ExitCommand", Parser.parseCommand("bye").getClass().getSimpleName());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseCommand_invalidCommand_throwsException() {
        try {
            Parser.parseCommand("");
            fail();
        } catch (MarvinException e) {
            assertEquals("I don’t know what you want me to do.", e.getMessage());
        }

        try {
            Parser.parseCommand("unknown command");
            fail();
        } catch (MarvinException e) {
            assertEquals("I don’t know what you want me to do.", e.getMessage());
        }

        try {
            Parser.parseCommand("todo");
            fail();
        } catch (MarvinException e) {
            assertEquals("A todo without a description is rather pointless.", e.getMessage());
        }

        try {
            Parser.parseCommand("deadline submit report");
            fail();
        } catch (MarvinException e) {
            assertEquals("Deadlines tend to require a deadline. Try using /by.", e.getMessage());
        }

        try {
            Parser.parseCommand("deadline /by 2024-12-01");
            fail();
        } catch (MarvinException e) {
            assertEquals("A deadline for nothing in particular is deeply confusing.", e.getMessage());
        }

        try {
            Parser.parseCommand("event team meeting");
            fail();
        } catch (MarvinException e) {
            assertEquals("An event should probably start at some point. Try /from.", e.getMessage());
        }

        try {
            Parser.parseCommand("event team meeting /from 2024-11-15");
            fail();
        } catch (MarvinException e) {
            assertEquals("Events usually end. Try specifying /to.", e.getMessage());
        }

        try {
            Parser.parseCommand("event /from 2024-11-15 /to 2024-11-16");
            fail();
        } catch (MarvinException e) {
            assertEquals("An event with missing details is... incomplete.", e.getMessage());
        }
    }

    @Test
    public void parseDate_validFormat_returnsValidLocalDate() {
        try {
            assertEquals("2024-12-01", Parser.parseDate("2024-12-01").toString());
            assertEquals("2024-02-29", Parser.parseDate("2024-02-29").toString()); // Leap year
            assertEquals("2024-12-01", Parser.parseDate("1/12/2024").toString());
            assertEquals("2024-02-29", Parser.parseDate("29/2/2024").toString()); // Leap year
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseDate_invalidFormat_throwsException() {
        try {
            Parser.parseDate("2024/12/01");
            fail();
        } catch (Exception e) {
            assertEquals("Unrecognized date/time format: 2024/12/01", e.getMessage());
        }

        // TODO: Enable this tests after improving date validation in Parser.parseDate
//        try {
//            Parser.parseDate("31/02/2024"); // Invalid date
//            fail();
//        } catch (Exception e) {
//            assertEquals("Unrecognized date/time format: 31/02/2024", e.getMessage());
//        }

        try {
            Parser.parseDate("invalid-date");
            fail();
        } catch (Exception e) {
            assertEquals("Unrecognized date/time format: invalid-date", e.getMessage());
        }
    }

    @Test
    public void parseDate_nullInput_throwsException() {
        try {
            Parser.parseDate(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("token must not be null", e.getMessage());
        }
    }
}
