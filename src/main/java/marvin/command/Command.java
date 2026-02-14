package marvin.command;

import java.util.HashMap;
import java.util.Map;

import marvin.MarvinException;
import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;
import marvin.task.Deadline;
import marvin.task.Event;
import marvin.task.Todo;

/**
 * Base command: implementors must provide execute.
 * Default isExit() returns false; override in exit commands.
 */
public abstract class Command {

    public abstract CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException;

    public boolean isExit() {
        return false;
    }

    /**
     * The enum for the types of commands the user can invoke.
     */
    private enum CommandType {
        LIST("list"),
        MARK("mark"),
        UNMARK("unmark"),
        DELETE("delete"),
        FIND("find"),
        TODO("todo"),
        DEADLINE("deadline"),
        EVENT("event"),
        EXIT("bye"),
        UNKNOWN("unknown");

        private static final Map<String, CommandType> MAP = new HashMap<>();

        static {
            for (CommandType t : values()) {
                MAP.put(t.word, t);
            }
        }

        private final String word;

        CommandType(String word) {
            this.word = word;
        }

        /**
         * Factory method: Returns the command type from the command keyword.
         *
         * @param command The command
         * @return The corresponding command type
         */
        private static CommandType from(String command) {
            CommandType type;
            String keyword = command.trim().split("\\s+", 2)[0];
            if (keyword == null) {
                return UNKNOWN;
            }
            type = MAP.getOrDefault(keyword.toLowerCase(), UNKNOWN);
            assert type != null : "Command type should not be null";
            return type;
        }
    }

    /**
     * Static function that parses a string and returns the corresponding command.
     *
     * @param command The command string
     * @return The command
     * @throws MarvinException If the command is invalid or null
     */
    public static Command parseCommand(String command) throws MarvinException {
        CommandType type = CommandType.from(command);

        String[] commandParts = command.trim().split("\\s+", 2);

        String args = commandParts.length > 1 ? commandParts[1] : ""; // only handles one argument

        String desc;

        switch (type) {
        case UNKNOWN:
            throw new MarvinException("I don’t know what you want me to do.");

        case EXIT:
            return new ExitCommand();

        case LIST:
            return new ListCommand();

        case MARK:
            return new MarkCommand(Integer.parseInt(args));

        case UNMARK:
            return new UnmarkCommand(Integer.parseInt(args));

        case DELETE:
            return new DeleteCommand(Integer.parseInt(args));

        case FIND:
            return new FindCommand(args);

        case TODO:
            if (args.isBlank()) {
                throw new MarvinException("A todo without a description is rather pointless.");
            }

            return new AddTaskCommand(new Todo(args));

        case DEADLINE:
            String[] split = args.split("/by", 2);
            if (split.length < 2) {
                throw new MarvinException("Deadlines tend to require a deadline. Try using /by.");
            }

            desc = split[0].trim();

            String by = split[1].trim();
            if (desc.isEmpty() || by.isEmpty()) {
                throw new MarvinException("A deadline for nothing in particular is deeply confusing.");
            }

            return new AddTaskCommand(new Deadline(desc, by));

        case EVENT:
            String[] fromSplit = args.split("/from", 2);
            if (fromSplit.length < 2) {
                throw new MarvinException("An event should probably start at some point. Try /from.");
            }

            desc = fromSplit[0].trim();

            String[] toSplit = fromSplit[1].split("/to", 2);
            if (toSplit.length < 2) {
                throw new MarvinException("Events usually end. Try specifying /to.");
            }

            String from = toSplit[0].trim();
            String to = toSplit[1].trim();
            if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                throw new MarvinException("An event with missing details is... incomplete.");
            }

            return new AddTaskCommand(new Event(desc, from, to));

        default:
            // Unknown commands should already be handled by first case
            assert false : "Unknown command not handled properly"; // this line should not be executed
            return null;
        }
    }
}
