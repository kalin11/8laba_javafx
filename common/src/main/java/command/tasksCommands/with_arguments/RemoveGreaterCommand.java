package command.tasksCommands.with_arguments;

import cmd.Cmd;
import command.parsing.CommandWithMovie;
import entity.Movie;
import visitor.Visitor;

import java.io.BufferedReader;

public class RemoveGreaterCommand implements CommandWithMovie {

    private Movie movie = null;

    private String[] args = null;

    public String toString(){
        return "remove_greater {element} - удалить из коллекции все элементы, превыщающие заданный";
    }

    @Override
    public synchronized Object accept(Visitor v, Movie movie, Cmd cmd) {
        return v.visit(this, movie, cmd);
    }

    @Override
    public Object accept(Visitor visitor, Movie m) {
        return null;
    }

//    public Object accept(Visitor v, Movie movie) {
//        return v.visit(this, movie);
//    }

    public String[] getArgs(){
        return args;
    }

    public Movie getMovie(){
        return movie;
    }

    
    public boolean execute(String[] arguments, BufferedReader in) {
//        ObjectParser objectParser = new ObjectParser();
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

