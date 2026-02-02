package marvin.command;

public class CommandResult {
    private final String response;
    private final boolean shouldExit;

    public CommandResult(String response, boolean shouldExit) {
        this.response = response;
        this.shouldExit = shouldExit;
    }

    public CommandResult(String response) {
        this(response, false);
    }

    public String getResponse() {
        return response;
    }

    public boolean shouldExit() {
        return shouldExit;
    }
}
