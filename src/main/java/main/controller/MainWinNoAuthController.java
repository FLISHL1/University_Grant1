package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.logic.Event;
import main.logic.dao.EventDAO;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWinNoAuthController extends Application implements Initializable {

    public AnchorPane mainPain;
    @FXML
    private DatePicker searchDate;

    @FXML
    private TextField searchDirection;

    private TableView<Event> table;
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
        EventDAO eventDAO = new EventDAO();
        eventDAO.init();
        List<Event> events = eventDAO.getAll();
        FilteredList<Event> filteredList = new FilteredList<>(FXCollections.observableList(events), p -> true);
        searchDirection.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(event -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (event.getDirection().toLowerCase().contains(lowerCaseFilter)) {
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

                if (event.getDate().toLowerCase().contains(formatDate)) {
                    return true;
                }
                return false;
            });
        });
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
        SortedList<Event> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
//        table.addEventHandler(MouseEvent.MOUSE_CLICKED, this::clickedTable);
        table.setRowFactory(t -> clickedTable());
        mainPain.getChildren().add(table);
    }

    private TableRow<Event> clickedTable(){
        TableRow<Event> row = new TableRow<>();
        row.setOnMouseClicked(event1 -> {
            if (event1.getClickCount() >= 2 && !row.isEmpty()){
                new EventInfoController(table.getSelectionModel().getSelectedItem()).loadScene((Stage) mainPain.getScene().getWindow(), "EventInfo");
//                new EventInfoController(table.getSelectionModel().getSelectedItem()).render();
            }
        });
        return row;
    }

    @FXML
    private void login(MouseEvent event){
         new AuthController().loadScene((Stage) mainPain.getScene().getWindow(), "Login");
         stoped();
    }
    public void stoped(){
        try {
            this.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void loadScene(Stage stage, String title){
        FXMLLoader loader = new FXMLLoader(MainWinNoAuthController.class.getResource("/main/MainPage.fxml"));
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
