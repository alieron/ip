package marvin.command;

import marvin.Storage;
import marvin.TaskList;
import marvin.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.show("Here are your current tasks:\n" + taskList.toString());
    }
}
