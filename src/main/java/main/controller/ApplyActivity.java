package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.logic.Activity;
import main.logic.Confirmation;
import main.logic.Event;
import main.logic.User.Moderation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ApplyActivity extends Application implements Initializable {

    @FXML
    private ComboBox<Activity> choiceActivity;

    @FXML
    private Button saveBtn;

    @FXML
    private TableView<Moderation> table;

    private Event event;
    public ApplyActivity(Event event){
        this.event = event;
    }

    @Override
    public void start(Stage stage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/ApplyApplication.fxml"));
        loader.setControllerFactory(param -> this);
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("Confirmation");
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
    }
    @FXML
    void save(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceActivity.getItems().addAll(event.getActivity());
        ArrayList<Moderation> moderations = new ArrayList<>();
        for (Activity activity: event.getActivity()){
            for (Confirmation confirmation : activity.getApplications()){
                moderations.add(confirmation.getModerator());
            }
        }
        FilteredList<Moderation> filteredList = new FilteredList<>(FXCollections.observableList(moderations), p -> true);
        choiceActivity.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(moderation -> {
                for (Confirmation confirmation : newValue.getApplications())
                    if (confirmation.getModerator().getId().equals(moderation.getId())) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Moderation> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
//        table.setStyle(tableStyle);
//        table.setRowFactory(t -> clickedTable());


        TableColumn<Moderation, String> nameColumn = new TableColumn<Moderation, String>("ФИО");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Moderation, String>("name"));
//        nameColumn.setStyle(columnStyle);
        table.getColumns().add(nameColumn);


        TableColumn<Moderation, String> phoneColumn = new TableColumn<Moderation, String>("Телефон");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Moderation, String>("phone"));
//        phoneColumn.setStyle(columnStyle);
        table.getColumns().add(phoneColumn);
    }
}
