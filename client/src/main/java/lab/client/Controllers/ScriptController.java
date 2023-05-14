package lab.client.Controllers;

import DB.DataBase.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lab.client.GetCommand;
import lab.client.GetDataFromServer;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScriptController implements Initializable {
    private Socket socket;
    private String username;
    private String password;
    private Stage stage;
    private String file;
    private Stage mainStage;
    @FXML
    private Label label_path;

    @FXML
    private javafx.scene.control.TextField path;
    @FXML
    private javafx.scene.control.Button execute;

    private ResourceBundle resourceBundle;
    private ArrayList<String> answers;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        answers = new ArrayList<>();
        answers.add("коллекция пустая");
        answers.add("нет объектов, принадлежащих вам");
        answers.add("нет объектов с таким id, принадлежащим вам");
        answers.add("нет объектов, удовлетворяющих условию");
        answers.add("нет объектов, принадлежащих вам");
        answers.add("коллекция не была обновлена");
        answers.add("коллекция пустая, добавьте объекты");
        answers.add("фильмов с жанром");
        execute();
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void execute() {
        execute.setOnAction(event -> {

            GetCommand getCommand = new GetCommand();
            String[] cmd = new String[2];
            cmd[0] = "execute_script";
            cmd[1] = path.getText();
            try {
                try {
                    getCommand.getCmdAndSendToTheServer(this.socket, cmd, this.username, this.password, null);
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
                alert.setHeaderText(resourceBundle.getString("script.header"));
                alert.setTitle(resourceBundle.getString("servers.answer"));
                alert.setContentText(initAns(answer));
                alert.showAndWait();
                alert.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            stage.close();

        });
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public String initAns(String answer) {
        if (answers.contains(answer)) {
            if (answer.equals(answers.get(0))) {
                return resourceBundle.getString("empty.collection");
            } else if (answer.equals(answers.get(1))) {
                return resourceBundle.getString("no.objects.for.you");
            } else if (answer.equals(answers.get(2))) {
                return resourceBundle.getString("no.objects.for.you.with.id");
            } else if (answer.equals(answers.get(3))) {
                return resourceBundle.getString("no.object.with.that.condition");
            } else if (answer.equals(answers.get(4))) {
                return resourceBundle.getString("collection.wasnt.update");
            } else if (answer.equals(answers.get(5))) {
                return resourceBundle.getString("collection.empty.add.objects");
            } else if (answer.contains(answers.get(6))) {
                return resourceBundle.getString("movies.with.genre");
            }
        }
        return answer;
    }

    public void forClosing(Stage stage){
        this.mainStage = stage;
    }
}
