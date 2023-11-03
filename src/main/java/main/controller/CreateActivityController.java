package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.Activity;
import main.logic.Event;
import main.logic.User.Jury;
import main.logic.User.Organizer;
import main.logic.User.Jury;
import main.logic.dao.ActivityDAO;
import main.logic.dao.JuryDAO;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateActivityController extends Controller {
    @FXML
    private Text helloName;

    @FXML
    private Text helloText;

    @FXML
    private ImageView icon;

    @FXML
    private TextField inputName;

    @FXML
    private TableView<Jury> table;

    @FXML
    private TableView<Jury> tableSelected;

    @FXML
    private ComboBox<Date> dateTime;

    private Organizer user;

    private Event createdEvent;
    private Activity newActivity;
    private CreateEventController createEventController;
    private String tableStyle =
            ("-fx-selection-bar: red;" +
                    "-fx-selection-bar-non-focused: salmon;");

    private String columnStyle =
            ("-fx-alignment: CENTER; " +
                    "-fx-background-color: rgba(255, 255, 255, 0.5);" +
                    "-fx-border-color: gray;" +
                    "-fx-font-size: 14pt;" +
                    "-fx-font-family: 'Comic Sans MS';");

    public CreateActivityController(Organizer user, Event event, CreateEventController createEventController) {
        this.user = user;
        this.createdEvent = event;
        this.createEventController = createEventController;
        this.newActivity = new Activity();
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
        JuryDAO juryDAO = new JuryDAO();
//      table
        table.setItems(FXCollections.observableList(juryDAO.getAll()));
        table.setStyle(tableStyle);
        table.setRowFactory(t -> clickedTable());


        TableColumn<Jury, String> nameColumn = new TableColumn<Jury, String>("ФИО");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Jury, String>("name"));
        nameColumn.setStyle(columnStyle);
        table.getColumns().add(nameColumn);


        TableColumn<Jury, String> phoneColumn = new TableColumn<Jury, String>("Телефон");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Jury, String>("phone"));
        phoneColumn.setStyle(columnStyle);
        table.getColumns().add(phoneColumn);

//      tableSelect
        tableSelected.setRowFactory(t -> clickedTableDelete());
        tableSelected.setStyle(tableStyle);
        nameColumn = new TableColumn<Jury, String>("ФИО");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Jury, String>("name"));
        nameColumn.setStyle(columnStyle);
        nameColumn.setMinWidth(324);
        tableSelected.getColumns().add(nameColumn);


        phoneColumn = new TableColumn<Jury, String>("Телефон");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Jury, String>("phone"));
        phoneColumn.setMinWidth(184);

        phoneColumn.setStyle(columnStyle);
        tableSelected.getColumns().add(phoneColumn);


//        ComboBox Time
        Date date = new Date(createEventController.);
        while (!date.equals(createdEvent.getDateEndToDate())){
            dateTime.getItems().add(date);
            date.setMinutes(date.getMinutes() + 5);
        }

    }

    private TableRow<Jury> clickedTableDelete() {
        TableRow<Jury> row = new TableRow<>();
        row.setOnMouseClicked(click -> {
            if (click.getClickCount() >= 2 && !row.isEmpty()) {
                tableSelected.getItems().remove(tableSelected.getSelectionModel().getSelectedItem());
            }
        });
        return row;
    }

    public void loadScene(Stage stage, String title) {
        super.loadScene("CreateActivity.fxml", stage, title);
    }

    private TableRow<Jury> clickedTable() {
        TableRow<Jury> row = new TableRow<>();
        row.setOnMouseClicked(click -> {
            if (click.getClickCount() >= 2 && !row.isEmpty()) {
                if (!tableSelected.getItems().contains(table.getSelectionModel().getSelectedItem()))
                    tableSelected.getItems().add(table.getSelectionModel().getSelectedItem());
            }
        });
        return row;
    }

    @FXML
    void back(ActionEvent event) {
        createEventController.loadScene((Stage) table.getScene().getWindow(), "Создание активности");
    }

    @FXML
    void getName(ActionEvent event) {
//        newActivity.setName(inputName.getText());
    }

    @FXML
    void home(MouseEvent event) {

    }

    @FXML
    void jury(MouseEvent event) {

    }

    @FXML
    void participants(MouseEvent event) {

    }

    @FXML
    void profile(MouseEvent event) {

    }

    @FXML
    void save(ActionEvent event) {
        newActivity.setName(inputName.getText());
//        newActivity.setStartTime(time.getValue());
        newActivity.setStartTime(new Date());
        newActivity.setJuries(tableSelected.getItems());
        createdEvent.getActivity().add(newActivity);
        back(event);
    }
}
