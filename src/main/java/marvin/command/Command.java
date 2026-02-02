package marvin.command;

import marvin.MarvinException;
import marvin.Storage;
import marvin.TaskList;
import marvin.gui.Ui;

/**
 * Base command: implementors must provide execute.
 * Default isExit() returns false; override in exit commands.
 */
public abstract class Command {

    public abstract CommandResult execute(TaskList taskList, Ui ui, Storage storage) throws MarvinException;

    public boolean isExit() {
        return false;
    }
}
