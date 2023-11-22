package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.attentionWindow.AlertShow;
import main.logic.Direction;
import main.logic.Event;
import main.logic.User.Participant;
import main.logic.User.User;
import main.logic.dao.EventDAO;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class WindowPart extends Controller {

    public AnchorPane mainPain;
    @FXML
    private DatePicker searchDate;

    @FXML
    private TextField searchDirection;
    @FXML
    private Text helloText;
    @FXML
    private Text helloName;
    @FXML
    private TableView<Event> table;
    private String tableStyle = ("-fx-selection-bar: rgb(0, 0, 204);" +
            "-fx-selection-bar-non-focused: rgba(0, 0, 204, 0.3);");

    private String columnStyle = ("-fx-alignment: CENTER; " +
            "-fx-background-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-color: gray;" +
            "-fx-font-size: 10pt;" +
            "-fx-font-family: 'Comic Sans MS';");

    private String sDirection;

    private String sDate;

    private User user;
    public WindowPart (User user){
        this.user = user;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(null, helloText, helloName, user);
        EventDAO eventDAO = null;
        try {
            eventDAO = new EventDAO();
        } catch (Throwable e) {
            AlertShow alertShow = new AlertShow();
            alertShow.showAlertErorr("Подключние к Базе Данных отсутствует");
            alertShow.getConf();
            System.exit(1);
        }
        List<Event> events = eventDAO.getAll();
        FilteredList<Event> filteredList = new FilteredList<>(FXCollections.observableList(events), p -> true);
        searchDirection.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(this::sort);
        });

        searchDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(this::sort);
        });
        table.setStyle(tableStyle);

        TableColumn<Event, ImageView> logoColumn = new TableColumn<Event, ImageView>("Логотип");
        logoColumn.setCellValueFactory(new PropertyValueFactory<Event, ImageView>("logo"));
        logoColumn.setStyle(columnStyle);
        table.getColumns().add(logoColumn);

        TableColumn<Event, String> nameColumn = new TableColumn<Event, String>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
        nameColumn.setStyle(columnStyle);
        nameColumn.setMaxWidth(610);
        table.getColumns().add(nameColumn);

        TableColumn<Event, String> dateColumn = new TableColumn<Event, String>("Дата");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("dateStart"));
        dateColumn.setStyle(columnStyle);
        table.getColumns().add(dateColumn);

        TableColumn<Event, Direction> directionColumn = new TableColumn<Event, Direction>("Направление");
        directionColumn.setCellValueFactory(new PropertyValueFactory<Event, Direction>("direction"));
        directionColumn.setStyle(columnStyle);
        table.getColumns().add(directionColumn);


        SortedList<Event> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
//        table.addEventHandler(MouseEvent.MOUSE_CLICKED, this::clickedTable);
        table.setRowFactory(t -> clickedTable());
    }

    private boolean sort(Event event) {
        if (sortDate(event) && sortDirection(event)) {
            return true;
        }
        return false;
    }

    private boolean sortDirection(Event event) {
        if (searchDirection.getText() == null || searchDirection.getText().isEmpty()) {
            return true;
        }

        sDirection = searchDirection.getText().toLowerCase();


        return event.getDirection().getName().toLowerCase().contains(sDirection);
    }

    private boolean sortDate(Event event) {
        if (searchDate.getValue() == null || searchDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).isEmpty()) {
            return true;
        }

        sDate = searchDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));


        return event.getDateStart().toLowerCase().contains(sDate);
    }

    private TableRow<Event> clickedTable() {
        TableRow<Event> row = new TableRow<>();
        row.setOnMouseClicked(event1 -> {
            if (event1.getClickCount() >= 2 && !row.isEmpty()) {
                new EventInfoController(table.getSelectionModel().getSelectedItem()).loadScene((Stage) mainPain.getScene().getWindow(), "EventInfo");
//                new EventInfoController(table.getSelectionModel().getSelectedItem()).render();
            }
        });
        return row;
    }

    @FXML
    private void login(MouseEvent event) {
        new ProfileController(user).loadScene((Stage) mainPain.getScene().getWindow(), "Login");
    }

    public void loadScene(Stage stage) {
        super.loadSceneWithController("WindowPart.fxml", stage, user instanceof Participant? "Главное окон участника":"Главное окон жюри");
    }


}
