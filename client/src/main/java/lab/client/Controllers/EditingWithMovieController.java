package lab.client.Controllers;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EditingWithMovieController implements Initializable {
    private Socket socket;
    private String username;
    private String password;
    private Stage stage;
    private Stage mainStage;
    private String command;
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
    private Button add_button;
    private ResourceBundle resourceBundle;
    private ArrayList<String> answers;


    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    String[] genres = {"ACTION", "DRAMA", "HORROR", "SCIENCE_FICTION"};
    String[] ratings = {"G", "PG", "NC_17", "R"};
    String[] countries = {"INDIA", "ITALY", "JAPAN"};

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
        movie_genre.getItems().addAll(genres);
        movie_rating.getItems().addAll(ratings);
        movie_country.getItems().addAll(countries);
        field_title.setOnAction(this::addMovie);
        field_x.setOnAction(this::addMovie);
        field_y.setOnAction(this::addMovie);
        field_oscars.setOnAction(this::addMovie);
        field_length.setOnAction(this::addMovie);
        field_name.setOnAction(this::addMovie);
        birthday.setOnAction(this::addMovie);
        field_weight.setOnAction(this::addMovie);
        movie_country.setOnAction(this::addMovie);
        movie_rating.setOnAction(this::addMovie);
        movie_genre.setOnAction(this::addMovie);
    }

    public void addMovie(ActionEvent ev) {
        GetCommand getCommand = new GetCommand();
        add_button.setOnAction(e -> {
            if (!checkIfEmpty()) {
                try {
                    Integer oscarsCount;
                    if (field_oscars.getText().isEmpty()) {
                        oscarsCount = 0;
                    } else {
                        oscarsCount = Integer.parseInt(field_oscars.getText());
                    }
                    float weight = Float.parseFloat(field_weight.getText());
                    if (oscarsCount >= 0 && oscarsCount < 12) {
                        if (weight > 50 && weight < 151) {
                            try {
                                String title = field_title.getText();
                                int x = Integer.parseInt(field_x.getText());
                                int y = Integer.parseInt(field_y.getText());
                                Date date = Calendar.getInstance().getTime();
                                Long length = Long.parseLong(field_length.getText());
                                MovieGenre genre;
                                try {
                                    genre = MovieGenre.valueOf(movie_genre.getValue());
                                } catch (NullPointerException exception) {
                                    genre = MovieGenre.NULL;
                                }
                                MpaaRating rating = MpaaRating.valueOf(movie_rating.getValue());
                                Person person = parsePerson();
                                Movie movie = new Movie(0, title, x, y, date, oscarsCount, length, genre, rating, person);
                                try {
                                    getCommand.getCmdAndSendToTheServer(this.socket, new String[]{command}, this.username, this.password, movie);
                                }catch (IOException exception){
                                    try {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setHeaderText(resourceBundle.getString("failed.connect"));
                                        alert.setContentText(resourceBundle.getString("failed.connect"));
                                        this.mainStage.close();
                                        SceneController sceneController = new SceneController();
                                        sceneController.switchToRecconectScene(e, resourceBundle);
                                    } catch (IOException exceeption) {

                                    }
                                }
                                String answer = new GetDataFromServer().getData(this.socket).toString();
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle(resourceBundle.getString("servers.answer"));
                                switch (command) {
                                    case "add":
                                        alert.setHeaderText(resourceBundle.getString("edit.header"));
                                    case "remove_lower":
                                        alert.setHeaderText(resourceBundle.getString("edit.header"));
                                    case "remove_greater":
                                        alert.setHeaderText(resourceBundle.getString("edit.header"));
                                }
                                alert.setContentText(initAns(answer));
                                alert.showAndWait();
                                stage.close();


                            } catch (NumberFormatException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (ClassNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(resourceBundle.getString("error"));
                            alert.setContentText(resourceBundle.getString("weight.problem"));
                            alert.show();
                        }
                    } else {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(resourceBundle.getString("error"));
                        alert.setContentText(resourceBundle.getString("oscars.problem"));
                        alert.show();
                    }
                }catch (NumberFormatException ex){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(resourceBundle.getString("error"));
                    alert.setContentText(resourceBundle.getString("empty.fields"));
                    alert.show();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(resourceBundle.getString("error"));
                alert.setContentText(resourceBundle.getString("empty.fields"));
                alert.show();
            }


        });
    }

    public boolean checkIfEmpty() {
        HashSet<String> list = new HashSet<>();
        list.add(field_title.getText());
        list.add(field_x.getText());
        list.add(field_y.getText());
        list.add(movie_rating.getValue());
        list.add(field_length.getText());
        list.add(field_name.getText());
        list.add(field_weight.getText());
        if (list.contains("") || list.isEmpty()) {
            return true;
        }
        return false;

    }
    public Person parsePerson(){
        String name = field_name.getText();
        float weight = Float.parseFloat(field_weight.getText());
        ZonedDateTime znd;
        try {
            System.out.println(birthday.getValue());
            String birth = String.valueOf(birthday.getValue());
            String[] bitrhdat = birth.split("-");
            String zonedDatetime = bitrhdat[1] + "/" + bitrhdat[2] + "/" + bitrhdat[0];
            znd = parseZND(zonedDatetime);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException exception) {
            znd = Person.zonedDateTime;
        }
        Country country;
        try {
            country = Country.valueOf(movie_country.getValue());
        } catch (NullPointerException exception) {
            country = Country.NULL;
        }
        return new Person(name, znd, weight, country);
    }

    public Socket getSocket() {
        return socket;
    }

    public void forClosing(Stage stage){
        this.mainStage = stage;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ZonedDateTime parseZND(String znd) {
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
        String hms = String.valueOf(H + ":" + M + ":" + S);
        System.out.println(hms);
        System.out.println(znd + " " + hms + ".900000");
        String pattern = "MM/dd/yyyy HH:mm:ss.SSSSSS";
        DateTimeFormatter Parser = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(znd + " " + hms + ".900000", Parser);
        System.out.println(zonedDateTime);
        return zonedDateTime;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCommand(String command) {
        this.command = command;
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
}
