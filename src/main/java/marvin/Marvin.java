package marvin;

import marvin.command.CommandType;
import marvin.task.Deadline;
import marvin.task.Event;
import marvin.task.Task;
import marvin.task.Todo;

import java.util.List;
import java.util.Scanner;

public class Marvin {
    private static final String SEPARATOR = "____________________________________________________________";

    private TaskList taskList = new TaskList();
    private Storage storage = new Storage();

    private void start() {
        List<Task> loaded = storage.load();
        for (Task t : loaded) {
            taskList.addTask(t);
        }

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

    private void awaitInput() {
        Scanner scanner = new Scanner(System.in);

        while (true) { // ignore infinite loop warning, parse can exit with the "bye" command
            try {
                String input = scanner.nextLine();
                parse(input);
            } catch (MarvinException e) {
                echo(e.getMessage());
            }
        }
    }

    private void exit() {
        storage.save(taskList);
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
        storage.save(taskList);
        echo("Another tedious thing for you to do.\n  adding: "
                + task
                + "\nYou have " + taskList.numTasks() + " tasks left."
        );
    }

    private void deleteTask(int taskNum) throws MarvinException {
        Task selectedTask = taskList.deleteTask(taskNum);
        storage.save(taskList);
        echo("One less thing to occupy this miserable existence.\n  deleting: "
                + selectedTask
                + "\nYou have " + taskList.numTasks() + " tasks left."
        );
    }

    private void markTask(int taskNum) throws MarvinException {
        Task selectedTask = taskList.markTask(taskNum);
        storage.save(taskList);
        echo("Progress, I suppose.\n  marked: "
                + selectedTask
        );
    }

    private void unmarkTask(int taskNum) throws MarvinException {
        Task selectedTask = taskList.unmarkTask(taskNum);
        storage.save(taskList);
        echo("Back to square one...\n  unmarked: "
                + selectedTask);
    }

    private void parse(String input) throws MarvinException {
        String[] command = input.trim().split("\\s+", 2);

        CommandType commandWord = CommandType.from(command[0]);
        String args = command.length > 1 ? command[1] : ""; // only handles one argument

        String desc;

        switch (commandWord) {
            case BYE:
                exit();
                System.exit(0);

            case LIST:
                printList();
                break;

            case MARK:
                markTask(Integer.parseInt(args));
                break;

            case UNMARK:
                unmarkTask(Integer.parseInt(args));
                break;

            case DELETE:
                deleteTask(Integer.parseInt(args));
                break;

            case TODO:
                if (args.isBlank()) {
                    throw new MarvinException("A todo without a description is rather pointless.");
                }

                addTask(new Todo(args));
                break;

            case DEADLINE:
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

            case EVENT:
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
        chatbot.start();
        chatbot.awaitInput();
    }
}
