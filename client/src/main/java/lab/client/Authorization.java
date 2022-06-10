package lab.client;

import cmd.Cmd;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Locale;
import java.util.Scanner;

public class Authorization {
    private final Scanner scanner = new Scanner(System.in);
    private String login;
    private String password;
    private boolean log_or_reg = false;

    public void setLog_or_reg(boolean x){
        log_or_reg = x;
    }

    public boolean isLog_or_reg(){
        return log_or_reg;
    }

    public String getLogin() {
        return login;
    }
    public String getPassword(){
        return password;
    }

    public void autorize(SocketChannel client) throws IOException {
        while (!log_or_reg) {
            String user = scanner.nextLine();
            if (user.trim().equals("register") || user.trim().equals("login")) {
                System.out.println("введите логин");
                this.login = scanner.nextLine();
                System.out.println("введите пароль");
                this.password = scanner.nextLine();
                Cmd userData = new Cmd(user, this.login, this.password);
                SendDataToServer sendData = new SendDataToServer();
                sendData.sendToTheServer(client, userData);
                log_or_reg = true;
            }
            else System.out.println("сначала надо авторизоваться");
        }
    }

    public void login(Socket socket, String login, String password) throws IOException{
        this.login = login;
        this.password = password;
        Cmd userData = new Cmd("login", this.login, this.password);
        SendDataToServer sendData = new SendDataToServer();
        System.out.println("1 \n");
        System.out.println(socket == null);
        sendData.sendToTheServer(socket, userData);
    }

    public void signup(Socket client, String login, String password) throws IOException{
        this.login = login;
        this.password = password;
        Cmd userData = new Cmd("register", this.login, this.password);
        SendDataToServer sendData = new SendDataToServer();
        sendData.sendToTheServer(client, userData);
    }
}

