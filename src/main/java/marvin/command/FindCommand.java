package marvin.command;

import marvin.MarvinException;
import marvin.Storage;
import marvin.TaskList;
import marvin.Ui;

public class FindCommand extends Command {
    private final String snippet;

    public FindCommand(String snippet) {
        this.snippet = snippet;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException {
        ui.show("Here are the matching tasks in your list:\n" +
                taskList.findTasksContains(snippet).toString());
    }
}
