package marvin.command;

import marvin.MarvinException;
import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;

/**
 * The Command to search the tasks for matching tasks with a keyword in their descriptions.
 */
public class FindCommand extends Command {
    private final String snippet;

    /**
     * Instantiates a new Find command.
     *
     * @param argString The main argument
     * @throws MarvinException If no other arguments are provided
     */
    public FindCommand(String argString) throws MarvinException {
        if (argString.isBlank()) {
            throw new MarvinException("It is pointless to search for nothing.");
        }
        this.snippet = argString;
    }

    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) {
        String response = "Here are the matching tasks in your list:\n"
                + taskList.findTasksContains(snippet).toString();
        return new CommandResult(response);
    }
}
