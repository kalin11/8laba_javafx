package lab.server;

import cmd.Cmd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Repository {
//    private static final String url = "jdbc:postgresql://localhost:63333/studs";
    private static final String url = "jdbc:postgresql://pg:5432/studs";
    private static final String username = "s336805";
    private static final String password = "ipb588";
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



//    public void sendStatementToDB() throws SQLException, ClassNotFoundException {
//        switch (cmd.getName()){
//            case "add":
//                Class.forName(driver);
//                connection = DriverManager.getConnection(url, username, password);
//                cmd.getMovie().getOperator().
//        }
//    }
}
