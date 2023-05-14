package lab.client.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lab.client.Authorization;
import lab.client.GetDataFromServer;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Locale;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    private Socket socket;
    @FXML
    private javafx.scene.control.Button signup_button;
    @FXML
    private ChoiceBox<String> signup_language;
    @FXML
    private Label signup;
    @FXML
    private Label username_label;
    @FXML
    private Label password_label;
    @FXML
    private javafx.scene.control.Button login_button;
    @FXML
    private Label question;
    @FXML
    private javafx.scene.control.TextField tf_username;
    @FXML
    private javafx.scene.control.TextField tf_password;

    public TextField getTf_username() {
        return tf_username;
    }

    public TextField getTf_password() {
        return tf_password;
    }

    public Button getLogin_button() {
        return login_button;
    }

    public Button getSignup_button() {
        return signup_button;
    }

    private ResourceBundle resourceBundle;
    private String[] languages = {"RU", "ENG", "LVA", "NOR"};
    private Stage stage;

    public void setResourceBundle(ResourceBundle resourceBundle){
        this.resourceBundle = resourceBundle;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setResourceBundle(resourceBundle);
        signup_language.getItems().addAll(languages);
        if (resourceBundle.getLocale().toString().equals("ru")){
            signup_language.setValue("RU");
        }
        else if (resourceBundle.getLocale().toString().equals("en")){
            signup_language.setValue("ENG");
        }
        else if (resourceBundle.getLocale().toString().equals("lv")){
            signup_language.setValue("LVA");
        }
        else if (resourceBundle.getLocale().toString().equals("no")){
            signup_language.setValue("NOR");
        }

        signup_language.setOnAction(this::changeLanguage);

        signup_button.setOnAction(event -> {
            ConnectToServer connect = new ConnectToServer();
            Authorization authorization = new Authorization();
            try{
                authorization.signup(socket, tf_username.getText(), tf_password.getText());
                GetDataFromServer getDataFromServer = new GetDataFromServer();
                String answer = getDataFromServer.getData(socket).toString();
                if (answer.equals("такой пользователь уже существует")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("такой пользователь уже существует");
                    alert.show();
                }
                else if (answer.equals("Вы успешно зарагестрированы!")){
                    SceneController sceneController = new SceneController();
                    sceneController.switchToMainScene(event, socket, tf_username.getText(), tf_password.getText(), resourceBundle);
                }

            }catch (IOException e){
                System.out.println("SignUp IO");
            }catch (ClassNotFoundException e){
                System.out.println("ClassNotFound Sign UP");
            }
        });
    }

    public void switchToLoginScene(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.switchToLoginScene(event, this.socket, resourceBundle );
    }



    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket(){
        return socket;
    }


    public void changeLanguage(ActionEvent event){
        String language = signup_language.getValue();
        setLabels(language);
    }

    public void setStage(Stage stage) {
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
        signup.setText(resources.getString("login.label"));
        username_label.setText(resources.getString("username"));
        password_label.setText(resources.getString("password"));
        tf_username.setPromptText(resources.getString("tf.username"));
        tf_password.setPromptText(resources.getString("tf.password"));
        login_button.setText(resources.getString("login.button"));
        question.setText(resources.getString("question"));
        signup_button.setText(resources.getString("signup.button"));
        stage.setTitle(resources.getString("login.title"));
        setResourceBundle(resources);
    }
}
