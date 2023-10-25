package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.logic.Event;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWinNoAuthController extends Application implements Initializable {

    public AnchorPane mainPain;

    @FXML
    private TableView eventTable;

    @Override
    public void start(Stage stage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/MainPage.fxml"));
        loader.setController(new MainWinNoAuthController());
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

        GenerateTable<Event> table = new GenerateTable<Event>(events)
                .setLayoutX(39)
                .setLayoutY(144)
                .setPrefHeight(555)
                .setPrefWidth(1140);
        table.delColumn("days");
        table.delColumn("id");
        table.delColumn("city");
        table.getColumn("name").setText("Название");





        mainPain.getChildren().add(table.generateTable());
    }

    @FXML
    private void login(MouseEvent event){
        AuthController.loadScene((Stage) mainPain.getScene().getWindow(), "Login");
    }

    public static void loadScene(Stage stage, String title){
        FXMLLoader loader = new FXMLLoader(MainWinNoAuthController.class.getResource("/main/MainPage.fxml"));
        loader.setController(new MainWinNoAuthController());
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
