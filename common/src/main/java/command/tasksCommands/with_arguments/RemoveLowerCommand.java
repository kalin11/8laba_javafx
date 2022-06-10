package command.tasksCommands.with_arguments;

import cmd.Cmd;
import command.parsing.CommandWithMovie;
import entity.Movie;
import visitor.Visitor;

import java.io.BufferedReader;

public class RemoveLowerCommand implements CommandWithMovie {

    private Movie movie = null;

    private String[] args = null;
    public String[] getArgs(){
        return args;
    }

    public String toString(){
        return "remove_lower {element} - удалить из коллекции все элементы, меньшие,чем заданный";
    }

    
    public synchronized Object accept(Visitor v, Movie movie, Cmd cmd) {
        return v.visit(this, movie, cmd);
    }

    @Override
    public Object accept(Visitor visitor, Movie m) {
        return null;
    }

    public Movie getMovie(){
        return movie;
    }

    
    public boolean execute(String[] arguments, BufferedReader in) {
        args = arguments;
        return true;
    }
    public boolean valid(String[] args) {
        this.args = args;
        if (args.length == 0){
            return true;
        }
        else{
            System.out.println("вы должны вводить значения полей в консоли");
            return false;
        }
    }
}
