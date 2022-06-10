package command.tasksCommands.without_arguments;

import cmd.Cmd;
import command.parsing.CommandWithoutMovie;
import visitor.Visitor;

import java.io.BufferedReader;

public class ClearCommand implements CommandWithoutMovie {
    private Cmd cmd;
    private String[] args;

    public String[] getArgs(){
        return args;
    }

    public Cmd getCmd(){
        return cmd;
    }


    
    public String toString(){
        return "clear : очистить коллекцию";
    }

    
    public Object accept(Visitor v) {
        return null;
    }

    
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
            System.out.println("команда 'clear' должна быть без аргументов");
            return false;
        }
        else {
            return true;
        }
    }
}
