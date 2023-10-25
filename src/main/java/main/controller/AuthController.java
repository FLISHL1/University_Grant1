package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import main.animation.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.embed.swing.JFXPanel;
import main.capcha.GenerateCapcha;
import main.logic.User.User;
import main.logic.User.UserSelection;
import net.synedra.validatorfx.Validator;

public class AuthController extends Application implements Initializable {
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
    @Override
    public void start(Stage stage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/Login.fxml"));
        loader.setController(new AuthController());
        loader.setControllerFactory(param -> new AuthController());
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("Login");
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

    @FXML
    private void registration(MouseEvent actionEvent) {
        RegUser.loadScene((Stage) login.getScene().getWindow(), "Registration");
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
        User user;
        if (validator.getValidationResult().getMessages().isEmpty() && checkCaptcha()){
            if (!password.getText().equals("") && checkCaptcha()){
                user = User.checkAuth(login.getText(), password.getText());
                if (user == null){
                    trying++;
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
        MainWinNoAuthController.loadScene((Stage) login.getScene().getWindow(), "Home");
    }

    public static void loadScene(Stage stage, String title){
        FXMLLoader loader = new FXMLLoader(AuthController.class.getResource("/main/Login.fxml"));
        loader.setController(new AuthController());
        loader.setControllerFactory(param -> new AuthController());
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
