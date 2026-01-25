package marvin.command;

import marvin.MarvinException;
import marvin.Storage;
import marvin.TaskList;
import marvin.Ui;
import marvin.task.Task;

public class UnmarkCommand extends Command {
    private final int taskIdx;

    public UnmarkCommand(int taskIdx) {
        this.taskIdx = taskIdx;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException {
        Task selectedTask = taskList.unmarkTask(taskIdx);
        storage.save(taskList);
        ui.show("Back to square one...\n  unmarked: "
                + selectedTask
        );
    }
}
