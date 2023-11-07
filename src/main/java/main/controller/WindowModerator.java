package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.attentionWindow.AlertShow;
import main.logic.Activity;
import main.logic.Application;
import main.logic.Direction;
import main.logic.Event;
import main.logic.User.Moderation;
import main.logic.dao.ActivityDAO;
import main.logic.dao.ApplicationDAO;
import main.logic.dao.DirectionDAO;
import main.logic.dao.EventDAO;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class WindowModerator extends Controller {

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
            "-fx-font-size: 15pt;" +
            "-fx-font-family: 'Comic Sans MS';");

    public WindowModerator(Moderation user) {
        this.user = user;
    }

    @FXML
    void moderatorAcctivity(ActionEvent event) {

    }
    @FXML
    void addApplication(ActionEvent event) {
        AlertShow alertShow = new AlertShow();
        alertShow.showAlertConf("Отправить заявку на мероприятие?:\n" + table.getSelectionModel().getSelectedItem().getName());
        if (alertShow.getConf() == ButtonType.YES) {
            ApplicationDAO applicationDAO = new ApplicationDAO();
            Application newApplication = new Application();
            newApplication.setIdModerator(user);
            newApplication.setActivity(table.getSelectionModel().getSelectedItem());
            newApplication.setStatus("new");
            applicationDAO.create(newApplication);
        }
    }

    @FXML
    void home(MouseEvent event) {

    }

    @FXML
    void profile(MouseEvent event) {
        new ProfileController(user).loadScene((Stage) table.getScene().getWindow(), "Профиль");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(icon, helloText, helloName, user);

//      tableInit
        List<Activity> activity = new ActivityDAO().getAll();
        FilteredList<Activity> filteredList = new FilteredList<>(FXCollections.observableList(activity), p -> true);
        choiceDirection.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(activity1 -> {
                if (newValue == null) {
                    return true;
                }
                ActivityDAO activityDAO = new ActivityDAO();
                activityDAO.openSession();
                activity1 = activityDAO.merge(activity1);
                if (activity1.getEvent().getDirection().getId().equals(newValue.getId())) {
                    return true;
                }
                return false;
            });
        });

        choiceEvent.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(activity1 -> {
                if (newValue == null) {
                    return true;
                }

                if (activity1.getEvent().getId().equals(newValue.getId())) {
                    return true;
                }
                return false;
            });
        });

        table.setStyle(tableStyle);

        TableColumn<Activity, String> nameColumn = new TableColumn<Activity, String>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Activity, String>("name"));
        nameColumn.setStyle(columnStyle);
        nameColumn.setMinWidth(714);
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
//      choiceInit

        choiceDirection.getItems().add(null);
        choiceDirection.getItems().addAll(FXCollections.observableList(new DirectionDAO().getAll()));

        choiceEvent.getItems().add(null);
        choiceEvent.getItems().addAll(FXCollections.observableList(new EventDAO().getAll()));

    }

    public void loadScene(Stage stage, String title) {
        super.loadScene("WindowModerator.fxml", stage, title);
    }
}

