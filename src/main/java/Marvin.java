import java.util.Scanner;

public class Marvin {
    private static final String SEPARATOR = "____________________________________________________________";

    public static void greet() {
        String logo = """
                 __  __
                |  \\/  |                 (_)
                | \\  / | __ _ _ ____   ____ _ __
                | |\\/| |/ _` | `__\\ \\ / /| | `_ \\
                | |  | | (_| | |   \\ V / | | | | |\s
                |_|  |_|\\__,_|_|    \\_/  |_|_| |_|\s
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

    public static void exit() {
        System.out.println("Goodbye.\n"
                + "Thank you for wasting my time.\n"
                + SEPARATOR
        );
    }

    public static void parse() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            System.out.println(SEPARATOR);

            if (input.equals("bye")) {
                exit();
                break;
            } else {
                // echo if command is not matched
                System.out.println(input
                        + "\n"
                        + SEPARATOR
                );
            }
        }
    }

    public static void main(String[] args) {
        greet();
        parse();
    }
}
