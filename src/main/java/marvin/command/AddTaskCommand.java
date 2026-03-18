package marvin.command;

import java.util.Map;

import marvin.MarvinException;
import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;
import marvin.task.Deadline;
import marvin.task.Event;
import marvin.task.Task;
import marvin.task.Todo;

/**
 * The Command to add tasks.
 */
public class AddTaskCommand extends Command {
    private Task task;

    /**
     * Instantiates a new Add task command.
     *
     * @param type      The type of task to create
     * @param argString The main argument
     * @param otherArgs The other arguments
     * @throws MarvinException If the command is invalid
     */
    public AddTaskCommand(CommandType type, String argString, Map<String, String> otherArgs) throws MarvinException {
        switch (type) {
        case TODO:
            this.task = Todo.fromCommand(argString);
            break;
        case DEADLINE:
            this.task = Deadline.fromCommand(argString, otherArgs);
            break;
        case EVENT:
            this.task = Event.fromCommand(argString, otherArgs);
            break;
        default:
            // Other command types should already be handled
            assert false : "Unknown task type not handled properly"; // this line should not be executed
        }
    }

    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException {
        taskList.addTask(task);
        storage.saveTasks(taskList);
        String response = "Another tedious thing for you to do.\n  adding: "
                + task
                + "\nYou have " + taskList.numTasks() + " tasks left.";
        return new CommandResult(response);
    }
}
