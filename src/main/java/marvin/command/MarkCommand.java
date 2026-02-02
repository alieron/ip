package marvin.command;

import marvin.MarvinException;
import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;
import marvin.task.Task;

public class MarkCommand extends Command {
    private final int taskIdx;

    public MarkCommand(int taskIdx) {
        this.taskIdx = taskIdx;
    }

    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException {
        Task selectedTask = taskList.markTask(taskIdx);
        storage.save(taskList);
        String response = ui.wrapMessage("Progress, I suppose.\n  marked: "
                + selectedTask
        );
        return new CommandResult(response);
    }
}
