package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.Event;
import main.logic.User.Organizer;
import main.logic.User.Participant;
import main.logic.User.Participant;
import main.logic.User.User;
import main.logic.dao.JuryDAO;
import main.logic.dao.ModeratorDAO;
import main.logic.dao.ParticipantDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UserList implements Initializable, Controller {


    @FXML
    private AnchorPane add;

    @FXML
    private Text helloName;

    @FXML
    private Text helloText;

    @FXML
    private ImageView icon;

    @FXML
    private ImageView jury;

    @FXML
    private ImageView main;

    @FXML
    private ImageView participants;

    @FXML
    private ChoiceBox<Event> searchEvent;

    @FXML
    private TextField searchName;

    @FXML
    private TableView<Participant> table;
        
    private User user;

    public UserList(User user){
        this.user = user;
    }

    private String tableStyle = ("-fx-selection-bar: red;" +
            "-fx-selection-bar-non-focused: salmon;");

    private String columnStyle = ("-fx-alignment: CENTER; " +
            "-fx-background-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-color: gray;" +
            "-fx-font-size: 14pt;" +
            "-fx-font-family: 'Comic Sans MS';");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        icon.setImage(user.getPhoto().getImage());
        helloName.setText((user.getSex().contains("муж")?"Дорогой ":"Дорогая ") + user.getName());
        String hello = "";
        int hour = new Date().getHours();
        if (hour >= 5 && hour < 12) hello = "Доброе утро!";
        else if (hour >= 12 && hour < 17) hello = "Добрый день!";
        else if (hour >= 17 && hour < 24) hello = "Добрый вечер!";
        else if (hour < 5) hello = "Доброй ночи!";
        helloText.setText(hello);
        ParticipantDAO participantDAO = new ParticipantDAO();
        FilteredList<Participant> filteredList = new FilteredList<>(FXCollections.observableList(participantDAO.getAll()), p -> true);
        searchName.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (user.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });


        SortedList<Participant> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
        table.setStyle(tableStyle);

        TableColumn<Participant, ImageView> logoColumn = new TableColumn<Participant, ImageView>("Фото");
        logoColumn.setCellValueFactory(new PropertyValueFactory<Participant, ImageView>("photo"));
        logoColumn.setStyle(columnStyle);
        table.getColumns().add(logoColumn);

        TableColumn<Participant, String> nameColumn = new TableColumn<Participant, String>("ФИО");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Participant, String>("name"));
        nameColumn.setStyle(columnStyle);
        table.getColumns().add(nameColumn);

        TableColumn<Participant, String> emailColumn = new TableColumn<Participant, String>("E-mail");
        emailColumn.setCellValueFactory(new PropertyValueFactory<Participant, String>("email"));
        emailColumn.setStyle(columnStyle);
        table.getColumns().add(emailColumn);


        TableColumn<Participant, String> roleColumn = new TableColumn<Participant, String>("Телефон");
        roleColumn.setCellValueFactory(new PropertyValueFactory<Participant, String>("phone"));
        roleColumn.setStyle(columnStyle);
        table.getColumns().add(roleColumn);
    }

    @Override
    public void loadScene(Stage stage, String title) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/main/UserList.fxml"));
//        loader.setController(this);
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

    @FXML
    private void createUser(MouseEvent event){
        new RegUser(user).loadScene((Stage) table.getScene().getWindow(), "Регистрация участника");
    }

    @FXML
    void home(MouseEvent event) {
        new WindowOrg((Organizer) user).loadScene((Stage) table.getScene().getWindow(), "Главное окно организатора");
    }

    @FXML
    void jury(MouseEvent event) {
        new ModeratorsAndJuryController((Organizer) user).loadScene((Stage) table.getScene().getWindow(), "Жури\\Модероторы");
    }

    @FXML
    void profile(MouseEvent event) {
        new ProfileController(user).loadScene((Stage) table.getScene().getWindow(), "Профиль");
    }
}
