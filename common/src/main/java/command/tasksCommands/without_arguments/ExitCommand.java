package command.tasksCommands.without_arguments;


import cmd.Cmd;
import command.parsing.CommandWithoutMovie;
import visitor.Visitor;

import java.io.BufferedReader;

public class ExitCommand implements CommandWithoutMovie {
    private String[] args;

    public String[] getArgs(){
        return args;
    }

    
    public String toString() {
        return  "exit - завершить проограмму, без сохранения в файл";
    }

    
    public Object accept(Visitor v) {
        throw new RuntimeException("How");
    }

    @Override
    public Object accept(Visitor v, Cmd cmd) {
        return null;
    }


    public boolean execute(String[] arguments, BufferedReader in) {
        args = arguments;
        return false;
    }
    public boolean valid(String[] args) {
        this.args = args;
        if (args.length!=0){
            System.out.println("команда 'exit' должна быть без аргументов");
            return false;
        }
        else {
            return true;
        }
    }
}
