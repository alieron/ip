package marvin.command;

import java.util.HashMap;
import java.util.Map;

import marvin.MarvinException;
import marvin.Parser;
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

        String argString = Parser.extractMainArg(command);
        Map<String, String> otherArgs = Parser.parseArgs(command);

        switch (type) {
        case UNKNOWN:
            throw new MarvinException("I don’t know what you want me to do.");

            // by this point, the command is at least known
        case EXIT:
            return new ExitCommand();

        case LIST:
            return new ListCommand();

        case MARK:
            try {
                return new MarkCommand(Integer.parseInt(argString));
            } catch (NumberFormatException e) {
                throw new MarvinException("You need to tell me which one to mark.");
            }

        case UNMARK:
            try {
                return new UnmarkCommand(Integer.parseInt(argString));
            } catch (NumberFormatException e) {
                throw new MarvinException("You need to tell me which one to unmark.");
            }

        case DELETE:
            try {
                return new DeleteCommand(Integer.parseInt(argString));
            } catch (NumberFormatException e) {
                throw new MarvinException("You need to tell me which one to delete.");
            }

        case FIND:
            if (argString.isBlank()) {
                throw new MarvinException("It is pointless to search for nothing.");
            }
            return new FindCommand(argString);

        case TODO:
            if (argString.isBlank()) {
                throw new MarvinException("A todo without a description is rather pointless.");
            }

            return new AddTaskCommand(new Todo(argString));

        case DEADLINE:
            if (argString.isBlank()) {
                throw new MarvinException("A deadline for nothing in particular is deeply confusing.");
            }

            String by = Parser.getFlag(otherArgs, "-b", "--by");

            if (by == null) {
                throw new MarvinException("Deadlines tend to require a deadline. Try using -b or --by.");
            }

            return new AddTaskCommand(new Deadline(argString, by));

        case EVENT:
            if (argString.isEmpty()) {
                throw new MarvinException("An event with missing details is... incomplete.");
            }

            String from = Parser.getFlag(otherArgs, "-f", "--from");
            if (from == null) {
                throw new MarvinException("An event should probably start at some point. Try using -f or --from.");
            }

            String to = Parser.getFlag(otherArgs, "-t", "--to");
            if (to == null) {
                throw new MarvinException("Events usually end. Try specifying -t or --to.");
            }

            return new AddTaskCommand(new Event(argString, from, to));

        default:
            // Unknown commands should already be handled by first case
            assert false : "Unknown command not handled properly"; // this line should not be executed
            return null;
        }
    }
}
