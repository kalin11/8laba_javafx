package lab.client.Controllers;

import DB.DataBase.GetCollectionFromDB;
import DB.DataBase.Product;
import entity.Country;
import entity.MovieGenre;
import entity.Person;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import lab.client.GetCommand;
import lab.client.GetDataFromServer;
import lab.client.TestClient;

import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainController implements Initializable {
    private Socket socket;
    private String path;
    private Parent root;
    private int colorCounter = 0;
    @FXML
    private Canvas canvas;
    @FXML
    private TextField color;
    @FXML
    private Label choose_cmd;
    @FXML
    private ChoiceBox<String> main_lang;
    @FXML
    private Label dynamicHead;
    @FXML
    private Label dynamicInfo;
    @FXML
    private Label headLabel;
    @FXML
    private TextField filter;
    @FXML
    private ChoiceBox<String> choose_table;
    @FXML
    private Label usernameLabel;
    @FXML
    private TableView<Product> table;
    @FXML
    private TableColumn<Product, Long> movie_id;
    @FXML
    private TableColumn<Product, String> title;
    @FXML
    private TableColumn<Product, Integer> x;
    @FXML
    private TableColumn<Product, Integer> y;
    @FXML
    private TableColumn<Product, String> creation_date;
    @FXML
    private TableColumn<Product, Integer> oscars_count;
    @FXML
    private TableColumn<Product, Long> length;
    @FXML
    private TableColumn<Product, String> genre;
    @FXML
    private TableColumn<Product, String> rating;
    @FXML
    private TableColumn<Product, String> person_name;
    @FXML
    private TableColumn<Product, String> birthday;
    @FXML
    private TableColumn<Product, Float> weight;
    @FXML
    private TableColumn<Product, String> country;
    @FXML
    private TableColumn<Product, String> owner;
    @FXML
    private Button execute_command;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Button help_button;
    @FXML
    private Label average_label;
    @FXML
    private Label average_value;
    private ResourceBundle resourceBundle;
    private Stage stage;
    private Scene scene;

    private ArrayList<String> cmdsWithoutArgs = new ArrayList<>();
    private ArrayList<String> cmdsWithMovie = new ArrayList<>();
    private String password;
    private String login;
    private ObservableList<Product> list;
    private ObservableList<Product> listik;
    private String infoAnswer;
    private ArrayList<String> answers;
    private String hex;
    private String[] commands  = {"add", "remove_by_id",
            "clear", "execute_script", "remove_lower", "remove_greater", "count_by_genre", "print_unique_oscars_count"};
    private String[] tables;

    public static boolean FLAG = false;
    private String[] languages = {"RU", "ENG", "LVA", "NOR"};
    private static int counter;

    public void setResourceBundle(ResourceBundle resourceBundle){
        this.resourceBundle = resourceBundle;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }


    public void setParent(Parent root){
        this.root = root;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setResourceBundle(resourceBundle);
        if (resourceBundle.getLocale().toString().equals("ru")){
            main_lang.setValue("RU");
            tables = new String[]{"Кол-во оскаров", "Длина", "Жанр", "Рейтинг", "Вес", "Страна", "Владелец"};
            choose_table.getItems().addAll(tables);

        }
        else if (resourceBundle.getLocale().toString().equals("en")){
            main_lang.setValue("ENG");
            tables = new String[]{"oscars_count", "length", "genre", "rating", "weight", "country", "owner"};
            choose_table.getItems().addAll(tables);

        }
        else if (resourceBundle.getLocale().toString().equals("lv")){
            main_lang.setValue("LVA");
            tables = new String[]{"Oskari skaitās", "garums", "žanrs", "reitings", "svars", "valsts", "īpašnieks"};
            choose_table.getItems().addAll(tables);
        }
        else if (resourceBundle.getLocale().toString().equals("no")){
            main_lang.setValue("NOR");
            tables = new String[]{"Oscar teller", "lengde", "sjanger", "vurdering", "vekt", "land", "Eieren"};
            choose_table.getItems().addAll(tables);
        }
        filter.setText(resourceBundle.getString("enter.your.value"));
        answers = new ArrayList<>();
        answers.add("коллекция пустая");
        answers.add("нет объектов, принадлежащих вам");
        answers.add("нет объектов с таким id, принадлежащим вам");
        answers.add("нет объектов, удовлетворяющих условию");
        answers.add("нет объектов, принадлежащих вам");
        answers.add("коллекция не была обновлена");
        answers.add("коллекция пустая, добавьте объекты");
        answers.add("фильмов с жанром");
        main_lang.getItems().addAll(languages);
        main_lang.setOnAction(this::changeLanguage);
        updating();
        table.setOnSort(tableViewSortEvent -> {
            counter++;
            FLAG = true;
            if (counter==3){
                counter = 0;
                FLAG = false;
            }
        });


        choiceBox.getItems().addAll(commands);
        help_button.setOnMouseEntered(event -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setText(this.resourceBundle.getString("help"));
            help_button.setTooltip(tooltip);
        });
        choiceBox.setOnAction(this::getCommand);

        table.setOnMouseClicked(this::doubleClickOnRow);
    }

    public void displayName(String username) {
        this.login = username;
        System.out.println(login.hashCode());
        System.out.println("я hex "+ hex);
        color.setStyle("-fx-control-inner-background: #" + hex);
        usernameLabel.setText(resourceBundle.getString("hello") + username);
    }

    public void setHex(String username){
        this.hex = String.valueOf(getColor(username));
    }

    public int getColor(String username){
        int code = username.hashCode();
        if (code < 0){
            code = -code;
        }
        while (String.valueOf(code).length() < 6){
            code *= 10;
            return code;
        }
        if (String.valueOf(code).length() > 6){
            code = Integer.parseInt(String.valueOf(code).substring(0,6));
            return code;
        }
        if (code < 0){
            code = -code;
        }
        return code;
    }

    public void drawRectange(int x, int y, String login){
        double x0 = canvas.getWidth();
        double y0 = canvas.getHeight();
        GraphicsContext context = canvas.getGraphicsContext2D();
        String color = String.valueOf(getColor(login));
        context.setFill(Color.web("#"+color));
        Rectangle rectangle = new Rectangle(x0/2-25+x, y0/2-25-y,50,50);
        draw(context, rectangle);
    }

    public void draw(GraphicsContext context, Rectangle rectangle){
        context.fillRect(rectangle.getX(), rectangle.getY(),rectangle.getHeight(), rectangle.getWidth());
    }

    public void animate(int x, int y, String login){
        double x0 = canvas.getWidth();
        double y0 = canvas.getHeight();
        GraphicsContext context = canvas.getGraphicsContext2D();
        String color = String.valueOf(getColor(login));
        context.setFill(Color.web("#"+color));
        Rectangle rectangle = new Rectangle(x0/2-25+x, y0/2-25-y,50,50);
        drawAnimate(context, rectangle);
    }

    public void drawAnimate(GraphicsContext context, Rectangle rectangle){
        long time = new Date().getTime();
        if (colorCounter == 0){
            context.fillRect(rectangle.getX(), rectangle.getY(),rectangle.getHeight(), rectangle.getWidth());
        }
        else {
            context.fillRect(rectangle.getX()+12.5, rectangle.getY()+12.5,rectangle.getHeight()/2, rectangle.getWidth()/2);
        }
    }

    public void drawCoordinatesSystem(){
        canvas.setHeight(370);
        canvas.setWidth(840);
        GraphicsContext context = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        context.setStroke(Color.BLACK);
        context.setLineWidth(1);
        //первые два параметра - координаты начала (х, у), вторые - координаты конца
        context.strokeLine(w / 2, 0, w / 2, h);
        context.strokeLine(0, h / 2, w, h / 2);
        context.strokeLine(0, 0, w, 0);
        context.strokeLine(0, h, w, h);
        context.strokeLine(0, 0, 0, h);
        context.strokeLine(w, 0, w, h);
//        System.out.println("Привет, я - "+hex);
    }


    public void setPassword(String password) {
        this.password = password;
    }
    public void setLogin(String login){
        this.login = login;
    }

    public void getCommand(ActionEvent ev) {
        cmdsWithoutArgs.add("clear");
        cmdsWithoutArgs.add("print_unique_oscars_count");
        cmdsWithMovie.add("add");
        cmdsWithMovie.add("update");
        cmdsWithMovie.add("remove_lower");
        cmdsWithMovie.add("remove_greater");
        String command = choiceBox.getValue();
        execute_command.setOnAction(event -> {
            if (command != null) {
                if (cmdsWithoutArgs.contains(command)) {
                    GetCommand getCommand = new GetCommand();
                    String[] cmd = {command};
                    try {
                        getCommand.getCmdAndSendToTheServer(this.socket, cmd, this.login, this.password, null);
                        String answer = new GetDataFromServer().getData(this.socket).toString();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(resourceBundle.getString("servers.answer"));
                        alert.setHeaderText(null);
                        alert.setContentText(initAns(answer));
                        alert.showAndWait();
                        System.out.println(answer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (command.equals("count_by_genre")) {
                    SceneController sceneController = new SceneController();
                    try {
                        sceneController.switchToCountByGenre(event, this.socket, this.login, this.password, this.resourceBundle);
                    } catch (IOException e) {
                        stage.close();
                        try {
                            sceneController.switchToRecconectScene(event, resourceBundle);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                } else if (cmdsWithMovie.contains(command)) {
                    if (command.equals("add")) {
                        SceneController sceneController = new SceneController();
                        try {
                            sceneController.switchToEditingAddingScene(event, this.socket, this.login, this.password, command, this.resourceBundle);
                        } catch (IOException e) {
                            stage.close();
                            try {
                                sceneController.switchToRecconectScene(event, resourceBundle);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else if (command.equals("remove_lower")) {
                        SceneController sceneController = new SceneController();
                        try {
                            sceneController.switchToEditingAddingScene(event, this.socket, this.login, this.password, command, this.resourceBundle);
                        } catch (IOException e) {
                            stage.close();
                            try {
                                sceneController.switchToRecconectScene(event, resourceBundle);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else if (command.equals("remove_greater")) {
                        SceneController sceneController = new SceneController();
                        try {
                            sceneController.switchToEditingAddingScene(event, this.socket, this.login, this.password, command, this.resourceBundle);
                        } catch (IOException e) {
                            stage.close();
                            try {
                                sceneController.switchToRecconectScene(event, resourceBundle);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } else if (command.equals("remove_by_id")) {
                    SceneController sceneController = new SceneController();
                    try {
                        sceneController.switchToRemoveByID(event, this.socket, this.login, this.password, this, this.resourceBundle);
                    } catch (IOException e) {
                        stage.close();
                        try {
                            sceneController.switchToRecconectScene(event, resourceBundle);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (command.equals("execute_script")) {
                    SceneController sceneController = new SceneController();
                    try {
                        sceneController.switchToScriptScene(event, this.socket, this.login, this.password, path, this.resourceBundle);
                    } catch (IOException e) {
                        stage.close();
                        try {
                            sceneController.switchToRecconectScene(event, resourceBundle);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("вы не выбрали команду");
                alert.show();
            }
        });
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public double canvasChosen(double x1, double y1, double x2, double y2){
        double x = Math.pow((x2 - x1), 2);
        double y = Math.pow((y2 - y1), 2);
        double result = Math.sqrt(x + y);
        return result;
    }

    public void loadTable() {
        if (filter.getText().equals(resourceBundle.getString("enter.your.value")) || filter.getText().isEmpty()) {
            FLAG = false;
        }
        try {
            list = FXCollections.observableList(new GetCollectionFromDB().getDataBase());
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            drawCoordinatesSystem();
            movie_id.setCellValueFactory(new PropertyValueFactory<>("movie_id"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            x.setCellValueFactory(new PropertyValueFactory<>("x"));
            y.setCellValueFactory(new PropertyValueFactory<>("y"));
            creation_date.setCellValueFactory(new PropertyValueFactory<>("creation_date"));
            oscars_count.setCellValueFactory(new PropertyValueFactory<>("oscars_count"));
            length.setCellValueFactory(new PropertyValueFactory<>("length"));
            genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            person_name.setCellValueFactory(new PropertyValueFactory<>("person_name"));
            birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
            weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
            country.setCellValueFactory(new PropertyValueFactory<>("country"));
            owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
            table.setItems(list);
            if (colorCounter == 0) {
                colorCounter = 1;
            } else {
                colorCounter = 0;
            }
            for (int i = 0; i < list.size(); i++) {
                Locale locale = resourceBundle.getLocale();
                DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);
                String[] numbers = list.get(i).getDate().split("-");
                String year = numbers[0];
                String month = numbers[1];
                String day = numbers[2];
                ZonedDateTime zonedDateTime;
                if (locale.toString().equals("ru") || locale.toString().equals("lv") || locale.toString().equals("no")){
                    list.get(i).setDate(day + "." + month + "." + year);
                }
                else {
                    list.get(i).setDate(month + "-" + day + "-" + year);
                }
                try {
                    zonedDateTime = ZonedDateTime.parse(list.get(i).getBirthday());
                    String zdn = zonedDateTime.format(f);
                    list.get(i).setBirthday(zdn);
                } catch (DateTimeParseException | NullPointerException e) {

                }

                if (list.get(i).getOwner().equals(login)) {
                    animate(list.get(i).getX(), list.get(i).getY(), list.get(i).getOwner());
                } else {
                    drawRectange(list.get(i).getX(), list.get(i).getY(), list.get(i).getOwner());
                }

            }
            canvas.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2) {
                    for (int i = 0; i < list.size(); i++) {
                        double res = -1;
                        double distance = canvasChosen(list.get(i).getX(), list.get(i).getY(), mouseEvent.getX() - canvas.getWidth() / 2, (-1) * (mouseEvent.getY() - canvas.getHeight() / 2));
                        if (distance < 25 * Math.sqrt(2)) {
                            ArrayList<Double> values = new ArrayList<>();
                            values.add(distance);
                            res = Collections.min(values);
                        }
                        if (res == distance) {
                            SceneController sceneController = new SceneController();
                            try {
                                if (list.get(i).getOwner().equals(login)) {
                                    try {
                                        sceneController.switchToUpdateScene(mouseEvent, this.socket, this.login, this.password, list.get(i), this.resourceBundle);
                                    } catch (IOException | NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        sceneController.switchToInfoScene(mouseEvent, this.socket, this.login, this.password, list.get(i), this.resourceBundle);
                                    } catch (IOException | NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                    }
                }
            });
            try {
                FilteredList<Product> filteredList = new FilteredList<>(list, b -> true);
                try {
                    filter.textProperty().addListener((observable, oldValue, newValue) -> {
                        filteredList.setPredicate(product -> {
                            if (newValue.isEmpty() || newValue == null) {
                                FLAG = false;
                                return true;
                            }
                            String keyword = newValue.toLowerCase();
                            if (choose_table.getValue() == null) {
                                return false;
                            } else {
                                FLAG = true;
                                if (product.getGenre() == null){
                                    product.setGenre(MovieGenre.NULL.toString());
                                }
                                if (product.getBirthday() == null) {
                                    product.setBirthday(String.valueOf(ZonedDateTime.now()));
                                }
                                if (product.getCountry() == null){
                                    product.setCountry(String.valueOf(Country.NULL));
                                }
                                if (product.getmovie_id().toString().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals(resourceBundle.getString("clm_id"))) {
                                    return true;
                                } else if (product.getTitle().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals(resourceBundle.getString("clm_title"))) {
                                    return true;
                                } else if (product.getX().toString().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals("x")) {
                                    return true;
                                } else if (product.getY().toString().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals("y")) {
                                    return true;
                                } else if (product.getDate().toString().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals(resourceBundle.getString("clm_date"))) {
                                    return true;
                                } else if (product.getOscarsCount().toString().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals(resourceBundle.getString("clm_oscars"))) {
                                    return true;
                                } else if (product.getLength().toString().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals(resourceBundle.getString("clm_length"))) {
                                    return true;
                                } else if (product.getGenre().toString().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals(resourceBundle.getString("clm_genre"))) {
                                    return true;
                                } else if (product.getRating().toString().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals(resourceBundle.getString("clm_rating"))) {
                                    return true;
                                } else if (product.getPerson_name().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals(resourceBundle.getString("clm_name"))) {
                                    return true;
                                } else if (product.getBirthday().toString().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals(resourceBundle.getString("clm_birthday"))) {
                                    return true;
                                } else if (product.getWeight().toString().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals(resourceBundle.getString("clm_weight"))) {
                                    return true;
                                } else if (product.getCountry().toString().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals(resourceBundle.getString("clm_country"))) {
                                    return true;
                                } else if (product.getOwner().toLowerCase().trim().indexOf(keyword) > -1 && choose_table.getValue().equals(resourceBundle.getString("clm_owner"))) {
                                    return true;
                                } else return false;
                            }
                        });
                    });
                    SortedList<Product> sortedList = new SortedList<>(filteredList);
                    sortedList.comparatorProperty().bind(table.comparatorProperty());
                    table.setItems(sortedList);
                } catch (NullPointerException e) {

                }
            } catch (IllegalArgumentException e) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

    }



    public void doubleClickOnRow(MouseEvent eve) {
        table.setOnMouseClicked(mouseEvent -> {
            Product product = table.getSelectionModel().getSelectedItem();
            try {
                if (mouseEvent.getClickCount() == 2) {
                    SceneController sceneController = new SceneController();
                    try {
                        if (product.getOwner().equals(login)) {
                            try {
//                        product.allInfo();
                                sceneController.switchToUpdateScene(mouseEvent, this.socket, this.login, this.password, product, this.resourceBundle);
                            } catch (IOException | NullPointerException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                sceneController.switchToInfoScene(mouseEvent, this.socket, this.login, this.password, product, this.resourceBundle);
                            } catch (IOException | NullPointerException e) {

                            }
                        }
                    } catch (NullPointerException e) {
                    }
                }
            }catch (NullPointerException e){

            }
        });
    }

    public void updating() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (!FLAG) {
                            loadTable();
                            try {
                                dynamicHead.setText(list.get(0).getTitle());
                            }catch (IndexOutOfBoundsException e) {}
                            String average = String.valueOf(list.stream().mapToDouble(Product::getLength).average().orElse(0));
                            if (average.length() > 7){
                                average_value.setText(average.substring(0,7));
                            }
                            else {
                                average_value.setText(average);
                            }
                            dynamicInfo.setText(resourceBundle.getString("list.class") + " class java.util.Collections$SynchronizedList" + "\n"+
                                                resourceBundle.getString("list.size")+ " " + list.size() + "\n");

                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 1000);
    }


    public void allowToUpdate(){
        table.setOnSort(tableViewSortEvent -> {
        });
    }



    public void changeLanguage(ActionEvent event){
        String language = main_lang.getValue();
        setLabels(language);
    }
    public void setLabels(String locale){
        if (locale.equals("NOR")){
            locale = "NO";
            tables = new String[]{"Oscar teller", "lengde", "sjanger", "vurdering", "vekt", "land", "Eieren"};
            choose_table.getItems().clear();
            choose_table.getItems().addAll(tables);
        }
        if (locale.equals("LVA")){
            locale = "LV";
            tables = new String[]{"Oskari skaitās", "garums", "žanrs", "reitings", "svars", "valsts", "īpašnieks"};
            choose_table.getItems().clear();
            choose_table.getItems().addAll(tables);
        }
        if (locale.equals("ENG")){
            locale = "en";
            tables = new String[]{"oscars_count", "length", "genre", "rating", "weight", "country", "owner"};
            choose_table.getItems().clear();
            choose_table.getItems().addAll(tables);
        }
        if (locale.equals("RU")){
            tables = new String[]{"Кол-во оскаров", "Длина", "Жанр", "Рейтинг", "Вес", "Страна", "Владелец"};
            choose_table.getItems().clear();
            choose_table.getItems().addAll(tables);
        }
        ResourceBundle resources = ResourceBundle.getBundle("bundles.Locale", new Locale(locale));
        usernameLabel.setText(resources.getString("hello") + " " + login);
        average_label.setText(resources.getString("average"));
        execute_command.setText(resources.getString("execute"));
        choose_cmd.setText(resources.getString("choose.command"));
        headLabel.setText(resources.getString("head"));
        movie_id.setText(resources.getString("clm_id"));
        title.setText(resources.getString("clm_title"));
        creation_date.setText(resources.getString("clm_date"));
        length.setText(resources.getString("clm_length"));
        genre.setText(resources.getString("clm_genre"));
        rating.setText(resources.getString("clm_rating"));
        oscars_count.setText(resources.getString("clm_oscars"));
        person_name.setText(resources.getString("clm_name"));
        birthday.setText(resources.getString("clm_birthday"));
        weight.setText(resources.getString("clm_weight"));
        country.setText(resources.getString("clm_country"));
        owner.setText(resources.getString("clm_owner"));
        stage.setTitle(resources.getString("main.title"));
        filter.setText(resources.getString("enter.your.value"));
        setResourceBundle(resources);
    }

    public String initAns(String answer){
        if (answers.contains(answer)){
            if (answer.equals(answers.get(0))){
                return resourceBundle.getString("empty.collection");
            }
            else if (answer.equals(answers.get(1))){
                return resourceBundle.getString("no.objects.for.you");
            }
            else if (answer.equals(answers.get(2))){
                return resourceBundle.getString("no.objects.for.you.with.id");
            }
            else if (answer.equals(answers.get(3))){
                return resourceBundle.getString("no.object.with.that.condition");
            }
            else if (answer.equals(answers.get(4))){
                return resourceBundle.getString("collection.wasnt.update");
            }
            else if(answer.equals(answers.get(5))){
                return resourceBundle.getString("collection.empty.add.objects");
            }
            else if (answer.contains(answers.get(6))){
                return resourceBundle.getString("movies.with.genre");
            }
        }
        return answer;
    }
}
