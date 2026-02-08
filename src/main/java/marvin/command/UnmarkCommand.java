package marvin.command;

import marvin.MarvinException;
import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;
import marvin.task.Task;

/**
 * The Command to unmark a tasks, setting it to be incomplete.
 */
public class UnmarkCommand extends Command {
    private final int taskIdx;

    public UnmarkCommand(int taskIdx) {
        this.taskIdx = taskIdx;
    }

    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException {
        Task selectedTask = taskList.unmarkTask(taskIdx);
        storage.saveTasks(taskList);
        String response = ui.wrapMessage("Back to square one...\n  unmarked: "
                + selectedTask
        );
        return new CommandResult(response);
    }
}
