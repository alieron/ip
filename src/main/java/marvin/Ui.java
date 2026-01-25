package marvin;

import java.util.Scanner;

public class Ui {
    private static final String SEPARATOR = "____________________________________________________________";

    private final Scanner in = new Scanner(System.in);

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

    public void showGoodbye() {
        show("Goodbye.\n"
                + "Thank you for wasting my time.");
    }

    public void show(String message) {
        System.out.println(SEPARATOR);
        System.out.println(message);
        System.out.println(SEPARATOR);
    }

    public void showError(String message) {
        System.err.println("Error: " + message);
    }

    public String readCommand() {
        return in.hasNextLine() ? in.nextLine() : null;
    }
}