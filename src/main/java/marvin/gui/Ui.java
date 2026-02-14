package marvin.gui;

/**
 * Represents the interface between the user and the chatbot.
 */
public class Ui {
    /**
     * Prints the welcome message.
     *
     * @return The welcome message
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
                + "\nI have a brain the size of a planet,\n"
                + "and here I am, greeting users.\n"
                + "It’s all terribly depressing.\n"
                + "\nAnyways, what do you want?\n"
                + "Not that it matters...\n";
    }

    /**
     * Prints the goodbye message.
     *
     * @return The goodbye message
     */
    public String getGoodBye() {
        return "Goodbye.\nThank you for wasting my time.";
    }

    /**
     * Prints an error message.
     *
     * @param message The error message
     * @return The message pre-appended with "Error: "
     */
    public String getError(String message) {
        return "Error: " + message;
    }
}
