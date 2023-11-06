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
import main.attentionWindow.AlertShow;
import main.logic.Activity;
import main.logic.City;
import main.logic.Direction;
import main.logic.Event;
import main.logic.User.Organizer;
import main.logic.User.User;
import main.logic.dao.CityDAO;
import main.logic.dao.DirectionDAO;
import main.logic.dao.EventDAO;


import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

import net.synedra.validatorfx.Validator;
import org.hibernate.Hibernate;

public class CreateEventController extends Controller {
    @FXML
    private ComboBox<City> city;
    @FXML
    private ComboBox<Direction> direction;
    @FXML
    public DatePicker startDate;

    @FXML
    public DatePicker endDate;

    @FXML
    private ComboBox<LocalTime> startTime;

    @FXML
    private ComboBox<LocalTime> endTime;

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


    private EventDAO eventDAO;
    private Organizer user;
    private Event newEvent;

    private Direction directionValue;
    private City cityValue;
    private Validator validator;
    private Validator validatorDateTime;
    private String tableStyle = ("-fx-selection-bar: red;" +
            "-fx-selection-bar-non-focused: salmon;");

    private String columnStyle = ("-fx-alignment: CENTER; " +
            "-fx-background-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-color: gray;" +
            "-fx-font-size: 10pt;" +
            "-fx-font-family: 'Comic Sans MS';");

    private LocalDate startDateOld;
    private LocalDate endDateOld;

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
        validator = new Validator();
        validatorDateTime = new Validator();

        icon.setImage(user.getPhoto().getImage());
        helloName.setText((user.getSex().contains("муж") ? "Дорогой " : "Дорогая ") + user.getName());
        String hello = "";
        int hour = new Date().getHours();
        if (hour >= 5 && hour < 12) hello = "Доброе утро!";
        else if (hour >= 12 && hour < 17) hello = "Добрый день!";
        else if (hour >= 17 && hour < 24) hello = "Добрый вечер!";
        else if (hour < 5) hello = "Доброй ночи!";
        helloText.setText(hello);
        table.setStyle(tableStyle);
        table.setRowFactory(t -> clickedTable());

        TableColumn<Activity, String> nameColumn = new TableColumn<Activity, String>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Activity, String>("name"));
        nameColumn.setStyle(columnStyle);
        table.getColumns().add(nameColumn);

        TableColumn<Activity, LocalDateTime> timeColumn = new TableColumn<Activity, LocalDateTime>("Время");
        timeColumn.setCellValueFactory(new PropertyValueFactory<Activity, LocalDateTime>("startTime"));
        timeColumn.setStyle(columnStyle);
        table.getColumns().add(timeColumn);


        city.setItems(FXCollections.observableList(new CityDAO().getAll()));
        direction.setItems(FXCollections.observableList(new DirectionDAO().getAll()));
        if (newEvent.getName() != null) {
            if(!Hibernate.isInitialized(newEvent.getActivity())) {
                eventDAO.openSession();
//                newEvent = eventDAO.merge(newEvent);
                eventDAO.refresh(newEvent);

                table.setItems(FXCollections.observableList(newEvent.getActivity()));
                eventName.setText(newEvent.getName());
                startDate.setValue(newEvent.getDateStartToDate().toLocalDate());
                startTime.setValue(newEvent.getDateStartToDate().toLocalTime());
                endDate.setValue(newEvent.getDateEndToDate().toLocalDate());
                endTime.setValue(newEvent.getDateEndToDate().toLocalTime());
                System.out.println(newEvent.getCity().getName());
                city.setValue(newEvent.getCity());
                cityValue = city.getValue();
                direction.setValue(newEvent.getDirection());
                directionValue = direction.getValue();
                eventDAO.closeSession();
            } else {
                table.setItems(FXCollections.observableList(newEvent.getActivity()));
                eventName.setText(newEvent.getName());
                startDate.setValue(newEvent.getDateStartToDate().toLocalDate());
                startTime.setValue(newEvent.getDateStartToDate().toLocalTime());
                endDate.setValue(newEvent.getDateEndToDate().toLocalDate());
                endTime.setValue(newEvent.getDateEndToDate().toLocalTime());
                System.out.println(newEvent.getCity().getName());
                city.setValue(newEvent.getCity());
                cityValue = city.getValue();
                direction.setValue(newEvent.getDirection());
                directionValue = direction.getValue();
            }
//            ...
        }


        validatorDateTime.createCheck()
                .dependsOn("dateStart", startDate.valueProperty())
                .dependsOn("dateEnd", endDate.valueProperty())
                .withMethod(c -> {
                    if (endDate.getValue() != null && startDate.getValue().isAfter(endDate.getValue())) {
                        c.error("Дата начала не может быть больше даты конца");
                    }
                }).decorates(startDate)
                .decorates(endDate)
                .immediate();

        validatorDateTime.createCheck()
                .dependsOn("startTime", startDate.valueProperty())
                .withMethod(c -> {
                    if (startDate.getValue() == null) {
                        c.error("Заполните дату начала мероприятия");
                    }
                }).decorates(startDate)
                .immediate();

        validatorDateTime.createCheck()
                .dependsOn("endTime", endDate.valueProperty())
                .withMethod(c -> {
                    if (endDate.getValue() == null) {
                        c.error("Заполните дату конца мероприятия");
                    }
                }).decorates(endDate)
                .immediate();


        validatorDateTime.createCheck()
                .dependsOn("startTime", startTime.valueProperty())
                .dependsOn("endTime", endTime.valueProperty())
                .withMethod(c -> {
                    if (startDate.getValue() != null && endDate.getValue() != null && endTime.getItems() != null) {
                        if (startDate.getValue().isEqual(endDate.getValue()) && endTime.getValue().isBefore(startTime.getValue())) {
                            c.error("Время конца не может быть раньше времени начала");
                        }
                    }
                }).decorates(startTime)
                .decorates(endTime)
                .immediate();

        validatorDateTime.createCheck()
                .dependsOn("startTime", startTime.valueProperty())
                .withMethod(c -> {
                    if (startTime.getValue() == null) {
                        c.error("Заполните время начала мероприятия");
                    }
                }).decorates(startTime)
                .immediate();

        validatorDateTime.createCheck()
                .dependsOn("endTime", endTime.valueProperty())
                .withMethod(c -> {
                    if (endTime.getValue() == null) {
                        c.error("Заполните время конца мероприятия");
                    }
                }).decorates(endTime)
                .immediate();

        validator.createCheck()
                .dependsOn("direction", direction.valueProperty())
                .withMethod(c -> {
                    if (direction.getValue() == null) {
                        c.error("Заполните направление мероприятия");
                    }
                }).decorates(direction)
                .immediate();

        validator.createCheck()
                .dependsOn("city", city.valueProperty())
                .withMethod(c -> {
                    if (city.getValue() == null) {
                        c.error("Заполните город мероприятия");
                    }
                }).decorates(city)
                .immediate();


    }

    public TableView<Activity> getTable() {
        return table;
    }

    @FXML
    void canban(ActionEvent event) {

    }

    @FXML
    void createCSV(ActionEvent event) {

    }

    private void editDate() {
        if (!newEvent.getActivity().isEmpty()) {
            AlertShow result = new AlertShow().showAlertConf("Вы уверены что хотите изменить дату?\n Все созданные активности сбросятся!");
            if (result.getConf().getButtonData() == ButtonType.NO.getButtonData()) {
                startDate.setValue(startDateOld);
                endDate.setValue(endDateOld);
            } else {
                table.getItems().clear();
            }
            System.out.println(result.getConf().getButtonData());
        }
        startDateOld = startDate.getValue();
        endDateOld = endDate.getValue();

    }

    @FXML
    void selectStartDate(ActionEvent event) {
        fillStartTime();
        editDate();
    }

    private void fillStartTime() {
        LocalTime localTime = LocalTime.of(6, 0);
        while (localTime.getHour() != 23 || localTime.getMinute() <= 50) {
            startTime.getItems().add(localTime);
            localTime = localTime.plusMinutes(5);
        }
    }

    @FXML
    void selectEndDate(ActionEvent event) {
        fillEndTime();
        editDate();
    }

    private void fillEndTime() {
        LocalTime localTime = LocalTime.of(6, 0);
        while (localTime.getHour() != 23 || localTime.getMinute() <= 50) {
            endTime.getItems().add(localTime);
            localTime = localTime.plusMinutes(5);
        }
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
        newEvent.getActivity().remove(table.getSelectionModel().getSelectedItem());
        table.getItems().remove(table.getSelectionModel().getSelectedItem());
    }

    void editActivity() {
        newEvent.getActivity().remove(table.getSelectionModel().getSelectedItem());
        new CreateActivityController(user, newEvent,table.getSelectionModel().getSelectedItem()).loadScene((Stage) startTime.getScene().getWindow(), "Создание активности");
    }

    @FXML
    void save(ActionEvent event) {
        if(!validator.containsErrors() && !validatorDateTime.containsErrors()){
            fillEvent();
            new EventDAO().create(newEvent);
            new WindowOrg(user).loadScene((Stage) table.getScene().getWindow(), "Окно организатора");
        } else {
            AlertShow.showAlert("info", "Ошибка", validator.createStringBinding().get() +
                    "\n"+
                    validatorDateTime.createStringBinding().get());
        }
    }

    @FXML
    void addAction(MouseEvent event) {
        if (!validatorDateTime.containsErrors()) {
            fillEvent();
            new CreateActivityController(user, newEvent).loadScene((Stage) startTime.getScene().getWindow(), "Создание активности");
        } else {
            AlertShow.showAlert("info", "Ошибка", validatorDateTime.createStringBinding().get());
        }
    }

    private void fillEvent() {
        newEvent.setOrganizer(user);
        newEvent.setName(eventName.getText());
        newEvent.setDateStart(startDate.getValue().atTime(startTime.getValue()));
        newEvent.setDateEnd(endDate.getValue().atTime(endTime.getValue()));
        newEvent.setCity(cityValue);
        newEvent.setDirection(directionValue);
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

