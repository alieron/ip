package marvin.command;

import marvin.MarvinException;
import marvin.Parser;
import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;
import marvin.task.Task;

/**
 * The Command to delete a task.
 */
public class DeleteCommand extends Command {
    private final int taskIdx;

    /**
     * Instantiates a new Delete command.
     *
     * @param argString The string of arguments
     * @throws MarvinException if the argument is not a valid integer
     */
    public DeleteCommand(String argString) throws MarvinException {
        try {
            this.taskIdx = Parser.parsePositiveNonZeroInt(argString);
        } catch (NumberFormatException e) {
            throw new MarvinException("You need to tell me which one to delete.");
        }
    }

    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException {
        Task selectedTask = taskList.deleteTask(taskIdx);
        storage.saveTasks(taskList);
        String response = "One less thing to occupy this miserable existence.\n  deleting: "
                + selectedTask
                + "\nYou have " + taskList.numTasks() + " tasks left.";
        return new CommandResult(response);
    }
}
