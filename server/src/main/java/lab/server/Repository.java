package lab.server;

import cmd.Cmd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Repository {
    private static final String url = "url";
    private static final String username = "usename";
    private static final String password = "password";
    private static final String driver = "org.postgresql.Driver";
    Cmd cmd;
    String movie;
    Connection connection;
    //тут происходит уже подключение к БД, кидаются запросы
    //потом при успешном запросе в БД нам нужно изменить коллкцию в памяти

    public Repository(Cmd cmd){
        this.cmd = cmd;
    }
    public Repository(Cmd cmd, String movie){
        this.cmd = cmd;
        this.movie = movie;
    }


}
