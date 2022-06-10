package lab.client.Controllers;

import DB.DataBase.Product;
import entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lab.client.GetCommand;
import lab.client.GetDataFromServer;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UpdateController implements Initializable {
    private Socket socket;
    private String username;
    private String password;
    private Stage stage;
    private Product product;
    private Stage mainStage;
    @FXML
    private Label title;
    @FXML
    private Label x;
    @FXML
    private Label y;
    @FXML
    private Label oscars_count;
    @FXML
    private Label length;
    @FXML
    private Label genre;
    @FXML
    private Label rating;
    @FXML
    private Label persons_name;
    @FXML
    private Label persons_birthday;
    @FXML
    private Label persons_weight;
    @FXML
    private Label persons_country;
    private long id;
    @FXML
    private TextField field_title;
    @FXML
    private TextField field_x;
    @FXML
    private TextField field_y;
    @FXML
    private TextField field_oscars;
    @FXML
    private TextField field_length;
    @FXML
    private ChoiceBox<String> movie_genre;
    @FXML
    private ChoiceBox<String> movie_rating;
    @FXML
    private TextField field_name;
    @FXML
    private DatePicker birthday;
    @FXML
    private TextField field_weight;
    @FXML
    private ChoiceBox<String> movie_country;
    @FXML
    private Button execute_button;
    @FXML
    private Button remove_button;

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

    private ResourceBundle resourceBundle;

    String[] genres = {"ACTION", "DRAMA", "HORROR", "SCIENCE_FICTION"};
    String[] ratings = {"G", "PG", "NC_17", "R"};
    String[] countries = {"INDIA", "ITALY", "JAPAN"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setResourceBundle(resourceBundle);
        movie_genre.getItems().addAll(genres);
        movie_rating.getItems().addAll(ratings);
        movie_country.getItems().addAll(countries);
        execute_button.setOnAction(event -> {
            Integer oscarsCount;
            try {
                if (!checkIfEmpty()) {
                    try {
                        if (field_oscars.getText().isEmpty()) {
                            oscarsCount = 0;
                        } else {
                            oscarsCount = Integer.parseInt(field_oscars.getText());
                        }
                        GetCommand getCommand = new GetCommand();
                        id = product.getmovie_id();
                        String title = field_title.getText();
                        //x y length weight
                        int x = Integer.parseInt(field_x.getText());
                        int y = Integer.parseInt(field_y.getText());
                        Date date = Calendar.getInstance().getTime();
                        Long length = Long.parseLong(field_length.getText());
                        float weight = Float.parseFloat(field_weight.getText());
                        if (oscarsCount >= 0 && oscarsCount < 12) {
                            if (weight > 50 || weight < 151) {
                                MovieGenre genre;
                                try {
                                    genre = MovieGenre.valueOf(movie_genre.getValue());
                                } catch (NullPointerException e) {
                                    genre = MovieGenre.NULL;
                                }
                                MpaaRating rating = MpaaRating.valueOf(movie_rating.getValue());
                                String name = field_name.getText();
                                ZonedDateTime znd;
                                System.out.println(birthday.getValue());
                                try {
//                                    System.out.println(birthday.getValue());
//                                    String b1 = String.valueOf(product.getBirthday());
//                                    System.out.println("show me how to love     " + b1);
//                            ZonedDateTime znd = parse(b1);
                                    String birth = String.valueOf(birthday.getValue());
                                    System.out.println("teeeeeest   " + birth);
                                    String[] bitrhdat = birth.split("-");
                                    String zonedDatetime = bitrhdat[1] + "/" + bitrhdat[2] + "/" + bitrhdat[0];
                                    System.out.println(zonedDatetime);
                                    znd = parseZND(zonedDatetime);
                                } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                                    znd = Person.zonedDateTime;
                                }
                                System.out.println("znd - " + znd);
                                Country country;
                                try {
                                    country = Country.valueOf(movie_country.getValue());
                                } catch (NullPointerException e) {
                                    country = Country.NULL;
                                }
                                Person person = new Person(name, znd, weight, country);
                                System.out.println("birthday - " + person.getBirthday());
                                Movie movie = new Movie(id, title, x, y, date, oscarsCount, length, genre, rating, person);
                                movie.allInfo();
                                String[] cmd = {"update", String.valueOf(movie.getId())};
                                System.out.println(genre.ordinal());
                                System.out.println(rating.ordinal());
                                try {
                                    getCommand.getCmdAndSendToTheServer(this.socket, cmd, this.username, this.password, movie);
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
                                alert.setHeaderText(resourceBundle.getString("editing.table"));
                                alert.setContentText(answer);
                                alert.showAndWait();
                                stage.close();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText(resourceBundle.getString("error"));
                                alert.setContentText(resourceBundle.getString("weight.problem"));
                                alert.show();
                            }
                            ;
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(resourceBundle.getString("error"));
                            alert.setContentText(resourceBundle.getString("oscars.problem"));
                            alert.show();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(resourceBundle.getString("error"));
                    alert.setContentText(resourceBundle.getString("empty.fields"));
                    alert.show();
                }
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(resourceBundle.getString("error"));
                alert.setContentText(resourceBundle.getString("type.error"));
                alert.show();
            }
        });
        remove_button.setOnAction(event -> {
            GetCommand getCommand = new GetCommand();
            String[] cmd = new String[2];
            cmd[0] = "remove_by_id";
            cmd[1] = product.getmovie_id().toString();
//            cmd[1] = String.valueOf(product.getMovie_id());
            try {
                getCommand.getCmdAndSendToTheServer(this.socket, cmd, this.username, this.password, null);
                String answer = new GetDataFromServer().getData(this.socket).toString();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(resourceBundle.getString("editing.table"));
                alert.setContentText(resourceBundle.getString("servers.answer"));
                if (answer.isEmpty()) {
                    alert.setContentText(resourceBundle.getString("remove.good"));
                } else {
                    alert.setContentText(answer);
                }
                alert.showAndWait();
                stage.close();
            } catch (IOException e) {
                try {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(resourceBundle.getString("failed.connect"));
                    alert.setContentText(resourceBundle.getString("failed.connect"));
                    this.mainStage.close();
                    SceneController sceneController = new SceneController();
                    sceneController.switchToRecconectScene(event, resourceBundle);
                } catch (IOException exception) {

                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

//        field_x.setOnAction(this::updateMovie);
//        field_y.setOnAction(this::updateMovie);
//        field_oscars.setOnAction(this::updateMovie);
//        field_length.setOnAction(this::updateMovie);
//        movie_genre.setOnAction(this::updateMovie);
//        movie_rating.setOnAction(this::updateMovie);
//        field_name.setOnAction(this::updateMovie);
//        birthday.setOnAction(this::updateMovie);
//        field_weight.setOnAction(this::updateMovie);
//        movie_country.setOnAction(this::updateMovie);

        //добавить обновление нормальное и удаление
    }

    public void forClosing(Stage stage){
        this.mainStage = stage;
    }
    public void setProduct(Product product) {
        this.product = product;
        this.field_title.setText(product.getTitle());
        this.field_x.setText(product.getX().toString());
        this.field_y.setText(product.getY().toString());
        this.field_oscars.setText(product.getOscarsCount().toString());
        this.field_length.setText(product.getLength().toString());
        this.movie_genre.setValue(product.getGenre());
        this.movie_rating.setValue(product.getRating());
        this.field_name.setText(product.getPerson_name());
        String numbers;
        DateTimeFormatter formatter;
        LocalDate localDate = LocalDate.now();
//            System.out.println(product.getBirthday());
        try {
            if (resourceBundle.getLocale().toString().equals("ru") || resourceBundle.getLocale().toString().equals("no")) {
                System.out.println("хыыыыыыыыыыыыыыыыыыы + " + product.getBirthday());
                numbers = product.getBirthday();
                String date = numbers.substring(0, 8);
                String time = numbers.substring(9);
                if (time.length() < 5) {
                    time = "0" + time;
                }
                System.out.println("date" + date);
                System.out.println("time" + time);
                formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
                localDate = LocalDate.parse(date.trim() + " " + time.trim(), formatter);
            } else if (resourceBundle.getLocale().toString().equals("en")) {
                numbers = product.getBirthday().substring(0, 8).trim();
                String[] values = numbers.split("/", 3);
                if (values[0].length() < 2) {
                    values[0] = "0" + values[0];
                }
                if (values[1].length() < 2) {
                    values[1] = "0" + values[1];
                }
                numbers = values[0] + "/" + values[1] + "/" + values[2];
                System.out.println(numbers.split(" ")[0]);
                formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
                localDate = LocalDate.parse(numbers.split(" ")[0], formatter);
            } else if (resourceBundle.getLocale().toString().equals("lv")) {
                numbers = product.getBirthday().substring(0, 8).trim();
                String[] values = numbers.split("\\.", 3);
                if (values[1].length() < 2) {
                    values[1] = "0" + values[1];
                }
                if (values[2].length() < 2) {
                    values[2] = "0" + values[2];
                }
                numbers = values[0] + "." + values[1] + "." + values[2];
                formatter = DateTimeFormatter.ofPattern("yy.dd.MM");
                localDate = LocalDate.parse(numbers, formatter);
            }
            this.birthday.setValue(localDate);
        }catch (NullPointerException e){
            this.birthday.setValue(null);
        }
//            numbers = product.getBirthday().substring(0, 10);
//        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        localDate = LocalDate.parse(numbers, formatter);

        this.field_weight.setText(product.getWeight().toString());
        this.movie_country.setValue(product.getCountry());
    }

    public void updateMovie(ActionEvent ev) {


    }

    public void remove(ActionEvent ev){

    }


    public boolean checkIfEmpty() {
        HashSet<String> list = new HashSet<>();
        list.add(field_title.getText());
        list.add(field_x.getText());
        list.add(field_y.getText());
        list.add(field_length.getText());
        list.add(movie_rating.getValue());
        list.add(field_name.getText());
        list.add(field_weight.getText());
        if (list.contains("") || list.isEmpty()) {
            return true;
        }
        return false;
    }

    public ZonedDateTime parse(String time){
        String date = time.substring(0,8).trim();
        String t = time.substring(9).trim();
        if (t.length() < 5){
            t = "0"+ t;
        }
        String[] aga  = date.split("\\.");
        String data = aga[1] + "/" + aga[0] + "/" +"20" +  aga[2];
        String[] yes = t.split(":");
        String taim = yes[0] + ":" + yes[1];
        String finaltime = data + " " + taim + ":00.000000";

        String pattern = "MM/dd/yyyy HH:mm:ss.SSSSSS";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withLocale(resourceBundle.getLocale());
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(finaltime, formatter);
        return zonedDateTime;
    }
    public ZonedDateTime parseZND(String znd) {
        //06/06/2022
        String hms;
        try {
            hms = product.getBirthday().split(" ")[1].trim() + ":00";
            if (hms.length() < 8) {
                hms = "0" + hms;
            }
        }catch (NullPointerException e){
            String H = "";
            String M = "";
            String S = "";

            int h = (int) (Math.random() * 24);
            if (h < 10) {
                H = "0" + h;
            } else {
                H = String.valueOf(h);
            }
            int m = (int) (Math.random() * 60);
            if (m < 10) {
                M = "0" + m;
            } else {
                M = String.valueOf(m);
            }
            int s = (int) (Math.random() * 60);
            if (s < 10) {
                S = "0" + s;
            } else {
                S = String.valueOf(s);
            }
            hms = H +":" +  M +":" +  S;
        }

//        System.out.println(hms);
//        System.out.println(znd + " " + hms + ".900000");
        String pattern = "MM/dd/yyyy HH:mm:ss";
        System.out.println("hms - "+hms);
        String zoneId = "";
        if (resourceBundle.getLocale().toString().equals("ru")){
            zoneId = "Europe/Moscow";
        }
        else if (resourceBundle.getLocale().toString().equals("no")){
            zoneId = "Europe/Oslo";
        }
        else if (resourceBundle.getLocale().toString().equals("lv")){
            zoneId = "Europe/Riga";
        }
        else if (resourceBundle.getLocale().toString().equals("en")){
            zoneId = "Indian/Comoro";
        }
        DateTimeFormatter Parser = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.of(zoneId));
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(znd + " " + hms , Parser);
        System.out.println("zoned date time - " + zonedDateTime);
        return zonedDateTime;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

}
