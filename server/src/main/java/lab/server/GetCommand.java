package lab.server;

import cmd.Cmd;
import collection.LinkedCollection;
import command.CommandName;
import command.parsing.Command;
import command.parsing.CommandLineParser;
import command.tasksCommands.with_arguments.*;
import command.tasksCommands.without_arguments.*;
import entity.Movie;
import visitor.VisitorImpl;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.HashSet;

public class GetCommand implements Runnable{
    private LinkedCollection collection;
    private Cmd cmd;
    private VisitorImpl visitor;
    private SocketChannel channel;
    private String login;
    private String password;
    private SelectionKey key;
    private Object o;

    public GetCommand(LinkedCollection collection, Cmd cmd, VisitorImpl visitor, SocketChannel channel, SelectionKey key){
        this.collection = collection;
        this.cmd = cmd;
        this.visitor = visitor;
        this.channel = channel;
        this.key = key;
    }

    @Override
    public void run() {
        try {
            getCommandAndSendToTheClient();
        }catch (IOException e){
            key.cancel();
            System.out.println("пользователь отключился");
        }
    }

    public void getCommandAndSendToTheClient() throws IOException {
        CommandLineParser parser;
        if (cmd.getArguments() == null){
            if (cmd.getName().equals("add") || cmd.getName().equals("remove_lower") || cmd.getName().equals("remove_greater")){
                Movie movie = cmd.getMovie();
                String[] temp = cmd.toString().split(" ");
                if (temp[1] != null){
                    parser = new CommandLineParser(cmd.getName() + " " + movie.allInfo(),initCommands());
                    //new Thread
                    o = parser.exe(visitor, cmd);

                    System.out.println("sending data to the client");
                }
            }else {
                //help, info, show, head, average_of_length
                //ну короче тут все без аргументов, стоит проверить только clear, тк он работает с БД
                if (cmd.getName().equals("clear")) {
                    parser = new CommandLineParser(cmd.getName(), initCommands());
                    o = parser.exe(visitor, cmd);
//                    sendData = new SendDataToTheClient();
//                    sendData.sendData(channel, o);
//                    sendData.closeOutput();
                }
                    //типа запрос в бд
                    //если ловим SQL исключение, то ничего не делаем
                    //если не ловим, то меняем коллекцию,
                    //карочи, команду clear и все похожие(которые модифицируют коллекцию по нескольким объектам) надо делать с локальной коллекцией
//                }
                else {
                    parser = new CommandLineParser(cmd.getName(), initCommands());
                    o = parser.exe(visitor);
//                    sendData = new SendDataToTheClient();
//                    sendData.sendData(channel, o);
//                    sendData.closeOutput();
                }
                System.out.println("sending data to the client");
            }
        } else {
            //update
            if (cmd.getMovie() != null){
                parser = new CommandLineParser(cmd.getName() + " " + cmd.getArguments() + " " + cmd.getMovie().allInfo(), initCommands());
                System.out.println("server - " + cmd.getMovie().getOperator().getBirthday());
                o = parser.exe(visitor, cmd);
//                sendData = new SendDataToTheClient();
//                sendData.sendData(channel, o);
//                sendData.closeOutput();
                System.out.println("sending data to the client");
            }
            else {
                if (cmd.getName().equals("count_by_genre")){
                    parser = new CommandLineParser(cmd.getName() + " " + cmd.getArguments() + " " + cmd.getFile(), initCommands());
                    o = parser.exe(visitor);
//                    sendData = new SendDataToTheClient();
//                    sendData.sendData(channel, o);
//                    sendData.closeOutput();
                    System.out.println("sending data to the client");
                }
                else {
                    //remove by id
                    if (cmd.getName().equals("remove_by_id")) {
                        parser = new CommandLineParser(cmd.getName() + " " + cmd.getArguments(), initCommands());
                        o = parser.exe(visitor, cmd);
//                    sendData = new SendDataToTheClient();
//                    sendData.sendData(channel, o);
//                    sendData.closeOutput();
                        System.out.println("sending data to the client");
                    }
                    else if (cmd.getName().equals("execute_script")){
//                        parser = new CommandLineParser(cmd.getName() + " " + cmd.getArguments() + " " + cmd.getFile(), initCommands());
                        parser = new CommandLineParser(cmd.getName() + " " + cmd.getArguments() + " " + cmd.getCommands(), initCommands());
                        o = parser.exe(visitor, cmd);
                        System.out.println("скрипт");
                    }
                }
            }
        }
    }

    public Object getResult() {
        return o;
    }




    public static HashMap<CommandName, Command> initCommands() {
        HashMap<CommandName, Command> commands = new HashMap<>();
        commands.put(CommandName.HELP, new HelpCommand());
        commands.put(CommandName.EXIT, new ExitCommand());
        commands.put(CommandName.ADD, new AddCommand());
        commands.put(CommandName.SHOW, new ShowCommand());
        commands.put(CommandName.HEAD, new HeadCommand());
        commands.put(CommandName.INFO, new InfoCommand());
        commands.put(CommandName.AVERAGE_OF_LENGTH, new AverageOfLength());
        commands.put(CommandName.PRINT_UNIQUE_OSCARS_COUNT, new PrintUniqueOscarsCount());
        commands.put(CommandName.UPDATE_ID, new UpdateCommand());
        commands.put(CommandName.REMOVE_BY_ID, new RemoveCommand());
        commands.put(CommandName.COUNT_BY_GENRE, new CountByGenreCommand());
        commands.put(CommandName.REMOVE_GREATER, new RemoveGreaterCommand());
        commands.put(CommandName.CLEAR, new ClearCommand());
        commands.put(CommandName.REMOVE_LOWER, new RemoveLowerCommand());
        commands.put(CommandName.EXECUTE_SCRIPT, new ExecuteScriptCommand());
        return commands;
    }
}
