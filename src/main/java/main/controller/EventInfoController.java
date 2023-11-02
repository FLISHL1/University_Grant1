package main.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.Event;
import main.logic.dao.EventDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class EventInfoController extends Controller {
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
        date.setText(event.getDateStart());
        EventDAO eventDAO = new EventDAO();
        eventDAO.openSession();
        eventDAO.merge(event);
        city.setText(event.getCity());
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
        super.loadSceneWithController("EventInfo.fxml", stage, title);
    }

}
