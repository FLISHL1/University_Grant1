package main.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import main.logic.User.Jury;
import main.logic.User.Organizer;
import main.logic.dao.JuryDAO;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateEventController implements Initializable, Controller {
    @FXML
    private ComboBox<CheckBox> jury1;
    private Organizer user;

    public CreateEventController(Organizer user){
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JuryDAO juryDAO = new JuryDAO();

//ayoutX="467.0" layoutY="20.0" prefHeight="0.0" prefWidth="150.0"

    }

    @Override
    public void loadScene(Stage stage, String title) {
        FXMLLoader loader = new FXMLLoader(MainWinNoAuthController.class.getResource("/main/CreateEvent.fxml"));
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

    @FXML
    private void delAction(){

    }
    private void createFieldAction(){
        AnchorPane anchorPane = new AnchorPane();
        TextField textField = new TextField();
        ChoiceBox<String> timeBox = new ChoiceBox<>();
    }
    @FXML
    private void addAction(){

    }
}
