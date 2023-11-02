package main.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import main.animation.Shape;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

import main.capcha.GenerateCapcha;
import main.logic.User.User;
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
    private TextField password;
    @FXML
    private TextField fieldSim;

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
                user = userDAO.auth(Integer.parseInt(login.getText()), password.getText());
                if (user == null){
                    shapePassword.playAnimation();
//                    Что то вывести
                    trying++;
                } else {

//                    if (user instanceof Organizer) new WindowOrg((Organizer) user).loadScene((Stage) password.getScene().getWindow(), "Главное окно организатора");
                }
            }
        } else {
            shapePassword.playAnimation();
            shapeUsername.playAnimation();
            reloadCaptcha();
//            AlertShow.showAlert("info", "Внимание", "Логин должен содержать только цифры!\nЛибо же каптча ввдена не верно");
        }
    }

    public void home(MouseEvent mouseEvent) {
        new MainWinNoAuthController().loadScene((Stage) login.getScene().getWindow(), "Home");
    }

    public void loadScene(Stage stage, String title){
        super.loadSceneWithController("Login.fxml", stage, title);

    }
}
