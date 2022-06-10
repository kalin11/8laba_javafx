package lab.client.Controllers;

import DB.DataBase.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lab.client.GetCommand;
import lab.client.GetDataFromServer;
import org.apache.commons.collections4.Get;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class InfoContoller implements Initializable {
    private Socket socket;
    private String username;
    private String password;
    private Stage stage;
    private Product product;
    @FXML
    private Label title;
    @FXML
    private Label label_id;
    @FXML
    private Label label_title;
    @FXML
    private Label label_x;
    @FXML
    private Label label_y;
    @FXML
    private Label label_date;
    @FXML
    private Label label_oscars;
    @FXML
    private Label label_length;
    @FXML
    private Label label_genre;
    @FXML
    private Label label_rating;
    @FXML
    private Label label_name;
    @FXML
    private Label label_birthday;
    @FXML
    private Label label_weight;
    @FXML
    private Label label_country;
    @FXML
    private TextField tf_id;
    @FXML
    private TextField tf_title;
    @FXML
    private TextField tf_x;
    @FXML
    private TextField tf_y;
    @FXML
    private TextField tf_date;
    @FXML
    private TextField tf_oscars;
    @FXML
    private TextField tf_length;
    @FXML
    private TextField tf_genre;
    @FXML
    private TextField tf_rating;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_birthday;
    @FXML
    private TextField tf_weight;
    @FXML
    private TextField tf_country;
    private ResourceBundle resourceBundle;
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

    public void setProduct(Product product) {
        this.product = product;
        this.tf_id.setText(this.product.getmovie_id().toString());
        this.tf_date.setText(this.product.getDate());
        this.tf_title.setText(this.product.getTitle());
        this.tf_x.setText(this.product.getX().toString());
        this.tf_y.setText(this.product.getY().toString());
        try {
            this.tf_oscars.setText(this.product.getOscarsCount().toString());
        }catch (NullPointerException e){
            this.tf_oscars.setText("");
        }
        this.tf_length.setText(this.product.getLength().toString());
        try {
            this.tf_genre.setText(this.product.getGenre());
        }catch (NullPointerException e){
            this.tf_genre.setText("");
        }
        this.tf_rating.setText(this.product.getRating());
        this.tf_name.setText(this.product.getPerson_name());
        try {
            this.tf_birthday.setText(this.product.getBirthday());
        }catch (NullPointerException e){
            this.tf_birthday.setText(null);
        }
        this.tf_weight.setText(this.product.getWeight().toString());
        try {
            this.tf_country.setText(this.product.getCountry());
        }catch (NullPointerException e){
            this.tf_country.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        update.setOnAction(event -> {
//            SceneController sceneController = new SceneController();
//            try {
//                sceneController.switchToUpdateScene(event, this.socket, username, password, product);
//                stage.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        });
//        remove.setOnAction(event -> {
//            GetCommand getCommand = new GetCommand();
//            String[] cmd = new String[2];
//            cmd[0] = "remove_by_id";
//            cmd[1] = product.getmovie_id().toString();
//            try {
//                getCommand.getCmdAndSendToTheServer(this.socket, cmd, this.username, this.password, null);
//                String answer = new GetDataFromServer().getData(this.socket).toString();
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setContentText("Server's answer");
//                if (answer.isEmpty()){
//                    alert.setContentText("Успешно!");
//                }
//                else {
//                    alert.setContentText(answer);
//                }
//                alert.showAndWait();
//                stage.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        });
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}
