package DB.DataBase;

import cmd.Cmd;
import collection.LinkedCollection;
import entity.Movie;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ConnectToDB {
    Connection connection;
    public Connection connectToDB(){
        try {
            Properties properties = new Properties();
            String dbFile = "path to property";
            FileReader fileReader = new FileReader(dbFile);
            properties.load(fileReader);
            String dbDriver = properties.getProperty("db.jdbc.drivers");
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            if (!"".equals(dbDriver) && !"".equals(dbUrl)) {
                Class.forName(dbDriver);
                this.connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            }
        }catch (ClassNotFoundException e){
            System.out.println("не удалось поставить драйвер");
        }catch (SQLException e){
            System.out.println("беды с БД");
        }catch (IOException e){
            System.out.println("файлик с информацией для БД не найден");
        }
        return connection;
    }
    public String autorize(SocketChannel channel, Cmd cmd) throws IOException {
        String reg_ok = "Вы успешно зарагестрированы!";
        String reg_neok = "такой пользователь уже существует";
        String log_ok = "Вы успешно вошли!";
        String log_neok = "такой учетной записи не было найдено";
        PreparedStatement ps;
        ResultSet resultSet;
        if (cmd.getName().equals("register")){
            try {
                int count;
                System.out.println(cmd.getName());
                System.out.println(cmd.getLogin());
                System.out.println(cmd.getPassword());
                System.out.println("connected to DB");
                String sql = "select count(*) from \"Users\" where user_log = ?";
                ps = connectToDB().prepareStatement(sql);
                ps.setString(1, cmd.getLogin());
                ps.execute();
                resultSet = ps.getResultSet();
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                    System.out.println(count);
                    if (count == 0) {
                        String add = "insert into \"Users\" (user_log, password) VALUES (?, ?)";
                        ps = connectToDB().prepareStatement(add);
                        ps.setString(1, cmd.getLogin());
                        ps.setString(2, hashPassword(cmd.getPassword()));
                        ps.execute();
                        return reg_ok;
                    } else {
                        return reg_neok;
                    }

                }
            } catch (SQLException e) {
                System.out.println("проблемы с БД");
            }catch (NullPointerException e){
                return  "проблемы с БД";
            }
        }
        else if (cmd.getName().equals("login")){
            try {
                int count = -1;
                System.out.println(cmd.getName());
                System.out.println("connected to DB");
                String sql = "select * from \"Users\" where user_log = ? and password = ?";
                ps = connectToDB().prepareStatement(sql);
                ps.setString(1, cmd.getLogin());
                ps.setString(2, hashPassword(cmd.getPassword()));
                ps.executeQuery();
                resultSet = ps.getResultSet();
                if (resultSet.next()){
                    String log = resultSet.getString(1);
                    System.out.println(log);
                    String pass = resultSet.getString(2);
                    System.out.println(pass);
                    System.out.println(count);
                    return log_ok;

                }
                else{
                    return log_neok;
                }
            } catch (SQLException e){
                System.out.println("проблемы с БД");
            } catch (NullPointerException e){
                return "проблемы с БД";
            }
        }
        return "";
    }

    public Object sendToDB(Cmd cmd){
        try {
            connectToDB();
            DBSender sender = new DBSender();
            return sender.send(connection, cmd);
        }catch (SQLException e){
            System.out.println("какие-то проблемы с БД");
            e.printStackTrace();
        }
        return "";
    }

    public String hashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] message = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, message);
            String hashText = no.toString(16);
            while (hashText.length() < 32){
                hashText = "0" + hashText;
            }
            return hashText;
        }catch (NoSuchAlgorithmException e){
            System.out.print("");
        }
        return password;
    }

}
