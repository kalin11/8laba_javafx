package command.parsing;


import cmd.Cmd;
import entity.Movie;
import visitor.Visitor;

public interface CommandWithMovie extends Command{
    Object accept(Visitor v, Movie movie, Cmd cmd);
    Object accept(Visitor visitor, Movie m);
    //todo тут еще она принимает Cmd

}
