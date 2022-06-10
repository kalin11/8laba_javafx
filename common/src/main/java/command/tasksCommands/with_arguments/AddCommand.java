package command.tasksCommands.with_arguments;

import cmd.Cmd;
import command.parsing.CommandWithMovie;
import entity.Movie;
import visitor.Visitor;

import java.io.BufferedReader;
import java.io.IOException;

public class AddCommand implements CommandWithMovie {
    private Movie movie = null;
    private String[] args = null;
    private Cmd cmd;

    
    public String toString() {
        return "add {element} - добавить новый элемент в коллекцию";
    }

    public Movie getMovie() {
        return movie;
    }

    public Cmd getCmd() {
        return cmd;
    }

    public synchronized Object accept(Visitor v, Movie movie, Cmd cmd) {
        return v.visit(this, movie, cmd);
    }

    public Object accept(Visitor visitor, Movie movie){
        return null;
    }
    public String[] getArgs(){
        return args;
    }

    
    public boolean execute(String[] arguments, BufferedReader in) {
        args = arguments;
        return true;
    }

    
    public boolean valid(String[] args) {
        this.args = args;
        if (args.length!=0){
            System.out.println("команда 'add' должна быть без аргументов");
            return false;
        }
        else {
            return true;
        }
    }
}