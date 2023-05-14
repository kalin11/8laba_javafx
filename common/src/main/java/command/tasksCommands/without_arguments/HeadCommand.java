package command.tasksCommands.without_arguments;

//import lab5.Collection.CollectionManager;
import cmd.Cmd;
import command.parsing.CommandWithoutMovie;
import visitor.Visitor;

import java.io.BufferedReader;

public class HeadCommand implements CommandWithoutMovie {
    private String[] args;

    public String[] getArgs(){
        return args;
    }

    
    public String toString() {
        return "head - вывести первый элемент коллекции";
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
            System.out.println("команда 'head' должна быть без аргументов");
            return false;
        }
        else {
            return true;
        }
    }


}

