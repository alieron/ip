package marvin;

import marvin.command.*;
import marvin.task.Deadline;
import marvin.task.Event;
import marvin.task.Todo;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

/**
 * Provides static utility methods to parse strings into various types.
 */
public class Parser {
    /**
     * Static function that parses a string and returns the corresponding command.
     *
     * @param commandString The command string
     * @return The command
     * @throws MarvinException If the command string is invalid or null
     */
    public static Command parseCommand(String commandString) throws MarvinException {
        String[] command = commandString.trim().split("\\s+", 2);

        CommandType commandWord = CommandType.from(command[0]);

        if (commandWord == null) {
            throw new MarvinException("I don’t know what you want me to do.");
        }

        String args = command.length > 1 ? command[1] : ""; // only handles one argument

        String desc;

        switch (commandWord) {
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
                throw new MarvinException("I don’t know what you want me to do.");
        }
    }

    /**
     * Static function that parses a date
     *
     * @param token The date string
     * @return The date object
     */
    public static LocalDate parseDate(String token) {
        if (token == null) {
            throw new IllegalArgumentException("token must not be null");
        }
        String trimmed = token.trim();
        DateTimeFormatter[] formatters = new DateTimeFormatter[]{
                DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd")
                        .optionalStart().appendLiteral('T').appendPattern("HH:mm").optionalEnd()
                        .optionalStart().appendLiteral(' ').appendPattern("HHmm").optionalEnd()
                        .toFormatter(),
                new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd")
                        .optionalStart().appendLiteral(' ').appendPattern("HH:mm").optionalEnd()
                        .toFormatter(),
                new DateTimeFormatterBuilder()
                        .appendPattern("d/M/yyyy")
                        .optionalStart().appendLiteral(' ').appendPattern("HHmm").optionalEnd()
                        .toFormatter(),
                new DateTimeFormatterBuilder()
                        .appendPattern("d/M/yyyy")
                        .optionalStart().appendLiteral(' ').appendPattern("HH:mm").optionalEnd()
                        .toFormatter(),
                DateTimeFormatter.ISO_LOCAL_DATE
        };

        for (DateTimeFormatter fmt : formatters) {
            try {
                TemporalAccessor ta = fmt.parse(trimmed);
                return LocalDate.from(ta);
            } catch (DateTimeException ignored) {
                // try next
            }
        }

        // fallback: try strict ISO date
        try {
            return LocalDate.parse(trimmed);
        } catch (DateTimeParseException ignored) {
        }

        throw new IllegalArgumentException("Unrecognized date/time format: " + token);
    }
}