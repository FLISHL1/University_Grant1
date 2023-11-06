package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.Activity;
import main.logic.Direction;
import main.logic.Event;
import main.logic.User.Moderation;
import main.logic.dao.ActivityDAO;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class WindowModerator extends Controller {

    @FXML
    private AnchorPane add;

    @FXML
    private ComboBox<Event> choiceEvent;

    @FXML
    private ComboBox<Direction> choiceDirection;

    @FXML
    private Text helloName;

    @FXML
    private Text helloText;

    @FXML
    private ImageView icon;

    @FXML
    private TableView<Activity> table;

    private Moderation user;
    private String tableStyle = ("-fx-selection-bar: red;" +
            "-fx-selection-bar-non-focused: salmon;");

    private String columnStyle = ("-fx-alignment: CENTER; " +
            "-fx-background-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-color: gray;" +
            "-fx-font-size: 10pt;" +
            "-fx-font-family: 'Comic Sans MS';");

    public WindowModerator(Moderation user) {
        this.user = user;
    }

    @FXML
    void moderatorAcctivity(ActionEvent event) {

    }

    @FXML
    void home(MouseEvent event) {

    }

    @FXML
    void profile(MouseEvent event) {

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
        List<Activity> activity = new ActivityDAO().getAll();
        FilteredList<Activity> filteredList = new FilteredList<>(FXCollections.observableList(activity), p -> true);
        choiceDirection.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(activity1 -> {
                if (newValue == null) {
                    return true;
                }

                if (activity1.getEvent().getDirection().equals(newValue)) {
                    return true;
                }
                return false;
            });
        });

        choiceEvent.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(event -> {
                if (newValue == null) {
                    return true;
                }

                if (event.equals(newValue)) {
                    return true;
                }
                return false;
            });
        });

        table.setStyle(tableStyle);

        TableColumn<Activity, String> nameColumn = new TableColumn<Activity, String>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Activity, String>("name"));
        nameColumn.setStyle(columnStyle);
        table.getColumns().add(nameColumn);

        TableColumn<Activity, LocalDateTime> dateColumn = new TableColumn<Activity, LocalDateTime>("Дата и время");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Activity, LocalDateTime>("startTime"));
        dateColumn.setStyle(columnStyle);
        table.getColumns().add(dateColumn);


        SortedList<Activity> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
//        table.addEventHandler(MouseEvent.MOUSE_CLICKED, this::clickedTable);
//        table.setRowFactory(t -> clickedTable());
    }

    public void loadScene(Stage stage, String title) {
        super.loadScene("WindowModerator.fxml", stage, title);
    }
}

