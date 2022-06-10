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
    }


//        System.out.println(
//
//                "show - вывести все элементы коллекции\n" +
//                        "info - вывести информацию о коллекции\n" +
//                        "add {element} - добавить новый элемент в коллекцию\n" +
//                        "update id {element} - обновить значение элемента коллекции, id которого равен заданному\n" +
//                        "remove_by_id id - удалить элемент из коллекции по его id\n" +
//                        "clear - удалить элемент из коллекции по его id\n" +
//                        "save - сохранить коллекцию в файл\n" +
//                        "execute_script file_name - считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
//                        "exit - завершить проограмму, без сохранения в файл\n" +
//                        "head - вывести первый элемент коллекции\n" +
//                        "remove_greater {element} - удалить из коллекции все элементы, превыщающие заданный\n" +
//                        "remove_lower {element} - удалить из коллекции все элементы, меньшие,чем заданный\n" +
//                        "average_of_length - вывести среднее значение поля length для всех элементов коллекции\n" +
//                        "count_by_genre genre - вывести количество элементов, значение поля genre которых равно заданному\n" +
//                        "print_unique_oscars_count : вывести уникальные значения поля oscarsCount всех элементов в коллекции\n"
//
//
//        );



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

//    boolean flag = false;

//    public Object visit(ExecuteScriptCommand c) {
//        try {
//            HashSet<String> paths = new HashSet<>();
//            String filename = c.getArgs()[0].toLowerCase(Locale.ROOT);
//            findAllPathForScript(c,paths,filename);
//            if (flag) {
//                return "извините, но вы пытаетесь зациклить скрипт и сломать мою прогу(( укажите другой файл";
//            }
//            else {
//                executeFileCommands(c);
//                paths.add(c.getArgs()[0].toLowerCase(Locale.ROOT));
//            }
//        } catch (ArrayIndexOutOfBoundsException e) {
//            System.out.print("");
//        }catch (IOException e){
//            System.out.println("недостаточно прав(");
//        }
//        return "\n";
//    }

//    public Object visit(ExecuteScriptCommand c, String lines){
//        return executeFileCommands(c, lines);
//    }
//
//
//
//    public void findAllPathForScript(ExecuteScriptCommand c, HashSet<String> set, String path) throws IOException {
//        set.add(path.toLowerCase(Locale.ROOT));
//        LinkedHashSet<String> set1 = new LinkedHashSet<>();
//        if (flag){
//            return;
//        }
//        try {
//            List<String> allLines = Files.readAllLines(Paths.get(path));
//            for (String s : allLines) {
//                if (s.contains("execute_script") && s.contains(".txt")) {
//                    String[] line = s.split(" ");
//                    set1.add(line[1].toLowerCase(Locale.ROOT));
//                }
//            }
//            for (String str : set1) {
//                if (!set.contains(str)) {
//                    findAllPathForScript(c, set, str);
//                } else {
//                    flag = true;
//                    return;
//                }
//            }
//        }catch (ConcurrentModificationException e) {
//            System.out.print("");
//        }
//    }
//
////    public void executeFileCommands(ExecuteScriptCommand c){
////        try {
////            List<String> fileCommands = Files.readAllLines(Paths.get(c.getArgs()[0]));
////            fileCommands.removeIf(command -> command.equals(" ") || command.equals(""));
////            boolean flag = false;
////            for (String cmd : fileCommands) {
////                final String[] cmds = cmd.trim().replaceAll("\\s+", " ").split(" ");
////                final String[] commandsArgs = new String[cmds.length - 1];
////                System.arraycopy(cmds, 1, commandsArgs, 0, cmds.length - 1);
////                Command command = commands.get(CommandName.fromString(cmds[0]));
//////
////                switch (Arrays.toString(new String[]{cmds[0]})) {
////                    case "[help]":
////                        HelpCommand helpCommand = new HelpCommand();
////                        helpCommand.execute(new String[0], in);
////                        this.visit(helpCommand);
////                        break;
////                    case "[info]":
////                        InfoCommand infoCommand = new InfoCommand();
////                        infoCommand.execute(new String[0], in);
////                        this.visit(infoCommand);
////                        break;
////                    case "[clear]":
////                        ClearCommand clearCommand = new ClearCommand();
////                        clearCommand.execute(new String[0], in);
////                        this.visit(clearCommand);
////                        break;
////                    case "[show]":
////                        ShowCommand showCommand = new ShowCommand();
////                        showCommand.execute(new String[0], in);
////                        this.visit(showCommand);
////                        break;
////                    case "[remove_by_id]":
////                        RemoveCommand removeCommand = new RemoveCommand();
////                        removeCommand.execute(commandsArgs, in);
////                        this.visit(removeCommand);
////                        break;
////                    case "[head]":
////                        HeadCommand headCommand = new HeadCommand();
////                        headCommand.execute(new String[0], in);
////                        this.visit(headCommand);
////                        break;
////                    case "[exit]":
////                        ExitCommand exitCommand = new ExitCommand();
////                        exitCommand.execute(new String[0], in);
////                        System.exit(0);
////
////                    case "[average_of_length]":
////                        AverageOfLength averageOfLength = new AverageOfLength();
////                        averageOfLength.execute(new String[0], in);
////                        this.visit(averageOfLength);
////
////                        break;
////                    case "[count_by_genre]":
////                        CountByGenreCommand count = new CountByGenreCommand();
////                        count.execute(commandsArgs, in);
////                        this.visit(count);
////
////                        break;
////                    case "[print_unique_oscars_count]":
////                        PrintUniqueOscarsCount print = new PrintUniqueOscarsCount();
////                        print.execute(new String[0], in);
////                        this.visit(print);
////
////                        break;
////                    case "[add]":
////                        try {
////                            String f = cmd.substring(4);
////                            if (getFields(f) == null) {
////                                return;
////                            } else {
////                                collection.add(getFields(f));
////                                System.out.println(collection);
////                            }
////                        } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
////                            System.out.println("не удалось запарсить");
////                            return;
////                        } catch (IllegalArgumentException e) {
////                            System.out.println("какое-то Enum значение не удалось запарсить");
////                            return;
////                        }
////                        break;
////                    case "[remove_greater]":
////                        try {
////                            String f = cmd.substring(15);
////                            ;
////                            if (getFields(f) == null || getFields(f).getCoordinates().getY() == 2) {
////                                System.out.println("проверьте значения Movie у команд 'remove_greater'");
////                                return;
////                            } else {
////                                collection.removeG(getFields(f));
////                                System.out.println(collection);
////                            }
////                        } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
////                            System.out.println("не удалось запарсить");
////                            return;
////                        } catch (IllegalArgumentException e) {
////                            System.out.println("какое-то Enum значение не удалось запарсить");
////                            return;
////                        }
////
////                        break;
////                    case "[remove_lower]":
////                        try {
////                            String f = cmd.substring(13);
////                            if (getFields(f) == null || getFields(f).getCoordinates().getY() == 2) {
////                                System.out.println("проверьте значения Movie у команд 'remove_lower'");
////                                return;
////                            } else {
////                                collection.removeL(getFields(f));
////                                System.out.println(collection);
////                            }
////                        } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
////                            System.out.println("не удалось запарсить");
////                        } catch (IllegalArgumentException e) {
////                            System.out.println("какой-то Enum значение не удалось запарсить");
////                            return;
////                        }
////                        break;
////                    case "[update]":
//////                    System.out.println(cmd);
////                        String idAndMov = cmd.substring(7);
//////                    System.out.println(idAndMov);
////                        String fileds = idAndMov.substring(idAndMov.indexOf(" ") + 1);
//////                    System.out.println(fileds);
////                        String id = cmd.replace(fileds, "").substring(7).trim();
//////                    System.out.println(id);
////                        try {
////                            String f = fileds;
//////                        System.out.println(f);
//////                        System.out.println(getFields(f).getCoordinates().getX());
//////                        System.out.println(getFields(f).getOscarsCount());
//////                        System.out.println(getFields(f).getCoordinates());
////                            if (getFields(f) == null || getFields(f).getCoordinates().getY() == -1000000000) {
////                                System.out.println("проверьте значения Movie у команд 'update'");
////                                return;
////                            } else {
////                                long ID = Long.parseLong(id);
////                                Movie movie = getFields(f);
////                                collection.updateMov(ID, getFields(f));
//////                            collection.getMovie().setCreationDate(getFields(f).getCreationDate());
////                            }
////                        } catch (NumberFormatException e) {
////                            System.out.print("");
////                        }
////                        break;
////                    case "[execute_script]":
////                        ExecuteScriptCommand scriptCommand = new ExecuteScriptCommand();
//////                    System.out.println(Arrays.toString(commandsArgs));
////                        String[] args = new String[commandsArgs.length];
////                        for (int i = 0; i < commandsArgs.length; i++) {
////                            args[i] = commandsArgs[i].toLowerCase(Locale.ROOT);
////                        }
////                        scriptCommand.execute(args, in);
////                        this.visit(scriptCommand);
////                        break;
////                    default:
////                        flag = true;
////                        break;
////                }
////            }
////            if (flag){
////                System.out.println("файл не содержит команд/неверный формат команд");
////            }
////        } catch (IOException e) {
////            System.out.print("");
////        }
////
////    }
////
////    public Object executeFileCommands(ExecuteScriptCommand c, String file) {
////        String result = "";
//////        System.out.println(file);
////        String[] lines = file.split("!");
//////        System.out.println(Arrays.toString(lines));
////        List<String> fileCommands = new LinkedList<>(Arrays.asList(lines));
//////        System.out.println(fileCommands);
////        fileCommands.removeIf(command -> command.equals(" ") || command.equals(""));
////        boolean flag = false;
////        for (String cmd : fileCommands) {
////            final String[] cmds = cmd.trim().replaceAll("\\s+", " ").split(" ");
//////            System.out.println(Arrays.toString(cmds));
////            final String[] commandsArgs = new String[cmds.length - 1];
////            System.arraycopy(cmds, 1, commandsArgs, 0, cmds.length - 1);
////            Command command = commands.get(CommandName.fromString(cmds[0]));
//////
////            switch (Arrays.toString(new String[]{cmds[0]})) {
////                case "[help]":
////                    HelpCommand helpCommand = new HelpCommand();
////                    helpCommand.execute(new String[0], in);
////                    result += this.visit(helpCommand) + "\n";
////                    break;
////                case "[info]":
////                    InfoCommand infoCommand = new InfoCommand();
////                    infoCommand.execute(new String[0], in);
////                    result += this.visit(infoCommand) + "\n";
////                    break;
////                case "[clear]":
////                    ClearCommand clearCommand = new ClearCommand();
////                    clearCommand.execute(new String[0], in);
////                    result += this.visit(clearCommand) + "\n";
////                    break;
////                case "[show]":
////                    ShowCommand showCommand = new ShowCommand();
////                    showCommand.execute(new String[0], in);
////                    result += this.visit(showCommand) + "\n";
////                    break;
////                case "[remove_by_id]":
////                    RemoveCommand removeCommand = new RemoveCommand();
////                    removeCommand.execute(commandsArgs, in);
////                    result += this.visit(removeCommand) + "\n";
////                    break;
////                case "[head]":
////                    HeadCommand headCommand = new HeadCommand();
////                    headCommand.execute(new String[0], in);
////                    result += this.visit(headCommand) + "\n";
////                    break;
////                case "[exit]":
////                    ExitCommand exitCommand = new ExitCommand();
//////                        exitCommand.execute(new String[0], in);
//////                        System.exit(0);
////
////                case "[average_of_length]":
////                    AverageOfLength averageOfLength = new AverageOfLength();
////                    averageOfLength.execute(new String[0], in);
////                    result += this.visit(averageOfLength) + "\n";
////
////                    break;
////                case "[count_by_genre]":
////                    CountByGenreCommand count = new CountByGenreCommand();
////                    count.execute(commandsArgs, in);
////                    result += this.visit(count) + "\n";
////
////                    break;
////                case "[print_unique_oscars_count]":
////                    PrintUniqueOscarsCount print = new PrintUniqueOscarsCount();
////                    print.execute(new String[0], in);
////                    result += this.visit(print) + "\n";
////
////                    break;
////                case "[add]":
////                    try {
////                        String f = cmd.substring(4);
////                        if (getFields(f) == null) {
////                            continue;
////                        } else {
////                            collection.add(getFields(f));
////                            System.out.println(getFields(f));
////                            result += collection + "\n";
////                        }
////                    } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
////                        System.out.println("не удалось запарсить");
////                        continue;
////                    } catch (IllegalArgumentException e) {
////                        System.out.println("какое-то Enum значение не удалось запарсить");
////                        continue;
////                    }
////                    break;
////                case "[remove_greater]":
////                    try {
////                        String f = cmd.substring(15);
////                        if (getFields(f) == null || getFields(f).getCoordinates().getY() == 2) {
////                            System.out.println("проверьте значения Movie у команд 'remove_greater'");
////                            continue;
////                        } else {
////                            collection.removeG(getFields(f));
////                            System.out.println(collection);
////                            result += collection + "\n";
////                        }
////                    } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
////                        System.out.println("не удалось запарсить");
////                        continue;
////                    } catch (IllegalArgumentException e) {
////                        System.out.println("какое-то Enum значение не удалось запарсить");
////                        continue;
////                    }
////
////                    break;
////                case "[remove_lower]":
////                    try {
////                        String f = cmd.substring(13);
////                        if (getFields(f) == null || getFields(f).getCoordinates().getY() == 2) {
////                            System.out.println("проверьте значения Movie у команд 'remove_lower'");
////                            continue;
////                        } else {
////                            collection.removeL(getFields(f));
////                            System.out.println(collection);
////                            result += collection + "\n";
////                        }
////                    } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
////                        System.out.println("не удалось запарсить");
////                    } catch (IllegalArgumentException e) {
////                        System.out.println("какой-то Enum значение не удалось запарсить");
////                        continue;
////                    }
////                    break;
////                case "[update]":
//////                    System.out.println(cmd);
////                    String idAndMov = cmd.substring(7);
//////                    System.out.println(idAndMov);
////                    String fileds = idAndMov.substring(idAndMov.indexOf(" ") + 1);
//////                    System.out.println(fileds);
////                    String id = cmd.replace(fileds, "").substring(7).trim();
//////                    System.out.println(id);
////                    try {
////                        String f = fileds;
//////                        System.out.println(f);
//////                        System.out.println(getFields(f).getCoordinates().getX());
//////                        System.out.println(getFields(f).getOscarsCount());
//////                        System.out.println(getFields(f).getCoordinates());
////                        if (getFields(f) == null || getFields(f).getCoordinates().getY() == -1000000000) {
////                            System.out.println("проверьте значения Movie у команд 'update'");
////                            continue;
////                        } else {
////                            long ID = Long.parseLong(id);
////                            Movie movie = getFields(f);
////                            collection.updateMov(ID, getFields(f));
////                            result += collection + "\n";
//////                            collection.getMovie().setCreationDate(getFields(f).getCreationDate());
////                        }
////                    } catch (NumberFormatException e) {
////                        System.out.print("");
////                    }
////                    break;
////                case "[execute_script]":
////                    ExecuteScriptCommand scriptCommand = new ExecuteScriptCommand();
//////                    System.out.println(Arrays.toString(commandsArgs));
////                    String[] args = new String[commandsArgs.length];
////                    System.out.println(Arrays.toString(args));
////                    for (int i = 0; i < commandsArgs.length; i++) {
////                        args[i] = commandsArgs[i].toLowerCase(Locale.ROOT);
////                    }
////                    scriptCommand.execute(args, in);
////                    result += this.visit(scriptCommand);
////                    break;
////                default:
////                    flag = true;
////                    break;
////            }
////        }
////        if (flag) {
////            System.out.println("файл не содержит команд/неверный формат команд");
////        }
////        return result;
////
////    }
//
//    public Movie getFields(String f) {
//        String[] fields = f.split(";");
//        if (fields.length != 13) {
//            System.out.print("");
//        }
//        else {
//            ParseFromFile parse = new ParseFromFile();
//            Person person = new Person("Jora", ZonedDateTime.now(), 98.9F, Country.INDIA);
//            Movie movie = new Movie(1, "Vasya", new Coordinates(1,-1000000000), new Date(), 4, 99L, MovieGenre.ACTION, MpaaRating.R, person);
//            try {
//                movie.setId(parse.parseID(fields[0]));
//                movie.setName(parse.parseString(fields[1]));
//                if (parse.parseY(fields[3]) > 884) {
//                    System.out.print("");
//                } else {
//                    movie.setCoordinates(parse.parseX(fields[2]), parse.parseY(fields[3]));
//                }
//                movie.setCreationDate(parse.parseDate(fields[4]));
//                movie.setOscarsCount(parse.parseOscarsCount(fields[5]));
//                movie.setLength(parse.parseLength(fields[6]));
//                movie.setGenre(MovieGenre.valueOf(fields[7]));
//                movie.setMpaaRating(MpaaRating.valueOf(fields[8]));
//                Person p = new Person(parse.parseString(fields[9]),
//                        parse.parseZND(fields[10]),
//                        parse.parseWeight(fields[11]),
//                        Country.valueOf(fields[12]));
//                movie.setOperator(p);
//                return movie;
//            } catch (ParseException e) {
//                System.out.println("не удалось спарсить");
//            }
//
//        }
//        return null;
//    }

}
