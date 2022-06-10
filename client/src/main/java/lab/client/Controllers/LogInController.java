package lab.client.Controllers;

import cmd.Cmd;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import lab.client.Authorization;
import lab.client.GetDataFromServer;

public class LogInController implements Initializable {

    private Socket socket;
    @FXML
    private javafx.scene.control.Label login_label;
    @FXML
    private javafx.scene.control.Label username_label;
    @FXML
    private javafx.scene.control.Label password_label;
    @FXML
    private javafx.scene.control.Label question;
    @FXML
    private Label label_username;
    @FXML
    private ChoiceBox<String> login_language;

    @FXML
    private javafx.scene.control.Button button_login;

    @FXML
    private javafx.scene.control.Button button_signup;

    @FXML
    private javafx.scene.control.TextField tf_username;

    @FXML
    private javafx.scene.control.TextField tf_password;

    public javafx.scene.control.Button getButton_login() {
        return button_login;
    }

    public javafx.scene.control.Button getButton_signup() {
        return button_signup;
    }

    public Label getLabel_username() {
        return label_username;
    }

    public javafx.scene.control.TextField getTf_password() {
        return tf_password;
    }

    public javafx.scene.control.TextField getTf_username() {
        return tf_username;
    }

    public void setUserInfo(String username){
        label_username.setText("Hello, " + username);
    }

    private ResourceBundle resourceBundle;

    private Stage stage;

    private String[] languages = {"RU", "ENG", "LVA", "NOR"};



    public void setResourceBundle(ResourceBundle resourceBundle){
        this.resourceBundle = resourceBundle;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setResourceBundle(resourceBundle);
        login_language.getItems().addAll(languages);
        if (resourceBundle.getLocale().toString().equals("ru")){
            login_language.setValue("RU");
        }
        else if (resourceBundle.getLocale().toString().equals("en")){
            login_language.setValue("ENG");
        }
        else if (resourceBundle.getLocale().toString().equals("lv")){
            login_language.setValue("LVA");
        }
        else if (resourceBundle.getLocale().toString().equals("no")){
            login_language.setValue("NOR");
        }

        login_language.setOnAction(this::changeLanguage);
        button_login.setOnAction(event -> {
            Authorization authorization = new Authorization();
            try {
                authorization.login(this.socket,  tf_username.getText().trim(), tf_password.getText().trim());
                GetDataFromServer getDataFromServer = new GetDataFromServer();
                String answer = getDataFromServer.getData(this.socket).toString();
                if (answer.equals("Вы успешно вошли!")){
                    SceneController sceneController = new SceneController();
                    sceneController.switchToMainScene(event, this.socket, tf_username.getText(), tf_password.getText(), this.resourceBundle);
                }
                else if (answer.equals("такой учетной записи не было найдено")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("такой учетной записи не было найдено");
                    alert.show();
                }
            } catch (IOException e) {
                System.out.println("LogIn IO");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("ClassNotFound Log in");
            }
        });
    }

    public void switchToSignUpScene(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.switchToSignUpScene(event, this.socket, resourceBundle);
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket(){
        return socket;
    }

    public void switchToMainScene(ActionEvent event){
//        button_login.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                ConnectToServer connect = new ConnectToServer();
//                Authorization authorization = new Authorization();
//                try {
//                    authorization.login(connect.getSocket(),  tf_username.getText().trim(), tf_password.getText().trim());
//                    GetDataFromServer getDataFromServer = new GetDataFromServer();
//                    String answer = getDataFromServer.getData(connect.getSocket()).toString();
//                    if (answer.equals("Вы успешно вошли!")){
//                        SceneController sceneController = new SceneController();
//                        sceneController.switchToMainScene(event, connect.getSocket());
//                    }
//                    else if (answer.equals("такой учетной записи не было найдено")){
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setContentText("такой учетной записи не было найдено");
//                        alert.show();
//                    }
//                } catch (IOException e) {
//                    System.out.println("LogIn IO");
//                } catch (ClassNotFoundException e) {
//                    System.out.println("ClassNotFound Log in");
//                }
//
//            }
//        });

    }

    public void changeLanguage(ActionEvent event){
        String language = login_language.getValue();
        setLabels(language);
    }

    public void setStage(Stage stage){
        this.stage = stage;
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
        login_label.setText(resources.getString("login.label"));
        username_label.setText(resources.getString("username"));
        password_label.setText(resources.getString("password"));
        tf_username.setPromptText(resources.getString("tf.username"));
        tf_password.setPromptText(resources.getString("tf.password"));
        button_login.setText(resources.getString("login.button"));
        question.setText(resources.getString("question"));
        button_signup.setText(resources.getString("signup.button"));
        stage.setTitle(resources.getString("login.title"));
        setResourceBundle(resources);
    }

}
