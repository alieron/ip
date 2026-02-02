package marvin.command;

import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;

public class ListCommand extends Command {
    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) {
        String response = ui.wrapMessage("Here are your current tasks:\n" + taskList.toString());
        return new CommandResult(response);
    }
}
