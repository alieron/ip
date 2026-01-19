import java.util.Scanner;
import java.lang.StringBuilder;

public class Marvin {
    private static final String SEPARATOR = "____________________________________________________________";

    private int index = 0;
    private String[] taskList = new String[100];

    private void greet() {
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

    private void exit() {
        echo("Goodbye.\n"
                + "Thank you for wasting my time.");
    }

    private void echo(String out) {
        System.out.println(SEPARATOR);
        System.out.println(out);
        System.out.println(SEPARATOR);
    }

    private void printList() {
        if (index == 0) {
            echo("There is nothing here. [sigh]");
        } else {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < index; i++) {
                sb.append(i + 1);
                sb.append(". ");
                sb.append(taskList[i]);
                if (i < index - 1) sb.append("\n");
            }

            echo(sb.toString());
        }
    }

    private void addTask(String task) {
        taskList[index++] = task;
        echo("added: " + task);
    }

    private void parse() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            switch (input) {
                case "bye":
                    exit();
                    return;

                case "list":
                    printList();
                    break;

                default:
                    // add if command is not matched
                    addTask(input);
            }
        }
    }

    public static void main(String[] args) {
        Marvin chatbot = new Marvin();
        chatbot.greet();
        chatbot.parse();
    }
}
