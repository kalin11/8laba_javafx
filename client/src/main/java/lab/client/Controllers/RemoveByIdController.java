package lab.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lab.client.GetCommand;
import lab.client.GetDataFromServer;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class RemoveByIdController implements Initializable {
    private String login;
    private String password;
    private Socket socket;
    private Stage stage;
    private MainController mainController;
    private Stage mainStage;
    @FXML
    private Label label_id;
    @FXML
    private javafx.scene.control.Button button_delete;
    @FXML
    private javafx.scene.control.TextField tf_id;
    private ResourceBundle resourceBundle;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        removeElement();
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void removeElement(){
        button_delete.setOnAction(event -> {
            String[] cmd = new String[2];
            GetCommand getCommand = new GetCommand();
            if (!tf_id.getText().isEmpty()){
                try{
                    long id = Long.parseLong(tf_id.getText());
                    cmd[0] = "remove_by_id";
                    cmd[1] = tf_id.getText();
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
                    alert.setContentText(resourceBundle.getString("editing.table"));
                    alert.setHeaderText(resourceBundle.getString("editing.table"));
                    alert.setTitle(resourceBundle.getString("info.header"));
                    if (answer.isEmpty()){
                        alert.setContentText(resourceBundle.getString("remove.good"));
                        alert.showAndWait();
                    }
                    else {
                        alert.setContentText(answer);
                        alert.showAndWait();
                    }
                    stage.close();
                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(resourceBundle.getString("incorrect.data.type"));
                    alert.setContentText(resourceBundle.getString("incorrect.data.type"));
                    alert.show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(resourceBundle.getString("empty.field"));
                alert.setContentText(resourceBundle.getString("empty.field"));
                alert.show();
            }
        });
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void forClosing(Stage stage){
        this.mainStage = stage;
    }
}
