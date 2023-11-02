package main.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.Activity;
import main.logic.City;
import main.logic.Direction;
import main.logic.Event;
import main.logic.User.Organizer;
import main.logic.dao.CityDAO;
import main.logic.dao.DirectionDAO;
import main.logic.dao.EventDAO;


import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateEventController extends Controller {
    @FXML
    private Text activities;
    @FXML
    private ComboBox<City> city;
    @FXML
    private ComboBox<Direction> direction;

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox<Date> endTime;

    @FXML
    private TextField eventName;

    @FXML
    private Text helloName;

    @FXML
    private Text helloText;

    @FXML
    private ImageView icon;

    @FXML
    private TableView<Activity> table;

    @FXML
    private DatePicker startDate;

    @FXML
    private ComboBox<String> startTime;

    @FXML
    private Text time;
    private EventDAO eventDAO;
    private Organizer user;
    private Event newEvent;

    private Direction directionValue;
    private City cityValue;
    private String tableStyle = ("-fx-selection-bar: red;" +
            "-fx-selection-bar-non-focused: salmon;");

    private String columnStyle = ("-fx-alignment: CENTER; " +
            "-fx-background-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-color: gray;" +
            "-fx-font-size: 10pt;" +
            "-fx-font-family: 'Comic Sans MS';");


    public CreateEventController(Organizer user) {
        this.user = user;
        eventDAO = new EventDAO();
        newEvent = new Event();
    }

    public CreateEventController(Organizer user, Event editableEvent) {
        this.user = user;
        eventDAO = new EventDAO();
        this.newEvent = editableEvent;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        icon.setImage(user.getPhoto().getImage());
        helloName.setText((user.getSex().contains("муж") ? "Дорогой " : "Дорогая ") + user.getName());
        String hello = "";
        int hour = new Date().getHours();
        if (hour >= 5 && hour < 12) hello = "Доброе утро!";
        else if (hour >= 12 && hour < 17) hello = "Добрый день!";
        else if (hour >= 17 && hour < 24) hello = "Добрый вечер!";
        else if (hour < 5) hello = "Доброй ночи!";
        helloText.setText(hello);
        table.setItems(FXCollections.observableList(newEvent.getActivity()));
        table.setStyle(tableStyle);
        table.setRowFactory(t -> clickedTable());
        TableColumn<Activity, String> nameColumn = new TableColumn<Activity, String>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Activity, String>("name"));
        nameColumn.setStyle(columnStyle);
        table.getColumns().add(nameColumn);

        TableColumn<Activity, String> timeColumn = new TableColumn<Activity, String>("Время");
        timeColumn.setCellValueFactory(new PropertyValueFactory<Activity, String>("startTime"));
        timeColumn.setStyle(columnStyle);
        table.getColumns().add(timeColumn);


        city.setItems(FXCollections.observableList(new CityDAO().getAll()));
        direction.setItems(FXCollections.observableList(new DirectionDAO().getAll()));
        if (newEvent.getId() != null) {
            eventName.setText(newEvent.getName());
//            ...
        }
    }
    public TableView<Activity> getTable(){ return table;}

    @FXML
    void canban(ActionEvent event) {

    }

    @FXML
    void createCSV(ActionEvent event) {

    }

    @FXML
    void selectEndDate(ActionEvent event) {

    }

    private TableRow<Activity> clickedTable() {
        TableRow<Activity> row = new TableRow<>();
        row.setOnMouseClicked(activity -> {
            if (activity.getClickCount() >= 2 && !row.isEmpty()) {
                editActivity();
            }
        });
        return row;
    }

    @FXML
    void delAction(MouseEvent event) {
        System.out.println(table.getSelectionModel().getSelectedItem().getName());
        newEvent.getActivity().remove(table.getSelectionModel().getSelectedItem());
        table.getItems().remove(table.getSelectionModel().getSelectedItem());
    }

    void editActivity() {
//        new CreateActivityController(this, table.getSelectionModel().getSelectedItem()).loadScene((Stage) startTime.getScene().getWindow(), "Создание активности");
    }

    @FXML
    void save(ActionEvent event) {

    }

    @FXML
    void addAction(MouseEvent event) {
        new CreateActivityController(user, newEvent, this).loadScene((Stage) startTime.getScene().getWindow(), "Создание активности");
    }

    @FXML
    void choiceCity(ActionEvent event) {
        for (City city1 : city.getItems()) {
            if (city1.getName().equals(city.getEditor().getText())) {
                cityValue = city1;
                return;
            }
        }
        cityValue = new City(city.getEditor().getText());
    }

    @FXML
    void choiseDirection(ActionEvent event) {
        for (Direction direction1 : direction.getItems()) {
            if (direction1.name.equals(direction.getEditor().getText())) {
                directionValue = direction1;
                return;
            }
        }
        directionValue = new Direction(direction.getEditor().getText());
    }

    @FXML
    void home(MouseEvent event) {
        new WindowOrg(user).loadScene((Stage) startTime.getScene().getWindow(), "Главное окно организатора");
    }

    @FXML
    void jury(MouseEvent event) {
        new ModeratorsAndJuryController(user).loadScene((Stage) startTime.getScene().getWindow(), "Жюри\\Модераторы");
    }

    @FXML
    void participants(MouseEvent event) {
        new UserList(user).loadScene((Stage) startTime.getScene().getWindow(), "Участники");
    }

    @FXML
    void profile(MouseEvent event) {
        new ProfileController(user).loadScene((Stage) startTime.getScene().getWindow(), "Профиль");
    }


    public void loadScene(Stage stage, String title) {
        super.loadScene("CreateEvent.fxml", stage, title);
    }


}

