package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.logic.Event;
import main.logic.Person;
import main.server.SqlSender;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWinNoAuthController extends Application implements Initializable {
    public ChoiceBox choiceBox;
    public AnchorPane mainPain;

    @FXML
    private TableView eventTable;

    @Override
    public void start(Stage stage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/MainPage.fxml"));
        loader.setControllerFactory(param -> new MainWinNoAuthController());
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("MainWindow");
        stage.setResizable(false);
//        primaryStage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.show();
    }

    public void render(){
        new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                start(new Stage());
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Event> events = Event.getAllEvents();

        ObservableList<Event> obEvents = FXCollections.observableArrayList(events);

        TableView<Event> table = new TableView<Event>(obEvents);
        table.setLayoutX(39);
        table.setLayoutY(144);
        table.setPrefHeight(555);
        table.setPrefWidth(1140);

        TableColumn<Event, String> idColumn = new TableColumn<Event, String>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("id"));

        TableColumn<Event, String> idName = new TableColumn<Event, String>("Название");
        idName.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));

        TableColumn<Event, String> idDate = new TableColumn<Event, String>("Дата");
        idDate.setCellValueFactory(new PropertyValueFactory<Event, String>("date"));

        TableColumn<Event, String> idDays = new TableColumn<Event, String>("Продолжительность");
        idDays.setCellValueFactory(new PropertyValueFactory<Event, String>("days"));

        TableColumn<Event, String> idCity = new TableColumn<Event, String>("Город");
        idCity.setCellValueFactory(new PropertyValueFactory<Event, String>("city"));

        TableColumn<Event, String> idDirection = new TableColumn<Event, String>("Направление");
        idDirection.setCellValueFactory(new PropertyValueFactory<Event, String>("direction"));


        table.getColumns().add(idColumn);
        table.getColumns().add(idName);
        table.getColumns().add(idDate);
        table.getColumns().add(idDays);
        table.getColumns().add(idCity);
        table.getColumns().add(idDirection);

        mainPain.getChildren().add(table);
    }

    @FXML
    private void login(MouseEvent event){
        AuthController.loadScene((Stage) choiceBox.getScene().getWindow(), "Login");
    }

    public static void loadScene(Stage stage, String title){
        FXMLLoader loader = new FXMLLoader(MainWinNoAuthController.class.getResource("/main/MainPage.fxml"));
        loader.setControllerFactory(param -> new MainWinNoAuthController());
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
