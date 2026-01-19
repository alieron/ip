import java.util.Scanner;
import java.lang.StringBuilder;

public class Marvin {
    private static final String SEPARATOR = "____________________________________________________________";

    private int index = 0;
    private Task[] taskList = new Task[100];

    private void greet() {
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
                sb.append(".");
                sb.append(taskList[i]);
                if (i < index - 1) sb.append("\n");
            }

            echo("Here are the tasks in your list:\n" + sb.toString());
        }
    }

    private void addTask(Task task) {
        taskList[index++] = task;
        echo("Added:\n  " + task + "\nYou have " + index + " tasks left.");
    }

    private void markTask(int taskNum) {
        Task selectedTask = taskList[taskNum - 1];
        selectedTask.mark(); // doesn't care about current state, just sets to true
        echo("Nice! I've marked this task as done:\n  " + selectedTask);
    }

    private void unmarkTask(int taskNum) {
        Task selectedTask = taskList[taskNum - 1];
        selectedTask.unmark(); // doesn't care about current state, just sets to false
        echo("OK, I've marked this task as not done yet:\n  " + selectedTask);
    }

    private void parse() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            String[] command = input.trim().split("\\s+", 2);

            String commandWord = command[0];
            String args = command.length > 1 ? command[1] : ""; // only handles one argument

            String desc;

            switch (commandWord) {
                case "bye":
                    exit();
                    return;

                case "list":
                    printList();
                    break;

                case "mark":
                    markTask(Integer.parseInt(args));
                    break;

                case "unmark":
                    unmarkTask(Integer.parseInt(args));
                    break;

                case "todo":
                    addTask(new Todo(args));
                    break;

                case "deadline":
                    String[] split = args.split("/by", 2);
                    desc = split[0].trim();
                    String by = split.length > 1 ? split[1].trim() : "";
                    addTask(new Deadline(desc, by));
                    break;

                case "event":
                    String[] fromSplit = args.split("/from", 2);
                    desc = fromSplit[0].trim();

                    String[] toSplit = fromSplit[1].split("/to", 2);
                    String from = toSplit[0].trim();
                    String to = toSplit[1].trim();
                    addTask(new Event(desc, from, to));
                    break;

                default:
                    // add if command is not matched
//                    addTask(new Task(commandWord));
            }
        }
    }

    public static void main(String[] args) {
        Marvin chatbot = new Marvin();
        chatbot.greet();
        chatbot.parse();
    }
}
