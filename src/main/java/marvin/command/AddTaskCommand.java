package marvin.command;

import marvin.MarvinException;
import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;
import marvin.task.Task;

/**
 * The Command to add tasks.
 */
public class AddTaskCommand extends Command {
    private final Task task;

    public AddTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException {
        taskList.addTask(task);
        storage.saveTasks(taskList);
        String response = ui.wrapMessage("Another tedious thing for you to do.\n  adding: "
                + task
                + "\nYou have " + taskList.numTasks() + " tasks left."
        );
        return new CommandResult(response);
    }
}
