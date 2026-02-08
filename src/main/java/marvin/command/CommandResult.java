package marvin.command;

/**
 * Record of the result.
 * Result of running a command, determines whether the chatbot should exit or not and the chatbot's response.
 */
public record CommandResult(String response, boolean shouldExit) {

    public CommandResult(String response) {
        this(response, false);
    }
}
