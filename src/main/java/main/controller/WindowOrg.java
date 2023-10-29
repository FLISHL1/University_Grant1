package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.Event;
import main.logic.dao.EventDAO;
import main.logic.User.Organizer;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WindowOrg extends Application implements Initializable, Controller{

    public AnchorPane mainPain;
    public Text helloText;
    public ImageView logotype;
    public Text name;
    Organizer user;
    private TableView<Event> table;

    public WindowOrg(Organizer user){
        this.user = user;
    }
    @Override
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
        name.setText((user.getSex().contains("муж")?"Дорогой ":"Дорогая ") + user.getName());
        String hello = "";
        int hour = new Date().getHours();
        if (hour >= 5 && hour < 12) hello = "Доброе утро!";
        else if (hour >= 12 && hour < 16) hello = "Добрый день!";
        else if (hour >= 16 && hour < 24) hello = "Доброе вечер!";
        else if (hour >= 24 && hour < 5) hello = "Доброй ночи!";
        helloText.setText(hello);
        EventDAO eventDAO = new EventDAO();
        List<Event> events = eventDAO.getAll();

        GenerateTable<Event> genTable = new GenerateTable<Event>(new ArrayList<>(events))
                .setLayoutX(39)
                .setLayoutY(144)
                .setPrefHeight(555)
                .setPrefWidth(1140);
        genTable.delColumn("days");
        genTable.delColumn("id");
        genTable.delColumn("city");
        genTable.delColumn("organizer");
        genTable.delColumn("description");
        genTable.getColumn("name").setText("Название");
        genTable.getColumn("logo").setText("Логотип");
        genTable.getColumn("date").setText("Дата");
        genTable.getColumn("direction").setText("Направление");
        table = genTable.generateTable();

//        table.addEventHandler(MouseEvent.MOUSE_CLICKED, this::clickedTable);
        table.setRowFactory(t -> clickedTable());
        mainPain.getChildren().add(table);
    }
    private TableRow<Event> clickedTable(){
        TableRow<Event> row = new TableRow<>();
        row.setOnMouseClicked(event1 -> {
            if (event1.getClickCount() >= 2 && !row.isEmpty()){
//                EventInfoController.loadScene((Stage) mainPain.getScene().getWindow(), "EventInfo", table.getSelectionModel().getSelectedItem());
                new EventInfoController(table.getSelectionModel().getSelectedItem()).render();
            }
        });
        return row;
    }

    @FXML
    private void profile(MouseEvent event){
        new ProfileController(user).loadScene((Stage) mainPain.getScene().getWindow(), "Профиль");
    }
}
