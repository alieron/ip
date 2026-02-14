package marvin;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser class with static utility functions to parse strings into various types.
 */
public class Parser {

    /**
     * Extract the main(first) argument from the command.
     *
     * @param command The command string, can be an empty string but not null
     * @return The main argument, will be an empty string if no main argument is present
     */
    public static String extractMainArg(String command) {
        assert command != null : "Command should not be null";

        Pattern mainArgPattern = Pattern.compile(
                "^[a-zA-Z]*\\s*([^-]*)(?!-)",
                Pattern.DOTALL
        );

        Matcher matcher = mainArgPattern.matcher(command);

        if (matcher.find()) {
            return matcher.group(1);
        }
        // regex should match even empty strings
        assert false : "Matcher should match command";
        return "";
    }

    /**
     * Parse any additional arguments into a map by their respective flags.
     *
     * @param command The command string
     * @return the map
     */
    public static Map<String, String> parseArgs(String command) {
        Map<String, String> args = new HashMap<>();

        Pattern flagArgsPattern = Pattern.compile(
                "((?:-|--)[a-zA-Z]*)\\s+([^-](?:(?!\\s+-).)*)",
                Pattern.DOTALL
        );

        Matcher matcher = flagArgsPattern.matcher(command);

        while (matcher.find()) {
            String flag = matcher.group(1);
            String value = matcher.group(2);

            args.put(flag, value);
        }

        return args;
    }


    /**
     * Get the value associated with the given flag.
     *
     * @param args      The map of flags -> values
     * @param shortFlag The short flag to match
     * @param longFlag  The long flag to match
     * @return The value associated with the flag, null if not found
     */
    public static String getFlag(Map<String, String> args, String shortFlag, String longFlag) {
        return args.getOrDefault(longFlag, args.get(shortFlag));
    }

    /**
     * Static function that parses a date.
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
            // nothing to be done
        }

        throw new IllegalArgumentException("Unrecognized date/time format: " + token);
    }
}
