import java.util.Scanner;

public class Marvin {
    private static final String SEPARATOR = "____________________________________________________________";

    private TaskList taskList = new TaskList();

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
        echo(taskList.toString());
    }

    private void addTask(Task task) {
        taskList.addTask(task);
        echo("Task added.\n"
                + "Another tedious thing for you to do.\n  "
                + task
                + "\nYou have " + taskList.numTasks() + " tasks left."
        );
    }

    private void deleteTask(int taskNum) throws MarvinException {
        Task selectedTask = taskList.deleteTask(taskNum);
        echo("Task deleted.\n"
                + "One less thing to occupy this miserable existence.\n  "
                + selectedTask
                + "\nYou have " + taskList.numTasks() + " tasks left."
        );
    }

    private void markTask(int taskNum) throws MarvinException {
        Task selectedTask = taskList.markTask(taskNum);
        echo("That task is now marked as done.\n"
                + "Progress, I suppose.\n  "
                + selectedTask
        );
    }

    private void unmarkTask(int taskNum) throws MarvinException {
        Task selectedTask = taskList.unmarkTask(taskNum);
        echo("The task is now marked as not done.\n"
                + "Back to square one...\n  "
                + selectedTask);
    }

    private void parse(String input) throws MarvinException {
        String[] command = input.trim().split("\\s+", 2);

        String commandWord = command[0];
        String args = command.length > 1 ? command[1] : ""; // only handles one argument

        String desc;

        switch (commandWord) {
            case "bye":
                exit();
                System.exit(0);

            case "list":
                printList();
                break;

            case "mark":
                markTask(Integer.parseInt(args));
                break;

            case "unmark":
                unmarkTask(Integer.parseInt(args));
                break;

            case "delete":
                deleteTask(Integer.parseInt(args));
                break;

            case "todo":
                if (args.isBlank()) {
                    throw new MarvinException("A todo without a description is rather pointless.");
                }

                addTask(new Todo(args));
                break;

            case "deadline":
                String[] split = args.split("/by", 2);
                if (split.length < 2) {
                    throw new MarvinException("Deadlines tend to require a deadline. Try using /by.");
                }

                desc = split[0].trim();

                String by = split[1].trim();
                if (desc.isEmpty() || by.isEmpty()) {
                    throw new MarvinException("A deadline for nothing in particular is deeply confusing.");
                }

                addTask(new Deadline(desc, by));
                break;

            case "event":
                String[] fromSplit = args.split("/from", 2);
                if (fromSplit.length < 2) {
                    throw new MarvinException("An event should probably start at some point. Try /from.");
                }

                desc = fromSplit[0].trim();

                String[] toSplit = fromSplit[1].split("/to", 2);
                if (toSplit.length < 2) {
                    throw new MarvinException("Events usually end. Try specifying /to.");
                }

                String from = toSplit[0].trim();
                String to = toSplit[1].trim();
                if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    throw new MarvinException("An event with missing details is... incomplete.");
                }

                addTask(new Event(desc, from, to));
                break;

            default:
                throw new MarvinException("I don’t know what you want me to do.");
        }
    }

    public static void main(String[] args) {
        Marvin chatbot = new Marvin();
        chatbot.greet();

        Scanner scanner = new Scanner(System.in);

        while (true) { // ignore infinite loop warning, parse can exit with the "bye" command
            try {
                String input = scanner.nextLine();
                chatbot.parse(input);
            } catch (MarvinException e) {
                chatbot.echo(e.getMessage());
            }
        }
    }
}
