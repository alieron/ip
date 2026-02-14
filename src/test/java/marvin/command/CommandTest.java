package marvin.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import marvin.MarvinException;

public class CommandTest {
    @Test
    public void parseCommand_validCommand_success() {
        try {
            assertEquals("ListCommand", Command.parseCommand("list").getClass().getSimpleName());
            assertEquals("AddTaskCommand",
                    Command.parseCommand("todo read book").getClass().getSimpleName());
            assertEquals("AddTaskCommand",
                    Command.parseCommand("deadline submit report --by 2024-12-01").getClass().getSimpleName());
            assertEquals("AddTaskCommand",
                    Command.parseCommand("event team meeting --from 2024-11-15 --to 2024-11-16")
                            .getClass().getSimpleName());
            assertEquals("MarkCommand", Command.parseCommand("mark 1").getClass().getSimpleName());
            assertEquals("UnmarkCommand", Command.parseCommand("unmark 1").getClass().getSimpleName());
            assertEquals("DeleteCommand", Command.parseCommand("delete 1").getClass().getSimpleName());
            assertEquals("FindCommand", Command.parseCommand("find book").getClass().getSimpleName());
            assertEquals("ExitCommand", Command.parseCommand("bye").getClass().getSimpleName());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseCommand_unknownCommand_throwsException() {
        try {
            Command.parseCommand("");
            fail();
        } catch (MarvinException e) {
            assertEquals("I don’t know what you want me to do.", e.getMessage());
        }

        try {
            Command.parseCommand("unknown command");
            fail();
        } catch (MarvinException e) {
            assertEquals("I don’t know what you want me to do.", e.getMessage());
        }
    }

    @Test
    public void parseCommand_invalidMarkCommand_throwsException() {
        try {
            Command.parseCommand("mark");
            fail();
        } catch (MarvinException e) {
            assertEquals("You need to tell me which one to mark.", e.getMessage());
        }
    }

    @Test
    public void parseCommand_invalidUnmarkCommand_throwsException() {
        try {
            Command.parseCommand("unmark");
            fail();
        } catch (MarvinException e) {
            assertEquals("You need to tell me which one to unmark.", e.getMessage());
        }
    }

    @Test
    public void parseCommand_invalidDeleteCommand_throwsException() {
        try {
            Command.parseCommand("delete");
            fail();
        } catch (MarvinException e) {
            assertEquals("You need to tell me which one to delete.", e.getMessage());
        }
    }

    @Test
    public void parseCommand_invalidFindCommand_throwsException() {
        try {
            Command.parseCommand("find");
            fail();
        } catch (MarvinException e) {
            assertEquals("It is pointless to search for nothing.", e.getMessage());
        }
    }

    @Test
    public void parseCommand_invalidTodoCommand_throwsException() {
        try {
            Command.parseCommand("todo");
            fail();
        } catch (MarvinException e) {
            assertEquals("A todo without a description is rather pointless.", e.getMessage());
        }
    }

    @Test
    public void parseCommand_invalidDeadlineCommand_throwsException() {
        try {
            Command.parseCommand("deadline submit report");
            fail();
        } catch (MarvinException e) {
            assertEquals("Deadlines tend to require a deadline. Try using -b or --by.", e.getMessage());
        }

        try {
            Command.parseCommand("deadline --by 2024-12-01");
            fail();
        } catch (MarvinException e) {
            assertEquals("A deadline for nothing in particular is deeply confusing.", e.getMessage());
        }
    }

    @Test
    public void parseCommand_invalidEventCommand_throwsException() {
        try {
            Command.parseCommand("event team meeting");
            fail();
        } catch (MarvinException e) {
            assertEquals("An event should probably start at some point. Try using -f or --from.", e.getMessage());
        }

        try {
            Command.parseCommand("event team meeting --from 2024-11-15");
            fail();
        } catch (MarvinException e) {
            assertEquals("Events usually end. Try specifying -t or --to.", e.getMessage());
        }

        try {
            Command.parseCommand("event --from 2024-11-15 --to 2024-11-16");
            fail();
        } catch (MarvinException e) {
            assertEquals("An event with missing details is... incomplete.", e.getMessage());
        }
    }
}
