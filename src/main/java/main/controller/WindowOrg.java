package main.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.Activity;
import main.logic.Direction;
import main.logic.Event;
import main.logic.dao.ActivityDAO;
import main.logic.dao.EventDAO;
import main.logic.User.Organizer;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WindowOrg extends Controller {

    public AnchorPane mainPain;
    public Text helloText;
    public ImageView logotype;
    public Text helloName;
    @FXML
    private TextField searchDirection;
    @FXML
    private DatePicker searchDate;
    @FXML
    private ImageView icon;
    private Organizer user;
    private EventDAO eventDAO;

    private ActivityDAO activityDAO;
    @FXML
    private TableView<Event> table;
    private String tableStyle = ("-fx-selection-bar: red;" +
            "-fx-selection-bar-non-focused: salmon;");

    private String columnStyle = ("-fx-alignment: CENTER; " +
            "-fx-background-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-color: gray;" +
            "-fx-font-size: 10pt;" +
            "-fx-font-family: 'Comic Sans MS';");

    public WindowOrg(Organizer user) {
        this.user = user;
    }

    /*@Override
    public void start(Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/WindowOrg.fxml"));
        loader.setController(this);
        loader.setControllerFactory(param -> this);
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("Главное окно организатора");
        stage.setResizable(false);
//        primaryStage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.show();
    }

    public void render() {
        new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                start(new Stage());
            }
        });
    }*/

    public void loadScene(Stage stage, String title) {
        FXMLLoader loader = new FXMLLoader(MainWinNoAuthController.class.getResource("/main/WindowOrg.fxml"));
        loader.setController(this);
        loader.setControllerFactory(param -> this);
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle(title);
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(icon, helloText, helloName, user);
        eventDAO = new EventDAO();
        List<Event> events = eventDAO.getAll();
        FilteredList<Event> filteredList = new FilteredList<>(FXCollections.observableList(events), p -> true);
        searchDirection.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(event -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (event.getDirection().getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        searchDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(event -> {
                if (newValue == null || newValue.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).isEmpty()) {
                    return true;
                }

                String formatDate = newValue.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

                if (event.getDateStart().toLowerCase().contains(formatDate)) {
                    return true;
                }
                return false;
            });
        });

        table.setStyle(tableStyle);

        TableColumn<Event, ImageView> logoColumn = new TableColumn<Event, ImageView>("Логотип");
        logoColumn.setCellValueFactory(new PropertyValueFactory<Event, ImageView>("logo"));
        logoColumn.setStyle(columnStyle);
        table.getColumns().add(logoColumn);

        TableColumn<Event, String> nameColumn = new TableColumn<Event, String>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
        nameColumn.setStyle(columnStyle);
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


    private TableRow<Event> clickedTable() {
        activityDAO = new ActivityDAO();
        TableRow<Event> row = new TableRow<>();
        Event event = table.getSelectionModel().getSelectedItem();
        eventDAO.openSession();
        eventDAO.refresh(event);
        Activity activity = null;
        for (Activity activity1: event.getActivity()){
            activity1 = activityDAO.merge(activity1);
            if (!activity1.getApplications().isEmpty()){
                row.setStyle("-fx-background-color: rgba(0, 0, 204, 0.7);");
                activity = activity1;
                break;
            }
        }
        if (activity != null){
            ContextMenu rowMenu = new ContextMenu();
            MenuItem application = new MenuItem("Подтвердить участие");
            application.setOnAction(event1 -> {
                new ApplyActivity(table.getSelectionModel().getSelectedItem()).render();
            });
            rowMenu.getItems().add(application);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(rowMenu));
        }
        row.setOnMouseClicked(event1 -> {
            if (event1.getClickCount() >= 2 && !row.isEmpty()) {
                new CreateEventController(user, table.getSelectionModel().getSelectedItem()).loadScene((Stage) mainPain.getScene().getWindow(), "EventInfo");
            }
        });
        eventDAO.closeSession();
        return row;

    }

    @FXML
    private void createEvent(MouseEvent event) {
        new CreateEventController(user).loadScene((Stage) mainPain.getScene().getWindow(), "Создать мероприятие");

    }

    @FXML
    private void jury(MouseEvent event) {
        new ModeratorsAndJuryController(user).loadScene((Stage) mainPain.getScene().getWindow(), "Жури\\Модероторы");
    }

    @FXML
    private void users(MouseEvent event) {
        new UserList(user).loadScene((Stage) mainPain.getScene().getWindow(), "Участники");
    }

    @FXML
    private void profile(MouseEvent event) {
        new ProfileController(user).loadScene((Stage) mainPain.getScene().getWindow(), "Профиль");
    }
}
