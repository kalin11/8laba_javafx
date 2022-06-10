package lab.client.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lab.client.ClientConnect;
import sun.java2d.cmm.lcms.LcmsServiceProvider;

import javax.management.Notification;
import java.io.IOException;
import java.net.*;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConnectToServer implements Initializable {
    private String login;
    private String password;
    private String host;
    private int port;
    public Socket socket;
    @FXML
    private Button button_ru;
    @FXML
    private Button button_eng;
    @FXML
    private Button button_lva;
    @FXML
    private Button button_nor;
    @FXML
    private Label label_connect;
    @FXML
    private Label label_host;
    @FXML
    private Label label_port;
    @FXML
    private TextField server_host;
    @FXML
    private TextField server_port;
    @FXML
    private Button connect_button;
    private Stage stage;
    private ResourceBundle resourceBundle;

    public ConnectToServer(String host, int port) throws IOException{
        this.host = host;
        this.port = port;
    }

    public ConnectToServer(){

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public Button getConnect_button() {
        return connect_button;
    }

    public TextField getServer_port() {
        return server_port;
    }

    public TextField getServer_host() {
        return server_host;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setResourceBundle(resourceBundle);
        button_eng.setOnAction(this::setEng);
        button_ru.setOnAction(this::setRu);
        button_lva.setOnAction(this::setLv);
        button_nor.setOnAction(this::setNo);
        connect_button.setOnAction( event -> {
            try {
                if (getServer_port().getText().isEmpty() || getServer_host().getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("вы не заполнили поля");
                    alert.show();
                }
                else {
                    socket = new Socket(getServer_host().getText().trim(), Integer.parseInt(getServer_port().getText().trim()));
                    setSocket(socket);
                    SceneController sceneController = new SceneController();
                    sceneController.switchToLoginScene(event, socket, this.resourceBundle);
                }
            }catch (ConnectException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("такого сервера нет(");
                alert.show();
                setSocket(null);
            }catch (NumberFormatException e){
                Alert alert =  new Alert(Alert.AlertType.ERROR);
                alert.setContentText("порт сервера - число типа int");
                alert.show();
            } catch (UnknownHostException e) {
                System.out.println("unknown host");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void switchToLoginScene(ActionEvent event) throws IOException {
//        connect_button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                try {
//                    if (getServer_port().getText().isEmpty() || getServer_host().getText().isEmpty()) {
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setContentText("вы не заполнили поля");
//                        alert.show();
//                    }
//                    else {
//                        socket = new Socket(getServer_host().getText().trim(), Integer.parseInt(getServer_port().getText().trim()));
//                        setSocket(socket);
//                        System.out.println(socket==null);
//                        SceneController sceneController = new SceneController();
//                        sceneController.switchToLoginScene(event, socket);
//                    }
//                }catch (ConnectException e){
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setContentText("такого сервера нет(");
//                    alert.show();
//                    setSocket(null);
//                }catch (NumberFormatException e){
//                    Alert alert =  new Alert(Alert.AlertType.ERROR);
//                    alert.setContentText("порт сервера - число типа int");
//                    alert.show();
//                } catch (UnknownHostException e) {
//                    System.out.println("unknown host");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

    }


//        try {
//            FXMLLoader loader = new FXMLLoader();
//            ResourceBundle resources = ResourceBundle.getBundle("bundles.Locale", new Locale("en"));
//            setResourceBundle(resources);
//            loader.setResources(resources);
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/ConnectToServer.fxml"), resources);
//            Stage stage = (Stage) button_eng.getScene().getWindow();
//            stage.setTitle(loader.getResources().getString("connect.to.server.title"));
//            stage.setScene(new Scene(root));
//        }catch (IOException e){
//
//        }

//        stage.setTitle(resources.getString("connect.to.server.title"));
//        connect_button.setText(resources.getString("key.connect"));
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            ResourceBundle resources = ResourceBundle.getBundle("bundles.Locale", new Locale("RU"));
//            setResourceBundle(resources);
//            loader.setResources(resources);
//            Parent root = FXMLLoader.load(getClass().getResource("/fxml/ConnectToServer.fxml"), resources);
//            Stage stage = (Stage) button_eng.getScene().getWindow();
//            stage.setTitle(loader.getResources().getString("connect.to.server.title"));
//            stage.setScene(new Scene(root));
//        }catch (IOException e){
//
//        }

    public void setResourceBundle(ResourceBundle resourceBundle){
        this.resourceBundle = resourceBundle;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setEng(ActionEvent event){
        setLabels("en");
    }
    public void setRu(ActionEvent event){
        setLabels("RU");
    }
    public void setLv(ActionEvent event){
        setLabels("LV");
    }
    public void setNo(ActionEvent event){
        setLabels("NO");
    }
    public void setLabels(String locale){
        if (locale.equals("NOR")){
            locale = "NO";
        }
        if (locale.equals("LVA")){
            locale = "LV";
        }
        if (locale.equals("ENG")){
            locale = "en";
        }
        ResourceBundle resources = ResourceBundle.getBundle("bundles.Locale", new Locale(locale));
        label_connect.setText(resources.getString("key.connect.label"));
        label_host.setText(resources.getString("key.host.label"));
        label_port.setText(resources.getString("key.port.label"));
        server_host.setPromptText(resources.getString("key.enter.host"));
        server_port.setPromptText(resources.getString("key.enter.port"));
        connect_button.setText(resources.getString("key.connect"));
        stage.setTitle(resources.getString("connect.to.server.title"));
        setResourceBundle(resources);
    }

}

