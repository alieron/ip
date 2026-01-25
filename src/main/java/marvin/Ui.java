package marvin;

import java.util.Scanner;

/**
 * Represents the interface between the user and the chatbot.
 */
public class Ui {
    private static final String SEPARATOR = "____________________________________________________________";

    private final Scanner in = new Scanner(System.in);

    /**
     * Prints the welcome message.
     */
    public void showWelcome() {
        String logo = """
                 __  __
                |  \\/  |                 (_)
                | \\  / | __ _ _ ____   ____ _ __
                | |\\/| |/ _` | `__\\ \\ / /| | `_ \\
                | |  | | (_| | |   \\ V / | | | | |
                |_|  |_|\\__,_|_|    \\_/  |_|_| |_|
                """;

        System.out.println("Hello. I am Marvin.\n"
                + logo
                + "\nI have a brain the size of a planet,\n"
                + "and here I am, greeting users.\n"
                + "It’s all terribly depressing.\n"
                + "\nAnyways, what do you want?\n"
                + "Not that it matters...\n"
                + SEPARATOR
        );
    }

    /**
     * Prints the goodbye message.
     */
    public void showGoodbye() {
        show("Goodbye.\n"
                + "Thank you for wasting my time.");
    }

    /**
     * Prints a message wrapped with horizontal line separators.
     *
     * @param message The message
     */
    public void show(String message) {
        System.out.println(SEPARATOR);
        System.out.println(message);
        System.out.println(SEPARATOR);
    }

    /**
     * Prints an error message.
     *
     * @param message The message
     */
    public void showError(String message) {
        System.err.println("Error: " + message);
    }

    /**
     * Returns the command string when the user sends a newline character.
     *
     * @return The command string
     */
    public String readCommand() {
        return in.hasNextLine() ? in.nextLine() : null;
    }
}