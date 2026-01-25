package marvin.command;

import java.util.HashMap;
import java.util.Map;

public enum CommandType {
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    EXIT("bye");

    private final String word;
    private static final Map<String, CommandType> MAP = new HashMap<>();

    static {
        for (CommandType t : values()) MAP.put(t.word, t);
    }

    CommandType(String word) { this.word = word; }

    public static CommandType from(String w) {
        if (w == null) return null;
        return MAP.get(w.toLowerCase());
    }
}