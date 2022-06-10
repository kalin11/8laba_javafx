package cmd;

import entity.Movie;
import java.io.Serializable;
import java.util.List;

public class Cmd implements Serializable {
    private String command;
    private String arguments;
    private List<Cmd> commands;
    private String file;
    private Movie movie;
    private String login;
    private String password;

    public Cmd (){};

    public Cmd(String name, String log, String pass){
        command = name;
        login = log;
        password = pass;
    }

    public Cmd(String cmd, String arguments){
        command = cmd;
        this.arguments = arguments;
    }

    public Cmd(String cmd, String args, List<Cmd> commands, String login, String password){
        this.command = cmd;
        this.arguments = args;
        this.commands = commands;
        this.login = login;
        this.password = password;
    }

    public Cmd(String cmd, String args, String login, String password){
        command = cmd;
        arguments = args;
        this.login = login;
        this.password = password;
    }

    public Cmd(String cmd, String args, String file, String login, String password){
        command = cmd;
        arguments = args;
        this.file = file;
        this.login = login;
        this.password = password;
    }

    public Cmd(String cmd){
        command = cmd;
    }

    public Cmd(String cmd, String args, Movie movie, String login, String password){
        command = cmd;
        arguments = args;
        this.movie = movie;
        this.login = login;
        this.password = password;
    }

    public Cmd(String cmd, Movie movie, String login, String password){
        command = cmd;
        this.movie = movie;
        this.login = login;
        this.password = password;
    }

    public String toString(){
        return command + " " + arguments + " " + movie + "\n" + file;
    }

    public String forMovieCmd(){
        return command + " " + movie;
    }

    public String getName(){
        return command;
    }
    public String getArguments(){
        return arguments;
    }
    public Movie getMovie(){
        return movie;
    }
    public String getFile(){
        return file;
    }

    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setMovie(Movie movie){
        this.movie = movie;
    }

    public void setArguments(String args){
        this.arguments = args;
    }

    public List<Cmd> getCommands(){
        return commands;
    }
}
