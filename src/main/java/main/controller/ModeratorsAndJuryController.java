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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.logic.Event;
import main.logic.User.User;
import main.logic.dao.JuryDAO;
import main.logic.dao.ModeratorDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ModeratorsAndJuryController implements Controller, Initializable {
    @FXML
    private AnchorPane mainPain;
    @FXML
    private TableView<User> table;
    @FXML
    private ChoiceBox searchEvent;

    @FXML
    private TextField searchDirection;
    private String tableStyle = ("-fx-selection-bar: red;" +
            "-fx-selection-bar-non-focused: salmon;");

    private String columnStyle = ("-fx-alignment: CENTER; " +
            "-fx-background-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-color: gray;" +
            "-fx-font-size: 14pt;" +
            "-fx-font-family: 'Comic Sans MS';");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JuryDAO juryDAO = new JuryDAO();
        ModeratorDAO moderatorDAO = new ModeratorDAO();
        List<User> userList = new ArrayList<>();
        userList.addAll(juryDAO.getAll());
        userList.addAll(moderatorDAO.getAll());
        FilteredList<User> filteredList = new FilteredList<>(FXCollections.observableList(userList), p -> true);
        searchDirection.textProperty().addListener((observable, oldValue, newValue) -> {
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


        SortedList<User> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
        table.setStyle(tableStyle);

        TableColumn<User, ImageView> logoColumn = new TableColumn<User, ImageView>("Фото");
        logoColumn.setCellValueFactory(new PropertyValueFactory<User, ImageView>("photo"));
        logoColumn.setStyle(columnStyle);
        table.getColumns().add(logoColumn);

        TableColumn<User, String> nameColumn = new TableColumn<User, String>("ФИО");
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        nameColumn.setStyle(columnStyle);
        table.getColumns().add(nameColumn);

        TableColumn<User, String> emailColumn = new TableColumn<User, String>("E-mail");
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        emailColumn.setStyle(columnStyle);
        table.getColumns().add(emailColumn);


        TableColumn<User, String> roleColumn = new TableColumn<User, String>("Роль");
        roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
        roleColumn.setStyle(columnStyle);
        table.getColumns().add(roleColumn);
    }

    @Override
    public void loadScene(Stage stage, String title) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/main/ModerJuryVisible.fxml"));
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
}
