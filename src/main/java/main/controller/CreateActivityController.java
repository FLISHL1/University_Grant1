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
import net.synedra.validatorfx.Validator;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;

import java.net.URL;
import java.time.LocalDateTime;
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
    private ComboBox<LocalDateTime> dateTime;

    @FXML
    private Button saveBtn;

    private Organizer user;
    private Activity newActivity;
    private Event newEvent;
    private Validator validator;
    private ActivityDAO activityDAO;
    private String tableStyle =
            ("-fx-selection-bar: red;" +
                    "-fx-selection-bar-non-focused: salmon;");

    private String columnStyle =
            ("-fx-alignment: CENTER; " +
                    "-fx-background-color: rgba(255, 255, 255, 0.5);" +
                    "-fx-border-color: gray;" +
                    "-fx-font-size: 14pt;" +
                    "-fx-font-family: 'Comic Sans MS';");

    public CreateActivityController(Organizer user, Event newEvent) {
        this.user = user;
        this.newActivity = new Activity();
        this.newEvent = newEvent;
    }

    public CreateActivityController(Organizer user, Event newEvent, Activity activity) {
        this.user = user;
        this.newActivity = activity;
        this.newEvent = newEvent;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(icon, helloText, helloName, user);
        JuryDAO juryDAO = new JuryDAO();
//        update for newActivity
        if (newActivity.getName() != null) {

            activityDAO = new ActivityDAO();
            activityDAO.openSession();
            activityDAO.refresh(newActivity);

            dateTime.setValue(newActivity.getStartTime());
            inputName.setText(newActivity.getName());
            tableSelected.setItems(FXCollections.observableList(newActivity.getJuries()));
            saveBtn.setText("Изменить активность");
            activityDAO.closeSession();
        }

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
        LocalDateTime date = newEvent.getDateStartToDate();
        while (!date.equals(newEvent.getDateEndToDate())) {
            dateTime.getItems().add(date);
            date = date.plusMinutes(5);
        }
//       Validator
        validator = new Validator();
        validator.createCheck()
                .dependsOn("name", inputName.textProperty())
                .withMethod(c -> {
                    String name = c.get("name");
                    if (name == null) {
                        c.error("Заполните название активности");
                    }
                }).decorates(inputName)
                .immediate();

        validator.createCheck()
                .dependsOn("time", dateTime.valueProperty())
                .withMethod(c -> {
                    if (dateTime.getValue() == null) {
                        c.error("Заполните время начала активности");
                    }
                }).decorates(dateTime)
                .immediate();
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
        new CreateEventController(user, newEvent).loadScene((Stage) table.getScene().getWindow(), "Создание активности");
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
        newEvent.getActivity().add(newActivity);
        newActivity.setName(inputName.getText());
        newActivity.setStartTime(dateTime.getValue());
        newActivity.setJuries(tableSelected.getItems());
//        if (Hibernate.isInitialized(newActivity)){
//            newEvent.getActivity().
//            activityDAO.update(newActivity);
//        }
        System.out.println(newActivity.getStartTime());
        back(event);
    }
}
