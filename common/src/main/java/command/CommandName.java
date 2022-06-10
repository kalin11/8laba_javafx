package command;

public enum CommandName {
    HELP("help"),
    ADD("add"),
    EXIT("exit"),
    SHOW("show"),
    HEAD("head"),
    CLEAR("clear"),
    INFO("info"),
    AVERAGE_OF_LENGTH("average_of_length"),
    PRINT_UNIQUE_OSCARS_COUNT("print_unique_oscars_count"),
    UPDATE_ID("update"),
    REMOVE_BY_ID("remove_by_id"),
    COUNT_BY_GENRE("count_by_genre"),
    REMOVE_GREATER("remove_greater"),
    REMOVE_LOWER("remove_lower"),
    EXECUTE_SCRIPT("execute_script"),
    REGISTER("register"),
    LOGIN("login"),
    UNKNOWN("unknown");


    private final String value;
    CommandName(String name) {
        value = name;
    }
    public static CommandName fromString(String text){
        for (CommandName c : CommandName.values()){
            if (c.value.equalsIgnoreCase(text)){
                return c;
            }
        }
        return UNKNOWN;
    }
}
