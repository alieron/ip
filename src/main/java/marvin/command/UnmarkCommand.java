package marvin.command;

import marvin.MarvinException;
import marvin.Parser;
import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;
import marvin.task.Task;

/**
 * The Command to unmark a tasks, setting it to be incomplete.
 */
public class UnmarkCommand extends Command {
    private final int taskIdx;

    /**
     * Instantiates a new Unmark command.
     *
     * @param argString The main argument
     * @throws MarvinException If the argument is not a valid integer
     */
    public UnmarkCommand(String argString) throws MarvinException {
        try {
            this.taskIdx = Parser.parsePositiveNonZeroInt(argString);
        } catch (NumberFormatException e) {
            throw new MarvinException("You need to tell me which one to unmark.");
        }
    }

    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException {
        Task selectedTask = taskList.unmarkTask(taskIdx);
        storage.saveTasks(taskList);
        String response = "Back to square one...\n  unmarked: "
                + selectedTask;
        return new CommandResult(response);
    }
}
