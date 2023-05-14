package lab.server;

import DB.DataBase.GetCollectionFromDB;
import collection.LinkedCollection;

import java.net.BindException;
import java.nio.channels.UnresolvedAddressException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws BindException, SQLException, ClassNotFoundException {
        try {
            String host = "localhost";
            int port = 16000;
            System.out.println("Сервер запущен!");
            GetCollectionFromDB getCollection = new GetCollectionFromDB();
            LinkedCollection collection = getCollection.getCollection();
            System.out.println(collection.print());
            ServerController controller = new ServerController();
            controller.acceptConnection(collection, host, port);
        }catch (BindException e){
            System.out.println("адрес уже заиспользован");
        }
    }

}
