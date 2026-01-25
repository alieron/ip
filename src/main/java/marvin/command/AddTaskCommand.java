package marvin.command;

import marvin.MarvinException;
import marvin.Storage;
import marvin.TaskList;
import marvin.Ui;
import marvin.task.Task;

public class AddTaskCommand extends Command {
    private final Task task;

    public AddTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException {
        taskList.addTask(task);
        storage.save(taskList);
        ui.show("Another tedious thing for you to do.\n  adding: "
                + task
                + "\nYou have " + taskList.numTasks() + " tasks left."
        );
    }
}
