package lab.client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab.client.Controllers.ConnectToServer;

import java.util.Locale;
import java.util.ResourceBundle;

public class TestClient extends Application {
    String login;
    String password;
    public static void main(String[] args)  {
        String host = "localhost";
        int port = 15000;
        ClientConnect clientConnect = new ClientConnect();
        //обработка UnresolvedExcep
        launch(args);
//        clientConnect.connectToServer(host, port);
        TestClient testClient = new TestClient();
        testClient.login = clientConnect.getLogin();
        testClient.password = clientConnect.getPassword();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle resources = ResourceBundle.getBundle("bundles.Locale");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConnectToServer.fxml"), resources);
        loader.setResources(resources);
//        loader.setResources(ResourceBundle.getBundle("bundles.Locale", new Locale("ru")));
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ConnectToServer.fxml"), resources);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle(loader.getResources().getString("connect.to.server.title"));
        ConnectToServer connect = loader.getController();
        connect.setStage(stage);
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.show();
    }
}
