import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

/**
 * Parser class with static utility functions to parse strings into various types.
 */
public class Parser {
    public static LocalDate parseDate(String token) {
        if (token == null) {
            throw new IllegalArgumentException("token must not be null");
        }
        String trimmed = token.trim();
        DateTimeFormatter[] formatters = new DateTimeFormatter[] {
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
        } catch (DateTimeParseException ignored) {}

        throw new IllegalArgumentException("Unrecognized date/time format: " + token);
    }
}