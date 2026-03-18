package marvin.command;

import marvin.MarvinException;
import marvin.Parser;
import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;
import marvin.task.Task;

/**
 * The Command to mark a task, setting it to be complete.
 */
public class MarkCommand extends Command {
    private final int taskIdx;

    /**
     * Instantiates a new Mark command.
     *
     * @param argString The main argument
     * @throws MarvinException If the argument is not a valid integer
     */
    public MarkCommand(String argString) throws MarvinException {
        try {
            this.taskIdx = Parser.parsePositiveNonZeroInt(argString);
        } catch (NumberFormatException e) {
            throw new MarvinException("You need to tell me which one to mark.");
        }
    }

    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException {
        Task selectedTask = taskList.markTask(taskIdx);
        storage.saveTasks(taskList);
        String response = "Progress, I suppose.\n  marked: "
                + selectedTask;
        return new CommandResult(response);
    }
}
