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
    EXIT("bye");

    private static final Map<String, CommandType> MAP = new HashMap<>();

    static {
        for (CommandType t : values()) MAP.put(t.word, t);
    }

    private final String word;

    CommandType(String word) {
        this.word = word;
    }

    public static CommandType from(String w) {
        if (w == null) return null;
        return MAP.get(w.toLowerCase());
    }
}