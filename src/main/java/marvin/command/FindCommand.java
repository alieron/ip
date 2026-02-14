package marvin.command;

import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;

/**
 * The Command to search the tasks for matching tasks with a keyword in their descriptions.
 */
public class FindCommand extends Command {
    private final String snippet;

    public FindCommand(String snippet) {
        this.snippet = snippet;
    }

    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) {
        String response = "Here are the matching tasks in your list:\n"
                + taskList.findTasksContains(snippet).toString();
        return new CommandResult(response);
    }
}
