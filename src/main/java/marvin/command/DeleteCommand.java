package marvin.command;

import marvin.MarvinException;
import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;
import marvin.task.Task;

public class DeleteCommand extends Command {
    private final int taskIdx;

    public DeleteCommand(int taskIdx) {
        this.taskIdx = taskIdx;
    }

    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException {
        Task selectedTask = taskList.deleteTask(taskIdx);
        storage.save(taskList);
        String response = ui.wrapMessage("One less thing to occupy this miserable existence.\n  deleting: "
                + selectedTask
                + "\nYou have " + taskList.numTasks() + " tasks left."
        );
        return new CommandResult(response);
    }
}
