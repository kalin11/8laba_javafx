package visitor;

import cmd.Cmd;
import collection.LinkedCollection;
import command.CommandName;
import command.parsing.Command;
import command.tasksCommands.ParseFromFile;
import command.tasksCommands.with_arguments.*;
import command.tasksCommands.without_arguments.*;
import entity.*;
//import lab5.entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.*;

public class VisitorImpl implements Visitor, Serializable {

    private final LinkedCollection collection;
    private final HashMap<CommandName, Command> commands;
//    private HashSet<String> paths = new HashSet<>();
    private BufferedReader in;

    public VisitorImpl(LinkedCollection collection, HashMap<CommandName, Command> commands) {
        this.collection = collection;
        this.commands = commands;
    }

    
    public Object visit(AddCommand c, Movie movie, Cmd cmd) {
        collection.add(movie, cmd);
        return collection;
    }


    
    public String visit(HelpCommand c) {
        String str = "";
        for (Map.Entry<CommandName, Command> entry : commands.entrySet()) {
            str += entry.getValue() + "\n";
        }
        return str;
    }

    public String visit(HelpCommand c, Cmd cmd) {
        String str = "";
        for (Map.Entry<CommandName, Command> entry : commands.entrySet()) {
            str += entry.getValue() + "\n";
        }
        return str;
    



    public String visit(ShowCommand c) {
        return collection.print();
    }

    public String visit(ShowCommand c, Cmd cmd){
        return collection.print();
    }

    public Object visit(HeadCommand c) {
        return collection.printFirstElement();
    }

    public Object visit(HeadCommand c, Cmd cmd){
        return collection.printFirstElement();
    }

    public Object visit(ClearCommand c, Cmd cmd) {
        return collection.clear(cmd);
    }

    public String visit(InfoCommand c) {
        return collection.info();
    }
    public String visit(InfoCommand c, Cmd cmd) {
        return collection.info();
    }

    public Object visit(AverageOfLength c) {
        return collection.averageOfLength();
    }

    public Object visit(AverageOfLength c, Cmd cmd) {
        return collection.averageOfLength();
    }

    public Object visit(PrintUniqueOscarsCount c) {
        return collection.printMoviesWithUniqueOscarsCount();
    }

    public Object visit(PrintUniqueOscarsCount c, Cmd cmd) {
        return collection.printMoviesWithUniqueOscarsCount();
    }

    //
    public Object visit(UpdateCommand c, Movie movie, Cmd cmd) {
        if (collection.isEmpty()) {
            return "коллекция пустая, чего обновлять?";
        } else {
            return collection.updateElement(movie.getId(), movie, cmd);

        }
    }

    public Object visit(RemoveCommand c, Cmd cmd) {
        if (collection.isEmpty()){
            return "коллекция пустая";
        }
        else {
            try {
                return collection.remove(Long.parseLong(c.getArgs()[0]), cmd);
            }catch (NumberFormatException e){
                return "не удалось спарсить id";
            }
            catch (ArrayIndexOutOfBoundsException e){
                return "неть id";
            }
        }
    }

    public String visit(CountByGenreCommand c) {
        try {
            return collection.countByGenre(MovieGenre.valueOf(c.getArgs()[0].toUpperCase(Locale.ROOT)));
        }catch (IllegalArgumentException e){
            return "ну удалось спарсить жанр";
        }
    }

    public String visit(CountByGenreCommand c, Cmd cmd) {
        try {
            return collection.countByGenre(MovieGenre.valueOf(c.getArgs()[0].toUpperCase(Locale.ROOT)));
        }catch (IllegalArgumentException e){
            return "ну удалось спарсить жанр";
        }
    }

    public Object visit(RemoveGreaterCommand c, Movie movie, Cmd cmd) {
        if (collection.isEmpty()) {
            return "коллекция пустая";
        } else {
            return collection.removeG(movie, cmd);
        }
    }

    public Object visit(RemoveLowerCommand c, Movie movie, Cmd cmd) {
        if (collection.isEmpty()) {
            return "коллекция пустая";
        } else {
            return collection.removeL(movie, cmd);
        }
    }

    
    public Object visit(ExecuteScriptCommand c) {
        return null;
    }

    
    public Object visit(ExecuteScriptCommand c, String str) {
        return null;
    }

}
