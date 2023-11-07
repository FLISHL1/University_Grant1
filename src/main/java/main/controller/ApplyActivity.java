package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.logic.Activity;
import main.logic.Application;
import main.logic.Event;
import main.logic.User.Jury;
import main.logic.User.Moderation;

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

    @FXML
    void save(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceActivity.getItems().addAll(event.getActivity());
        ArrayList<Moderation> moderations = new ArrayList<>();
        for (Activity activity: event.getActivity()){
            for (Application application: activity.getApplications()){
                moderations.add(application.getIdModerator());
            }
        }
        FilteredList<Moderation> filteredList = new FilteredList<>(FXCollections.observableList(moderations), p -> true);
        choiceActivity.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(moderation -> {
                if (newValue.getApplications().) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Event> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
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
    }
}
