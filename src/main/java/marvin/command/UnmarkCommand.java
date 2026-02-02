package marvin.command;

import marvin.MarvinException;
import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;
import marvin.task.Task;

public class UnmarkCommand extends Command {
    private final int taskIdx;

    public UnmarkCommand(int taskIdx) {
        this.taskIdx = taskIdx;
    }

    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException {
        Task selectedTask = taskList.unmarkTask(taskIdx);
        storage.save(taskList);
        String response = ui.wrapMessage("Back to square one...\n  unmarked: "
                + selectedTask
        );
        return new CommandResult(response);
    }
}
