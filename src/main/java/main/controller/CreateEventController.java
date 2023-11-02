package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.logic.Event;
import main.logic.User.Organizer;
import main.logic.dao.EventDAO;
import main.logic.dao.JuryDAO;


import java.net.URL;
import java.util.ResourceBundle;

public class CreateEventController extends Controller {
    private EventDAO eventDAO;
    private Organizer user;
    private Event newEvent;

    public CreateEventController(Organizer user) {
        this.user = user;
        eventDAO = new EventDAO();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loadScene(Stage stage, String title) {
        super.loadScene("CreateEvent.fxml", stage, title);
    }

    @FXML
    private void delAction() {

    }

    private void createFieldAction() {

    }

    @FXML
    private void addAction() {

    }
}
