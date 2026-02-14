package marvin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class ParserTest {
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
