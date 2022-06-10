package lab.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jdk.nashorn.api.scripting.URLReader;
import lab.client.GetCommand;
import lab.client.GetDataFromServer;
import org.apache.commons.collections4.Get;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CountByGenreController implements Initializable {

    private Socket socket;
    private String login;
    private String password;

    @FXML
    private Label label;
    @FXML
    private javafx.scene.control.Button answer_button;
    @FXML
    private ChoiceBox<String> genre;
    private ResourceBundle resourceBundle;

    private String[] genres = {"ACTION", "DRAMA","HORROR", "SCIENCE_FICTION"};
    private Stage mainStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genre.getItems().addAll(genres);
        genre.setOnAction(this::getAnswer);
    }


    public void getAnswer(ActionEvent ev){
        GetCommand getCommand = new GetCommand();
        answer_button.setOnAction(event -> {
            String[] cmd = new String[2];
            try{
                cmd[0] = "count_by_genre";
                cmd[1] = genre.getValue();

                try {
                    getCommand.getCmdAndSendToTheServer(this.socket, cmd, this.login, this.password, null);
                }catch (IOException e){
                    try {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(resourceBundle.getString("failed.connect"));
                        alert.setContentText(resourceBundle.getString("failed.connect"));
                        this.mainStage.close();
                        SceneController sceneController = new SceneController();
                        sceneController.switchToRecconectScene(event, resourceBundle);
                    } catch (IOException exception) {

                    }
                }
                String answer = new GetDataFromServer().getData(this.socket).toString();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(resourceBundle.getString("servers.answer"));
                alert.setHeaderText(null);
                alert.setContentText(answer.replace("фильмов с жанром", resourceBundle.getString("movies.with.genre")));
                alert.showAndWait();
            }catch (IOException e){
                this.mainStage.close();
                SceneController sceneController = new SceneController();
                try {
                    sceneController.switchToRecconectScene(event, resourceBundle);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        });
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void forClosing(Stage stage){
        this.mainStage = stage;
    }
}
