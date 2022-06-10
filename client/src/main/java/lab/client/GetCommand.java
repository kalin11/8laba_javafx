package lab.client;

import cmd.Cmd;
import command.parsing.ObjectParser;
import entity.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.*;

public class GetCommand {
    private static final String EXECUTE_SCRIPT_STRING = "execute_script";
    private String login;
    private String password;
    private String rec = "";
    private boolean isEmpty = false;

    public String getRec(){
        return rec;
    }

    public boolean isEmpty(){
        return isEmpty;
    }

    public void getCmdAndSendToTheServer(SocketChannel client, String[] cmdWithArgs, String login, String password) throws IOException {
        List<Cmd> cmds = new ArrayList<>();
        SendDataToServer dataToServer = new SendDataToServer();
        if (cmdWithArgs[0].equals("exit")){
            Cmd cmd = new Cmd(cmdWithArgs[0]);
            dataToServer.sendToTheServer(client, cmd);
            System.exit(0);
        }
        else if (cmdWithArgs[0].isEmpty()){}
        else if (cmdWithArgs.length == 1){
            if (cmdWithArgs[0].equals("add") || cmdWithArgs[0].equals("remove_lower") || cmdWithArgs[0].equals("remove_greater")){
                Cmd cmd = new Cmd(cmdWithArgs[0], new ObjectParser().parseObject(new BufferedReader(new InputStreamReader(System.in))), login, password);
                dataToServer.sendToTheServer(client, cmd);
            }
            else {
                Cmd cmd = new Cmd(cmdWithArgs[0], login, password);
                dataToServer.sendToTheServer(client, cmd);
            }
        }
        else if (cmdWithArgs.length == 2){
            if (cmdWithArgs[0].equals("update")){
                Cmd cmd = new Cmd(cmdWithArgs[0], cmdWithArgs[1], new ObjectParser().parseObject(new BufferedReader(new InputStreamReader(System.in))), login, password);
                cmd.getMovie().setId(Long.parseLong(cmdWithArgs[1]));
                dataToServer.sendToTheServer(client, cmd);
            }
            else if (cmdWithArgs[0].equals(EXECUTE_SCRIPT_STRING)){
                try{
                    String commands = "";
                    HashSet<String> pathes = new HashSet<>();
                    pathes.add(cmdWithArgs[1]);
                    List<String> f = Files.readAllLines(Paths.get(cmdWithArgs[1]));
                    for (int i = 0; i < f.size(); i++){
                        String line = f.get(i);
                        if (line.toLowerCase(Locale.ROOT).startsWith(EXECUTE_SCRIPT_STRING)){
                            String filePath = line.substring(EXECUTE_SCRIPT_STRING.length()).trim();
                            if (pathes.contains(filePath)){
                                f.remove(i);
                                rec = "цикл";
                                System.out.println("вы пытаетесь зациклить скрипт");
                                continue;
                            }
                            else {
                                pathes.add(filePath);
                                List<String> ftmp = Files.readAllLines(Paths.get(filePath));
                                f.addAll(i+1, ftmp);
                                f.remove(i);
                            }
                        }
                    }
                    try{
                        f.removeIf(s -> s.equals(""));
                        for (String s : f){
                            commands += s + "!";
//                            cmdsList.add(s);
                        }
                        String[] cmdsForList = commands.split("!");
                        for (String string : cmdsForList){
                           cmds.add(getCommandFromFile(string, login));
                        }

                    }catch (StringIndexOutOfBoundsException e){
                        System.out.print("");
                    }
                    if (rec.equals("цикл")){

                    }
                    else {
                        //ну вот сюда получается надо лист с командами сделать и отправлять его уже
                        for (Cmd cmd : cmds){
                            System.out.println(cmd);
                        }
                        System.out.println(commands);
//                        Cmd cmd = new Cmd(cmdWithArgs[0], cmdWithArgs[1], commands, login, password);
                        Cmd cmd = new Cmd(cmdWithArgs[0], cmdWithArgs[1], cmds, login, password);
                        if (cmds.get(0).getName() == null){
                            isEmpty = true;
                        }
                        else {
                            dataToServer.sendToTheServer(client, cmd);
                            isEmpty = false;
                        }
                    }



                }catch (NoSuchFileException e){
                    System.out.print("");
                    return;
                }
            } else {
                Cmd cmd = new Cmd(cmdWithArgs[0], cmdWithArgs[1], login, password);
                dataToServer.sendToTheServer(client, cmd);
            }
        }
    }

    public void getCmdAndSendToTheServer(Socket client, String[] cmdWithArgs, String login, String password, Movie movie) throws IOException {
        List<Cmd> cmds = new ArrayList<>();
        SendDataToServer dataToServer = new SendDataToServer();
        if (cmdWithArgs[0].equals("exit")){
            Cmd cmd = new Cmd(cmdWithArgs[0]);
            dataToServer.sendToTheServer(client, cmd);
            System.exit(0);
        }
        else if (cmdWithArgs[0].isEmpty()){}
        else if (cmdWithArgs.length == 1){
            if (cmdWithArgs[0].equals("add") || cmdWithArgs[0].equals("remove_lower") || cmdWithArgs[0].equals("remove_greater")){
//                Cmd cmd = new Cmd(cmdWithArgs[0], new ObjectParser().parseObject(new BufferedReader(new InputStreamReader(System.in))), login, password);
                Cmd cmd = new Cmd(cmdWithArgs[0], movie, login, password);
                dataToServer.sendToTheServer(client, cmd);
            }
            else {
                Cmd cmd = new Cmd(cmdWithArgs[0], login, password);
                dataToServer.sendToTheServer(client, cmd);
            }
        }
        else if (cmdWithArgs.length == 2){
            if (cmdWithArgs[0].equals("update")){
                Cmd cmd = new Cmd(cmdWithArgs[0], cmdWithArgs[1], movie, login, password);
                System.out.println("GetCommand check - " + movie.getOperator().getBirthday());
                cmd.getMovie().setId(Long.parseLong(cmdWithArgs[1]));
                dataToServer.sendToTheServer(client, cmd);
            }
            else if (cmdWithArgs[0].equals(EXECUTE_SCRIPT_STRING)){
                try{
                    String commands = "";
                    HashSet<String> pathes = new HashSet<>();
                    pathes.add(cmdWithArgs[1]);
                    List<String> f = Files.readAllLines(Paths.get(cmdWithArgs[1]));
                    for (int i = 0; i < f.size(); i++){
                        String line = f.get(i);
                        if (line.toLowerCase(Locale.ROOT).startsWith(EXECUTE_SCRIPT_STRING)){
                            String filePath = line.substring(EXECUTE_SCRIPT_STRING.length()).trim();
                            if (pathes.contains(filePath)){
                                f.remove(i);
                                rec = "цикл";
                                System.out.println("вы пытаетесь зациклить скрипт");
                                continue;
                            }
                            else {
                                pathes.add(filePath);
                                List<String> ftmp = Files.readAllLines(Paths.get(filePath));
                                f.addAll(i+1, ftmp);
                                f.remove(i);
                            }
                        }
                    }
                    try{
                        f.removeIf(s -> s.equals(""));
                        for (String s : f){
                            commands += s + "!";
//                            cmdsList.add(s);
                        }
                        String[] cmdsForList = commands.split("!");
                        for (String string : cmdsForList){
                            cmds.add(getCommandFromFile(string, login));
                        }

                    }catch (StringIndexOutOfBoundsException e){
                        System.out.print("");
                    }
                    if (rec.equals("цикл")){

                    }
                    else {
                        //ну вот сюда получается надо лист с командами сделать и отправлять его уже
                        for (Cmd cmd : cmds){
                            System.out.println(cmd);
                        }
                        System.out.println(commands);
//                        Cmd cmd = new Cmd(cmdWithArgs[0], cmdWithArgs[1], commands, login, password);
                        Cmd cmd = new Cmd(cmdWithArgs[0], cmdWithArgs[1], cmds, login, password);
                        if (cmds.get(0).getName() == null){
                            isEmpty = true;
                        }
                        else {
                            dataToServer.sendToTheServer(client, cmd);
                            isEmpty = false;
                        }
                    }



                }catch (NoSuchFileException e){
                    System.out.print("");
                    return;
                }
            } else {
                Cmd cmd = new Cmd(cmdWithArgs[0], cmdWithArgs[1], login, password);
                dataToServer.sendToTheServer(client, cmd);
            }
        }
    }

    public Cmd getCommandFromFile(String command, String login){
        Person person = new Person("Jora", ZonedDateTime.now(), 98.9F, Country.INDIA);
        Movie movie = new Movie(1, "Vasya", new Coordinates(1,-1000000000), new Date(), 4, 99L, MovieGenre.ACTION, MpaaRating.R, person);
        HashSet<String> CmdLength1 = new HashSet<String>();
        CmdLength1.add("help");
        CmdLength1.add("info");
        CmdLength1.add("show");
        CmdLength1.add("clear");
        CmdLength1.add("exit");
        CmdLength1.add("head");
        CmdLength1.add("average_of_length");
        CmdLength1.add("print_unique_oscars_count");
        HashSet<String> CmdLength2 = new HashSet<String>();
        CmdLength2.add("add");
        CmdLength2.add("remove_by_id");
        CmdLength2.add("remove_lower");
        CmdLength2.add("remove_greater");
        CmdLength2.add("count_by_genre");
        HashSet<String> CmdLength3 = new HashSet<String>();
        CmdLength3.add("update");
        String[] withoutArgs = command.split(" ");
        String[] withOneArg = command.split(" ",2);
//        System.out.println(Arrays.toString(withOneArg));
        String[] withTwoArg = command.split(" ", 3);
//        System.out.println(Arrays.toString(withTwoArg));
        if (CmdLength1.contains(withoutArgs[0])){
            Cmd cmd = new Cmd(withoutArgs[0]);
            cmd.setLogin(login);
            return cmd;
        }
        else if (CmdLength2.contains(withOneArg[0])){
            //с одним аргументом
            if (withOneArg[0].equals("remove_by_id") || withOneArg[0].equals("count_by_genre")){
                Cmd cmd = new Cmd(withOneArg[0]);
                cmd.setArguments(withOneArg[1]);
                cmd.setLogin(login);
                return cmd;
            }
            else {
                Cmd cmd = new Cmd(withOneArg[0]);
                cmd.setLogin(login);
                cmd.setMovie(movie.getMoveFromString(withOneArg[1]));
                return cmd;
            }
        }
        else if (CmdLength3.contains(withTwoArg[0])){
            Cmd cmd = new Cmd(withTwoArg[0]);
            cmd.setLogin(login);
            cmd.setArguments(withTwoArg[1]);
            cmd.setMovie(movie.getMoveFromString(withTwoArg[2]));
            return cmd;
        }
        return new Cmd();
    }
}
