package main.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import main.animation.Shape;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

import main.attentionWindow.AlertShow;
import main.capcha.GenerateCapcha;
import main.logic.User.Moderation;
import main.logic.User.Organizer;
import main.logic.User.User;
import main.logic.dao.ModeratorDAO;
import main.logic.dao.OrganizerDAO;
import main.logic.dao.UserDAO;
import net.synedra.validatorfx.Validator;

public class AuthController extends Controller {
    @FXML
    private ImageView captcha;
    @FXML
    private Button enter;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private TextField passwordV;
    @FXML
    private TextField fieldSim;
    @FXML
    private CheckBox visiblePassword;

    private String answerCaptcha;
    private int trying;
    Validator validator = new Validator();
    Shape shapePassword;
    Shape shapeUsername;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shapePassword = new Shape(login);
        shapeUsername = new Shape(password);
        reloadCaptcha();
        validator.createCheck()
                .dependsOn("username", login.textProperty())
                .withMethod(c -> {
                    String userName = c.get("username");
                    if (!userName.matches("[0-9]+|")){
                        c.error("Могут быть только цифры");
                    }
                    if (userName.isEmpty()){
                        c.warn("Поле не может быть пустым");
                    }
                }).decorates(login)
                .immediate();

    }
    @FXML
    private void registration(MouseEvent actionEvent) {
         new RegUser().loadScene((Stage) login.getScene().getWindow(), "Registration");
    }


    @FXML
    private void reloadCaptcha(){
        Object[] lost = GenerateCapcha.create();
        answerCaptcha = (String) lost[0];
        BufferedImage image = (BufferedImage) lost[1];
        Image imageFX = SwingFXUtils.toFXImage(image, null);
        captcha.setImage(imageFX);
    }

    private boolean checkCaptcha(){
        return fieldSim.getText().equals(answerCaptcha);
    }
    @FXML
    private void styleLogin(Event event){
        enter.setStyle("-fx-background-color: rgb(153, 255, 255); -fx-background-radius: 15");
    }
    @FXML
    private void styleLogin1(Event event){
        enter.setStyle("-fx-background-color: rgb(0, 0, 204); -fx-background-radius: 15");

    }

    @FXML
    private void login(ActionEvent actionEvent) {
        UserDAO userDAO = new UserDAO();
        User user;
        if (validator.getValidationResult().getMessages().isEmpty() && checkCaptcha()){
            if (!password.getText().equals("") && checkCaptcha()){
                if (passwordV.isVisible())
                    user = userDAO.auth(Integer.parseInt(login.getText()), passwordV.getText());
                else
                    user = userDAO.auth(Integer.parseInt(login.getText()), password.getText());
                if (user == null){
                    shapePassword.playAnimation();
                    AlertShow.showAlert("info", "Информирование", "Либо логин, либо пароль не правилен");
//                    Что то вывести
                    trying++;
                } else {
                    User user_auth = new OrganizerDAO().getById(user.getId());
                    if (user_auth != null) new WindowOrg((Organizer) user).loadScene((Stage) password.getScene().getWindow(), "Главное окно организатора");
                    user_auth = new ModeratorDAO().getById(user.getId());
                    if (user_auth != null) new WindowModerator((Moderation) user).loadScene((Stage) password.getScene().getWindow(), "Главное окно модератора");

                }
            }
        } else {
            shapePassword.playAnimation();
            shapeUsername.playAnimation();
            reloadCaptcha();
            AlertShow.showAlert("info", "Внимание", "Логин должен содержать только цифры!\nЛибо же каптча ввдена не верно");
        }
    }
    @FXML
    private void visiblePassword(ActionEvent event) {
        if (visiblePassword.isSelected()) {
            passwordV.setText(password.getText());
            passwordV.setVisible(true);
        } else {
            password.setText(passwordV.getText());
            passwordV.setVisible(false);
        }

    }
    public void home(MouseEvent mouseEvent) {
        new MainWinNoAuthController().loadScene((Stage) login.getScene().getWindow(), "Home");
    }

    public void loadScene(Stage stage, String title){
        super.loadSceneWithController("Login.fxml", stage, title);

    }
}
