package marvin.gui;

import java.util.Scanner;

/**
 * Represents the interface between the user and the chatbot.
 */
public class Ui {
    private static final String SEPARATOR = "___________________________________________________";

    private final Scanner in = new Scanner(System.in);

    /**
     * Prints the welcome message.
     */
    public String getWelcome() {
        String logo = """
                 __  __
                |  \\/  |                 (_)
                | \\  / | __ _ _ ____   ____ _ __
                | |\\/| |/ _` | `__\\ \\ / /| | `_ \\
                | |  | | (_| | |   \\ V / | | | | |
                |_|  |_|\\__,_|_|    \\_/  |_|_| |_|
                """;

        return "Hello. I am Marvin.\n"
                + logo
                + "\nI have a brain the size of a planet,\n"
                + "and here I am, greeting users.\n"
                + "It’s all terribly depressing.\n"
                + "\nAnyways, what do you want?\n"
                + "Not that it matters...\n"
                + SEPARATOR;
    }

    /**
     * Prints the goodbye message.
     */
    public String getGoodBye() {
        return wrapMessage("Goodbye.\n"
                + "Thank you for wasting my time.");
    }

    /**
     * Prints a message wrapped with horizontal line separators.
     *
     * @param message The message
     */
    public String wrapMessage(String message) {
        return SEPARATOR + "\n" + message + "\n" + SEPARATOR;
    }

    /**
     * Prints an error message.
     *
     * @param message The message
     */
    public String getError(String message) {
        return wrapMessage("Error: " + message);
    }
}
