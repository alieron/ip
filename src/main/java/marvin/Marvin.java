package marvin;

import marvin.command.Command;
import marvin.command.CommandResult;
import marvin.gui.Ui;

/**
 * Marvin the paranoid android, now in chatbot form.
 */
public class Marvin {
    private TaskList taskList;
    private Storage storage;
    private Ui ui;

    Marvin() {
        storage = new Storage("data/tasks.txt");
        ui = new Ui();
        try {
            taskList = storage.load();
        } catch (MarvinException e) {
            ui.getError(e.getMessage());
            taskList = new TaskList();
        }
    }

    public String welcomUser() {
        return ui.getWelcome();
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param fullCommand The user's command
     * @return Marvin's response
     */
    public CommandResult runCommand(String fullCommand) {
        try {
            Command c = Parser.parseCommand(fullCommand);
            return c.execute(taskList, ui, storage);
        } catch (MarvinException e) {
            return new CommandResult(ui.wrapMessage(e.getMessage()), false);
        }
    }
}
