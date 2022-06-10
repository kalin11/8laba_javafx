package command.parsing;

import cmd.Cmd;
import command.CommandName;
import command.tasksCommands.ParseFromFile;
import entity.*;
import visitor.Visitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CommandLineParser {

    private BufferedReader in;
    private final HashMap<CommandName, Command> commands;
    private String cmdName;
    private boolean shouldClose = false;
    public CommandLineParser(BufferedReader in, HashMap<CommandName, Command> c) {
        this.in = in;
        this.commands = c;
    }
    public CommandLineParser(String cmd, HashMap<CommandName, Command> c) {
        this.cmdName = cmd;
        this.commands = c;
    }
    public Object exe(Visitor visitor) throws IOException {
        String line = cmdName;
        String zagl = "neok";
        if (line == null) {
            shouldClose = true;
            return zagl;
        }
        ///
        ////////////////
        final String[] cmds = line.trim().replaceAll("\\s+", " ").toLowerCase().split(" ");
//        System.out.println(Arrays.toString(cmds));
        if (cmds[0].equals("add") || cmds[0].equals("remove_lower") || cmds[0].equals("remove_greater")) {
            CommandWithMovie c = (CommandWithMovie) commands.get(CommandName.fromString(cmds[0]));
            if (c == null) {
                System.out.println("такой команды нет");
            } else {
                final String[] nado = line.trim().replaceAll("\\s+", " ").split(" ", 2);
                System.out.println(nado[1]);
                String strMov = nado[1];
                return c.accept(visitor, getM(strMov));

            }
        } else if (cmds[0].equals("update")) {
            CommandWithMovie c = (CommandWithMovie) commands.get(CommandName.fromString(cmds[0]));
            if (c == null){
                System.out.println("такой команды нет");
            }
            else {
                final String[] esho = line.trim().replaceAll("\\s+", " ").split(" ", 3);
                System.out.println("хуйхуйхуйхуйхуйхуй");
                System.out.println(Arrays.toString(esho));
                String strMov = esho[2];

                return c.accept(visitor, getM(strMov));
            }
        }else if (cmds[0].equals("execute_script")){
            CommandWithFile c = (CommandWithFile) commands.get(CommandName.fromString(cmds[0]));
            if (c == null){
                System.out.println("такой команды нет");
            }
            else{
                try {
                    String[] what = line.split(" ", 3);

                    String kak = line.substring(26);
                    final String[] ll = what[2].split("!");

//                    System.out.println(Arrays.toString(ll));

                    final String[] com = new String[cmds.length - 2];

                    System.arraycopy(cmds, 2, com, 0, cmds.length - 2);

//                System.out.println(Arrays.toString(com));
//                System.out.println(Arrays.stream(com).map( Object::toString )
//                        .collect( Collectors.joining( "\n" )));
                //это массив с командами скрипта
                return c.accept(visitor, Arrays.stream(ll).map( Object::toString )
                        .collect( Collectors.joining( "!")));
                }catch (StringIndexOutOfBoundsException e){
                    System.out.print("");
                }

            }
        }
        else {
            final String[] commandArguments = new String[cmds.length - 1];
            System.arraycopy(cmds, 1, commandArguments, 0, cmds.length - 1);
            Command c = commands.get(CommandName.fromString(cmds[0]));

                CommandWithoutMovie cw = (CommandWithoutMovie) c;
                if (c == null) {
                    System.out.println("Такой команды нет");
                } else {
                    if (c.execute(commandArguments, in)) {
                        return cw.accept(visitor);

                    } else {
                        shouldClose = true;
                    }
                }
            return "";
        }
        return "";
    }

    public Object exe(Visitor visitor, Cmd cmd) throws IOException{
        String line = cmdName;
        String zagl = "neok";
        if (line == null) {
            shouldClose = true;
            return zagl;
        }
        final String[] cmds = line.trim().replaceAll("\\s+", " ").toLowerCase().split(" ");
        if (cmds[0].equals("add") || cmds[0].equals("remove_lower") || cmds[0].equals("remove_greater")) {
            CommandWithMovie c = (CommandWithMovie) commands.get(CommandName.fromString(cmds[0]));
            if (c == null) {
                System.out.println("такой команды нет");
            } else {
                final String[] nado = line.trim().replaceAll("\\s+", " ").split(" ", 2);
                String strMov = nado[1];
                return c.accept(visitor, getM(strMov), cmd);
                //todo сделать иначе

            }
        } else if (cmds[0].equals("update")) {
            CommandWithMovie c = (CommandWithMovie) commands.get(CommandName.fromString(cmds[0]));
            if (c == null) {
                System.out.println("такой команды нет");
            } else {
                final String[] esho = line.trim().replaceAll("\\s+", " ").split(" ", 3);
                String strMov = esho[2];
                return c.accept(visitor, getM(strMov), cmd);
                //todo сделать иначе
            }
        }else if (cmds[0].equals("execute_script")){
            String res = "";
            for (Cmd com : cmd.getCommands()){
                if (com.getName().equals("add") || com.getName().equals("remove_lower") || com.getName().equals("remove_greater")){
                    CommandWithMovie commandWithMovie = (CommandWithMovie) commands.get(CommandName.fromString(com.getName()));
                    if (commandWithMovie == null){
                        System.out.println("такой команды нет");
                    }
                    else {
                        res += commandWithMovie.accept(visitor, com.getMovie(), com) + "\n";
                    }
                }
                else if (com.getName().equals("update")){
                    CommandWithMovie commandWithMovie = (CommandWithMovie) commands.get(CommandName.fromString(com.getName()));
                    if (commandWithMovie == null){
                        System.out.println("такой команды нет");
                    }
                    else {
                        res += commandWithMovie.accept(visitor, com.getMovie(), com) + "\n";
                    }
                }
                else {
                    Command c = commands.get(CommandName.fromString(com.getName()));
                    CommandWithoutMovie cw = (CommandWithoutMovie) c;
                    if (c == null){
                        System.out.println("такой команды нет");
                    }
                    else {
                        if (com.getArguments() == null){
                            if (cw.execute(new String[0], in)){
                                res += cw.accept(visitor, com) + "\n";
                            }
                        }
                        else {
                            if (cw.execute(com.getArguments().split(" "), in)){
                                res += cw.accept(visitor, com) + "\n";
                            }
                        }
//                        String[] args = com.getArguments().split(" ");
//                        if (args == null){
//                            if (cw.execute(new String[0], in)){
//                                return cw.accept(visitor, com);
//                            }
//                        }
//                        else {
//                            if (cw.execute(com.getArguments().split(" "), in)) {
//                                return cw.accept(visitor, com);
//                            }
//                        }
                    }
                }
            }
            return res;
        }
//        }else if (cmds[0].equals("execute_script")){
//            CommandWithFile c = (CommandWithFile) commands.get(CommandName.fromString(cmds[0]));
//            if (c == null){
//                System.out.println("такой команды нет");
//            }
//            else{
//                try {
//                    String[] what = line.split(" ", 3);
//
//                    String kak = line.substring(26);
//                    final String[] ll = what[2].split("!");
//
////                    System.out.println(Arrays.toString(ll));
//
//                    final String[] com = new String[cmds.length - 2];
//
//                    System.arraycopy(cmds, 2, com, 0, cmds.length - 2);
//
////                System.out.println(Arrays.toString(com));
////                System.out.println(Arrays.stream(com).map( Object::toString )
////                        .collect( Collectors.joining( "\n" )));
//                    //это массив с командами скрипта
//                    return c.accept(visitor, Arrays.stream(ll).map( Object::toString )
//                            .collect( Collectors.joining( "!")));
//                    //todo сделать иначе
//                }catch (StringIndexOutOfBoundsException e){
//                    System.out.print("");
//                }
//
//            }
//        }
        else {
            //remove_by_id
            final String[] commandArguments = new String[cmds.length - 1];
            System.arraycopy(cmds, 1, commandArguments, 0, cmds.length - 1);
            Command c = commands.get(CommandName.fromString(cmds[0]));

            CommandWithoutMovie cw = (CommandWithoutMovie) c;
            if (c == null) {
                System.out.println("Такой команды нет");
            } else {
                if (c.execute(commandArguments, in)) {
                    return cw.accept(visitor, cmd);

                } else {
                    shouldClose = true;
                }
            }
            return "";
        }
        return "";
    }









    public Movie getM(String f) {
        String[] fields = f.split(",");
        if (fields.length != 13) {
            System.out.print("");
        }
        else {
            ParseFromFile parse = new ParseFromFile();
            Person person = new Person("Jora", ZonedDateTime.now(), 98.9F, Country.INDIA);
            Movie movie = new Movie(1, "Vasya", new Coordinates(1,-1000000000), new Date(), 4, 99L, MovieGenre.ACTION, MpaaRating.R, person);
            try {
                movie.setId(parse.parseID(fields[0]));
                movie.setName(parse.parseString(fields[1]));
                if (parse.parseY(fields[3]) > 884) {
                    System.out.print("");
                } else {
                    movie.setCoordinates(parse.parseX(fields[2]), parse.parseY(fields[3]));
                }
                movie.setCreationDate(parse.parseDate(fields[4]));
                movie.setOscarsCount(parse.parseOscarsCount(fields[5]));
                movie.setLength(parse.parseLength(fields[6]));
                movie.setGenre(MovieGenre.valueOf(fields[7]));
                movie.setMpaaRating(MpaaRating.valueOf(fields[8]));
                Person p = new Person(parse.parseString(fields[9]),
                        parse.parseZND(fields[10]),
                        parse.parseWeight(fields[11]),
                        Country.valueOf(fields[12]));
                movie.setOperator(p);
                return movie;
            } catch (ParseException e) {
                System.out.println("не удалось спарсить");
            }

        }
        return null;
    }
}
