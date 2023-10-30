package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.Event;
import main.logic.dao.EventDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventInfoController extends Application implements Initializable, Controller {
    @FXML
    private Text name_event;
    @FXML
    private ImageView logo_event;
    @FXML
    private Text date;
    @FXML
    private Text organizer;
    @FXML
    private Text city;
    @FXML
    private Text description;


    private Event event;

    public EventInfoController(Event event){
        this.event = event;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo_event.setImage(event.getLogo().getImage());
        name_event.setText(event.getName());
        date.setText(event.getDate());
        city.setText(event.getCity());
        EventDAO eventDAO = new EventDAO();
        eventDAO.openSession();
        event = eventDAO.merge(event);
        organizer.setText(event.getUser().getName());
        eventDAO.closeSession();
    }

    @FXML
    private void login(MouseEvent mouseEvent) {
        new AuthController().loadScene((Stage) logo_event.getScene().getWindow(), "Login");
    }

    @FXML
    private void home(MouseEvent mouseEvent) {
        new MainWinNoAuthController().loadScene((Stage) logo_event.getScene().getWindow(), "Home");
    }
    public void loadScene(Stage stage, String title){
        FXMLLoader loader = new FXMLLoader(EventInfoController.class.getResource("/main/EventInfo.fxml"));
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
    public void start(Stage stage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/EventInfo.fxml"));
        loader.setController(this);
        loader.setControllerFactory(param -> this);
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("EventIfo");
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
}
