package command.tasksCommands.without_arguments;

import cmd.Cmd;
import command.parsing.CommandWithoutMovie;
import visitor.Visitor;

import java.io.BufferedReader;

public class PrintUniqueOscarsCount implements CommandWithoutMovie {
    private String[] args;

    public String[] getArgs(){
        return args;
    }


    public String toString(){
        return "print_unique_oscars_count : вывести уникальные значения поля oscarsCount всех элементов в коллекции";
    }

    
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public Object accept(Visitor v, Cmd cmd) {
        return v.visit(this, cmd);
    }


    public boolean execute(String[] arguments, BufferedReader in) {
        args = arguments;
        return true;
    }

    
    public boolean valid(String[] args) {
        this.args = args;
        if (args.length!=0){
            System.out.println("команда 'print_unique_oscars_count' должна быть без аргументов");
            return false;
        }
        else {
            return true;
        }
    }


}
