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
import org.hibernate.Hibernate;

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

    private String sDate;
    private String sDirection;
    private boolean firstInit = false;
    private String tableStyle = ("-fx-selection-bar: rgb(0, 0, 204);" +
            "-fx-selection-bar-non-focused: rgba(0, 0, 204, 0.3);");

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
        nameColumn.setMaxWidth(650);
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
        firstInit = true;
    }


    private boolean sort(Event event) {
        return sortDate(event) && sortDirection(event);
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
        activityDAO = new ActivityDAO();
        TableRow<Event> row = new TableRow<Event>() {
            @Override
            protected void updateItem(Event event, boolean b) {
                super.updateItem(event, b);
                if (event == null) return;
                eventDAO.openSession();

                if (!Hibernate.isInitialized(event.getActivity()))
                    eventDAO.refresh(event);

                Activity activity = null;
                activityDAO.openSession();
                for (Activity activity1 : event.getActivity()) {
                    if (!Hibernate.isInitialized(activity1))
                        activityDAO.refresh(activity1);
                    if (!activity1.getApplications().isEmpty()) {
                        this.setStyle("-fx-background-color: rgba(0, 0, 204, 0.7);");
                        activity = activity1;
                        break;
                    } else {
                        this.setStyle("");
                    }
                }
                activityDAO.closeSession();
                if (activity != null) {
                    ContextMenu rowMenu = new ContextMenu();
                    MenuItem application = new MenuItem("Подтвердить участие");
                    application.setOnAction(event1 -> {
                        new ApplyActivity(event).render();
                    });
                    rowMenu.getItems().add(application);
                    contextMenuProperty().bind(
                            Bindings.when(emptyProperty())
                                    .then((ContextMenu) null)
                                    .otherwise(rowMenu));
                }
                eventDAO.closeSession();
            }
        };

        row.setOnMouseClicked(event1 -> {
            if (event1.getClickCount() >= 2 && !row.isEmpty()) {
                new CreateEventController(user, table.getSelectionModel().getSelectedItem()).loadScene((Stage) mainPain.getScene().getWindow(), "EventInfo");
            }
        });
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
