package marvin.command;

import java.util.HashMap;
import java.util.Map;

public enum CommandType {
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    FIND("find"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    EXIT("bye"),
    UNKNOWN("unknown");

    private static final Map<String, CommandType> MAP = new HashMap<>();

    static {
        for (CommandType t : values()) {
            MAP.put(t.word, t);
        }
    }

    private final String word;

    CommandType(String word) {
        this.word = word;
    }

    public static CommandType from(String command) {
        CommandType type;
        if (command == null) {
            type = UNKNOWN;
        }
        type = MAP.getOrDefault(command.toLowerCase(), UNKNOWN);
        assert type != null : "Command type should not be null";
        return type;
    }
}
