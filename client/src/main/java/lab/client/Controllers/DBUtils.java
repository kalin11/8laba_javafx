//package lab.client.Controllers;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.stage.Stage;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.sql.*;
//
//public class DBUtils {
//    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username){
//        Parent root = null;
//
//        if (username!=null){
//            try{
//                URL url = new File(fxmlFile).toURI().toURL();
//                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
//                loader.setLocation(DBUtils.class.getResource(fxmlFile));
//                MainController mainController = loader.getController();
//                mainController.setUserInfo(username);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//        else {
//            try{
//                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setTitle(title);
//        stage.setScene(new Scene(root, 600, 400));
//        stage.show();
//    }
//
//    public static void signUp(ActionEvent event, String username, String password){
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try{
//            connection = DriverManager.getConnection("jdbc:postgresql://localhost:63333/studs", "s336805", "ipb588");
//            ps = connection.prepareStatement("SELECT * FROM \"Users\" where user_log = ?");
//            ps.setString(1, username);
//            rs = ps.executeQuery();
//            if (rs.isBeforeFirst()){
//                System.out.println("пользователь уже есть");
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setContentText("ты не можешь использовать это имя");
//                alert.show();
//            }
//            else {
//                ps = connection.prepareStatement("INSERT INTO \"Users\" (user_log, password) VALUES (?, ?)");
//                ps.setString(1, username);
//                ps.setString(2, password);
//                ps.executeUpdate();
//                changeScene(event, "C:\\Users\\kal1n\\IdeaProjects\\lab7TestThread\\client\\src\\main\\java\\lab\\client\\Controllers\\MainScene.fxml", "Welcome!", username);
//
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
//
//    public static void login(ActionEvent event, String username, String password){
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try{
//            connection = DriverManager.getConnection("jdbc:postgresql://localhost:63333/studs", "s336805", "ipb588");
//            ps = connection.prepareStatement("SELECT password FROM \"Users\" WHERE user_log = ?");
//            ps.setString(1, username);
//            rs = ps.executeQuery();
//
//            if (!rs.isBeforeFirst()){
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setContentText("неверные данные");
//                alert.show();
//            }
//            else {
//                while (rs.next()){
//                    String pass = rs.getString("password");
//                    if (pass.equals(password)){
//                        changeScene(event, "C:\\Users\\kal1n\\IdeaProjects\\lab7TestThread\\client\\src\\main\\java\\lab\\client\\Controllers\\MainScene.fxml", "Welcome", username);
//                    }
//                    else {
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setContentText("данные неверны");
//                        alert.show();
//                    }
//                }
//            }
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
//}
