package marvin;

public enum CommandType {
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    BYE("bye");

    private final String keyword;

    private CommandType(String keyword) {
        this.keyword = keyword;
    }

    public static CommandType from(String input) throws MarvinException { // factory method
        for (CommandType type : values()) {
            if (type.keyword.equals(input)) {
                return type;
            }
        }
        throw new MarvinException("I don’t know what you want me to do.");
    }
}

