package command.parsing;


import cmd.Cmd;
import visitor.Visitor;

public interface CommandWithoutMovie extends Command {
    Object accept(Visitor v);
    Object accept(Visitor v, Cmd cmd);
}
