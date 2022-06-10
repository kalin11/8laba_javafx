package command.tasksCommands.with_arguments;

import cmd.Cmd;
import command.parsing.CommandWithoutMovie;
import visitor.Visitor;

import java.io.BufferedReader;
import java.util.LinkedList;

public class RemoveCommand implements CommandWithoutMovie {

    private String[] args = null;

    private LinkedList list = null;

    public String toString(){
        return "remove_by_id id - удалить элемент из коллекции по его id";
    }


    
    public Object accept(Visitor v) {
        return null;
    }

    @Override
    public Object accept(Visitor v, Cmd cmd) {
        return v.visit(this, cmd);
    }

    public String[] getArgs(){
        return args;
    }

    
    public boolean execute(String[] arguments, BufferedReader in) {

        args = arguments;
            try{
                long x = Long.parseLong(arguments[0]);
            }catch (NumberFormatException e){
                System.out.println("тип аргумента неправильный");
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.print("");
            }
        return true;
    }
    public boolean valid(String[] args) {
        this.args = args;
        if (args.length==0){
            System.out.println("вы не ввели аргументы");
            return false;
        }
        else if (args.length > 1){
            System.out.println("вы ввели слишком много аругментов");
            return false;
        }
        else {
            try{
                long x = Long.parseLong(args[0]);
            }catch (NumberFormatException e){
                System.out.println("тип аргумента неправильный");
                return false;
            }
            return true;
        }

    }
}
