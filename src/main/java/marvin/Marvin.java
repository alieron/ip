package marvin;

import marvin.command.Command;

public class Marvin {
    private TaskList taskList;
    private Storage storage;
    private Ui ui;

    private Marvin() {
        storage = new Storage("data/tasks.txt");
        ui = new Ui();
        try {
            taskList = storage.load();
        } catch (MarvinException e) {
            ui.showError(e.getMessage());
            taskList = new TaskList();
        }
    }

    private void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parseCommand(fullCommand);
                c.execute(taskList, ui, storage);
                isExit = c.isExit();
            } catch (MarvinException e) {
                ui.show(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Marvin().run();
    }
}
