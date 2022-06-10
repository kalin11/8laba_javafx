package command.parsing;
import java.io.BufferedReader;

public interface Command {
    boolean execute(String[] arguments, BufferedReader in);
    boolean valid(String[] args);
}
