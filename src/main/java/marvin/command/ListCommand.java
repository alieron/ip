package marvin.command;

import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;

/**
 * The Command to list all tasks.
 */
public class ListCommand extends Command {
    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) {
        String response = "Here are your current tasks:\n" + taskList.toString();
        return new CommandResult(response);
    }
}
