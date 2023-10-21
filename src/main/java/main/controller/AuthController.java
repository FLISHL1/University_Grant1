package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javafx.embed.swing.JFXPanel;
import main.capcha.GenerateCapcha;
import main.logic.User;
import main.logic.UserSelection;

public class AuthController extends Application {
    @FXML
    private Button butLogin;
    @FXML
    private TextField insLogin;
    @FXML
    private TextField insPassword;
    @FXML
    private TextField insCaptcha;

    private String captcha;
    private int trying;

    @Override
    public void start(Stage stage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/AuthPage.fxml"));
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
    private void registration(ActionEvent actionEvent) {
    }

    @FXML
    private void cancel(ActionEvent actionEvent) {
        System.exit(0);
    }


    @FXML
    private void reloadCaptcha(){
        Object[] lost = GenerateCapcha.create();
        captcha = (String) lost[1];
        BufferedImage image = (BufferedImage) lost[2];
    }

    private boolean checkCaptcha(){
        if (trying <= 3){
            return true;
        } else {
            if (trying == 3){
//          reloadCaptcha();
            }
            return insCaptcha.getText().equals(captcha);
        }
    }

    @FXML
    private void login(ActionEvent actionEvent) {
        User user;
        if (insLogin.getText().matches("[0-9]+")){
            if (!insPassword.getText().equals("") && checkCaptcha()){
                user = UserSelection.checkAuth(insLogin.getText(), insPassword.getText());
                if (user == null){
                    trying++;
                }
            }
        } else {
            AlertShow.showAlert("info", "Внимание", "Логин должен содержать только цифры!");
        }
    }
}
