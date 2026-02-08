package marvin.command;

import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;

/**
 * The Command to exit the chatbot.
 */
public class ExitCommand extends Command {
    @Override
    public CommandResult execute(TaskList taskList, Ui ui, Storage storage) {
        String response = ui.getGoodBye();
        return new CommandResult(response, true);
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
