package main.controller;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.Activity;
import main.logic.Event;
import main.logic.User.Moderation;
import main.logic.User.Organizer;
import main.logic.User.User;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KanBanController extends Controller {

    @FXML
    private ImageView account;

    @FXML
    private AnchorPane add;

    @FXML
    private ComboBox<Event> choiceEvent;

    @FXML
    private Text helloName;

    @FXML
    private Text helloText;

    @FXML
    private ImageView icon;

    @FXML
    private Button pdf;

    @FXML
    private TableView<Activity> tableLeft;

    @FXML
    private TableView<Activity> tableRight;

    private User user;

    private Event event;
    private String tableStyle = ("-fx-selection-bar: rgb(0, 0, 204);" +
            "-fx-selection-bar-non-focused: rgba(0, 0, 204, 0.3);");

    private String columnStyle = ("-fx-alignment: CENTER; " +
            "-fx-background-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-color: gray;" +
            "-fx-font-size: 10pt;" +
            "-fx-font-family: 'Comic Sans MS';");


    public KanBanController(User user, Event event){
        this.user = user;
        this.event = event;
    }

    @FXML
    void back(ActionEvent event) {
        new CreateEventController((Organizer) user, this.event).loadScene((Stage) tableLeft.getScene().getWindow(), "Создание мероприятия");
    }

    @FXML
    void home(MouseEvent event) {
        if (user instanceof Organizer)
            new WindowOrg((Organizer) user).loadScene((Stage) tableLeft.getScene().getWindow(), "Главное окно организатора");
        else
            new WindowModerator((Moderation) user).loadScene((Stage) tableLeft.getScene().getWindow(), "Главное окно модератора");
    }

    @FXML
    void jury(MouseEvent event) {

    }
    @FXML
    void userList(MouseEvent event) {
        new UserList(user).loadScene((Stage) tableLeft.getScene().getWindow(), "Участники");
    }
    @FXML
    void profile(MouseEvent event) {
        new ProfileController(user).loadScene((Stage) tableLeft.getScene().getWindow(), "Профиль");
    }
    private TableRow<Activity> clickedTableLeft() {
        TableRow<Activity> row = new TableRow<>();
        row.setOnMouseClicked(click -> {
                if (!tableRight.getItems().contains(tableLeft.getSelectionModel().getSelectedItem())) {
                    tableRight.getItems().add(tableLeft.getSelectionModel().getSelectedItem());
                    tableLeft.getItems().remove(tableLeft.getSelectionModel().getSelectedItem());
                }
        });
        return row;
    }

    private TableRow<Activity> clickedTableRight() {
        TableRow<Activity> row = new TableRow<>();
        row.setOnMouseClicked(click -> {
            if (click.getClickCount() >= 1 && !row.isEmpty()) {
                if (!tableLeft.getItems().contains(tableRight.getSelectionModel().getSelectedItem())) {
                    tableLeft.getItems().add(tableRight.getSelectionModel().getSelectedItem());
                    tableRight.getItems().remove(tableRight.getSelectionModel().getSelectedItem());

                }
            }
        });
        return row;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(icon, helloText, helloName, user);

        ArrayList<Activity> activities = new ArrayList<>(event.getActivity());
        System.out.println((activities.size())/2+1);
        System.out.println(activities.size());
//      tableLeft
        tableLeft.setItems(FXCollections.observableList(activities));
        tableLeft.setStyle(tableStyle);
        tableLeft.setRowFactory(t -> clickedTableLeft());

        TableColumn<Activity, String> nameColumn = new TableColumn<Activity, String>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Activity, String>("name"));
        nameColumn.setStyle(columnStyle);
        tableLeft.getColumns().add(nameColumn);

        TableColumn<Activity, LocalDateTime> timeColumn = new TableColumn<Activity, LocalDateTime>("Время");
        timeColumn.setCellValueFactory(new PropertyValueFactory<Activity, LocalDateTime>("startTime"));
        timeColumn.setStyle(columnStyle);
        tableLeft.getColumns().add(timeColumn);

//      tableRight
        tableRight.setStyle(tableStyle);
        tableRight.setRowFactory(t -> clickedTableRight());

        nameColumn = new TableColumn<Activity, String>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Activity, String>("name"));
        nameColumn.setStyle(columnStyle);
        tableRight.getColumns().add(nameColumn);

        timeColumn = new TableColumn<Activity, LocalDateTime>("Время");
        timeColumn.setCellValueFactory(new PropertyValueFactory<Activity, LocalDateTime>("startTime"));
        timeColumn.setStyle(columnStyle);
        tableRight.getColumns().add(timeColumn);

    }


    public void loadScene(Stage stage, String title) {
        super.loadScene("KanBan.fxml", stage, title);
    }
}

