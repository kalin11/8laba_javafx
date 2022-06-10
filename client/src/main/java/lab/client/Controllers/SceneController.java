package lab.client.Controllers;

import DB.DataBase.Product;
import com.sun.xml.internal.stream.buffer.AttributesHolder;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ResourceBundle;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToLoginScene(ActionEvent event, Socket socket, ResourceBundle resourceBundle) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), resourceBundle);
        root = loader.load();
        LogInController log = loader.getController();
        log.setResourceBundle(resourceBundle);
        log.setSocket(socket);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(loader.getResources().getString("login.title"));
        log.setStage(stage);
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToRecconectScene(ActionEvent event, ResourceBundle resourceBundle) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConnectToServer.fxml"), resourceBundle);
        root = loader.load();
        ConnectToServer connect = loader.getController();
        connect.setResourceBundle(resourceBundle);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(loader.getResources().getString("connect.to.server.title"));
        connect.setStage(stage);
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSignUpScene(ActionEvent event, Socket socket, ResourceBundle resourceBundle) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sign-up.fxml"), resourceBundle);
        root = loader.load();
        SignUpController sign = loader.getController();
        sign.setResourceBundle(resourceBundle);
        sign.setSocket(socket);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(loader.getResources().getString("login.title"));
        sign.setStage(stage);
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMainScene(ActionEvent event, Socket socket, String username, String password, ResourceBundle resourceBundle) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"), resourceBundle);
        root = loader.load();
        MainController mainController = loader.getController();
        mainController.setParent(root);
        mainController.setHex(username);
        mainController.setResourceBundle(resourceBundle);
        mainController.setSocket(socket);
        mainController.setLogin(username);
        mainController.displayName(username);
        mainController.setPassword(password);
//        mainController.drawRectange();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainController.setStage(stage);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setResizable(false);
        stage.setX((primScreenBounds.getWidth() - stage.getWidth())/2);
        stage.setX((primScreenBounds.getHeight() - stage.getHeight())/2);
        stage.setTitle(loader.getResources().getString("main.title"));
        scene = new Scene(root);
        mainController.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToEditingAddingScene(ActionEvent event, Socket socket, String username, String passwod, String command, ResourceBundle resourceBundle) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add.fxml") , resourceBundle);
        root = loader.load();
        Stage stage = new Stage();
        EditingWithMovieController editController = loader.getController();
        editController.setSocket(socket);
        editController.setPassword(passwod);
        editController.setUsername(username);
        editController.setStage(stage);
        editController.setCommand(command);
        editController.setResourceBundle(resourceBundle);
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        editController.forClosing(this.stage);
        stage.setResizable(false);
        stage.setTitle(loader.getResources().getString("editing.table"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void switchToScriptScene(ActionEvent event, Socket socket, String username, String password, String path, ResourceBundle resourceBundle) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/script.fxml"), resourceBundle);
        root = loader.load();
        Stage stage = new Stage();
        ScriptController scriptController = loader.getController();
        scriptController.setSocket(socket);
        scriptController.setPassword(password);
        scriptController.setUsername(username);
        scriptController.setStage(stage);
        scriptController.setFile(path);
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scriptController.forClosing(this.stage);
        scriptController.setResourceBundle(resourceBundle);
        stage.setResizable(false);
        stage.setTitle(loader.getResources().getString("script.title"));
        stage.setScene(new Scene(root));
        stage.show();

    }
    public void switchToUpdateScene(MouseEvent event, Socket socket, String username, String password, Product product, ResourceBundle resourceBundle) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/update.fxml"), resourceBundle);
        root = loader.load();
        Stage stage = new Stage();
        UpdateController updateController = loader.getController();
        updateController.setProduct(product);
        updateController.setPassword(password);
        updateController.setSocket(socket);
        updateController.setStage(stage);
        updateController.setUsername(username);
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        updateController.forClosing(this.stage);
        stage.setResizable(false);
        updateController.setResourceBundle(resourceBundle);
        stage.setTitle(loader.getResources().getString("updating.title"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void switchToCountByGenre(ActionEvent event, Socket socket, String username, String password, ResourceBundle resourceBundle) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/count_by_genre.fxml"), resourceBundle);
        root = loader.load();
        CountByGenreController countByGenreController = loader.getController();
        countByGenreController.setLogin(username);
        countByGenreController.setPassword(password);
        countByGenreController.setSocket(socket);
        countByGenreController.setResourceBundle(resourceBundle);
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        countByGenreController.forClosing(this.stage);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle(loader.getResources().getString("count.by.genre.title"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void switchToRemoveByID(ActionEvent event, Socket socket, String username, String password, MainController mainController, ResourceBundle resourceBundle) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/remove_by_id.fxml"), resourceBundle);
        root = loader.load();
        Stage stage = new Stage();
        RemoveByIdController removeByIdController = loader.getController();
        removeByIdController.setLogin(username);
        removeByIdController.setPassword(password);
        removeByIdController.setSocket(socket);
        removeByIdController.setStage(stage);
        removeByIdController.setResourceBundle(resourceBundle);
        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        removeByIdController.forClosing(this.stage);
        removeByIdController.setMainController(mainController);
        stage.setTitle(loader.getResources().getString("remove.by.id.title"));
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void switchToInfoScene(MouseEvent event, Socket socket, String username, String password, Product product, ResourceBundle resourceBundle) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/movie_info.fxml"), resourceBundle);
        root = loader.load();
        Stage stage = new Stage();
        InfoContoller infoContoller = loader.getController();
        infoContoller.setProduct(product);
        infoContoller.setPassword(password);
        infoContoller.setSocket(socket);
        infoContoller.setStage(stage);
        infoContoller.setUsername(username);
        infoContoller.setResourceBundle(resourceBundle);
        stage.setResizable(false);
        stage.setTitle(loader.getResources().getString("info.title"));
        stage.setScene(new Scene(root));
        stage.show();
    }

}
