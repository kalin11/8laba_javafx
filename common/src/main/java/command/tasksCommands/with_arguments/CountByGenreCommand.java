package command.tasksCommands.with_arguments;

import cmd.Cmd;
import command.CommandName;
import command.parsing.CommandWithoutMovie;
import entity.MovieGenre;
import visitor.Visitor;

import java.io.BufferedReader;
import java.util.Locale;

public class CountByGenreCommand implements CommandWithoutMovie {

    private String[] args = null;

    public String toString(){
        return "count_by_genre genre - вывести количество элементов, значение поля genre которых равно заданному";
    }

    public String[] getArgs(){
        return args;
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
            try{
                CommandName.valueOf(CommandName.class, arguments[0]);
            }catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                System.out.print("");
            }
        return true;
    }
    public boolean valid(String[] args) {
        this.args = args;
        try {
            if (args.length == 1) {
                MovieGenre movieGenre = MovieGenre.valueOf(args[0].toUpperCase(Locale.ROOT));
                return true;
            } else if (args.length == 0) {
                System.out.println("вы ввели команду без аргумента, что считать? вы можете ввести 'ACTION, DRAMA, HORROR, SCIENCE_FICTION'");
                return false;
            } else {
                System.out.println("некорректный ввод параметра");
                return false;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("некорректный тип параметра, попробуйте заново, вы можете ввести 'ACTION, DRAMA, HORROR, SCIENCE_FICTION'");
            return false;
        }
    }
}
