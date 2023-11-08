package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.Event;
import main.logic.User.Organizer;
import main.logic.User.User;
import main.logic.dao.EventDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class EventInfoController extends Controller {
    @FXML
    private Label nameEvent;
    @FXML
    private ImageView logoEvent;
    @FXML
    private Label date;
    @FXML
    private Label organizer;
    @FXML
    private Label city;
    @FXML
    private Text description;

    private Event event;
    private User user;

    public EventInfoController(Event event){
        this.event = event;
    }

    public EventInfoController(Event event, User user)
    {
        this.event = event;
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logoEvent.setImage(event.getLogo().getImage());
        nameEvent.setText(event.getName());
        date.setText(event.getDateStart());
        EventDAO eventDAO = new EventDAO();
        eventDAO.openSession();
        eventDAO.merge(event);
        city.setText(event.getCity().getName());
        event = eventDAO.merge(event);
        organizer.setText(event.getOrganizer().getName());
        eventDAO.closeSession();
    }

    @FXML
    private void login(MouseEvent mouseEvent) {
        if (user == null)
            new AuthController().loadScene((Stage) logoEvent.getScene().getWindow(), "Авторизация");
        else
            new ProfileController(user).loadScene((Stage) logoEvent.getScene().getWindow(), "Профиль");
    }

    @FXML
    private void home(MouseEvent mouseEvent) {
        if (user == null)
            new MainWinNoAuthController().loadScene((Stage) logoEvent.getScene().getWindow(), "Главное окно");
        else
            new WindowOrg((Organizer) user).loadScene((Stage) logoEvent.getScene().getWindow(), "Окно организатора");
    }
    public void loadScene(Stage stage, String title){
        super.loadSceneWithController("EventInfo.fxml", stage, title);
    }

}
