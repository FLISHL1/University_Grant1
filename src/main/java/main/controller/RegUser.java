package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegUser extends Application implements Initializable {
    @FXML
    private Button singUp;

    @Override
    public void start(Stage stage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/RegUser.fxml"));
        loader.setController(new RegUser());
        loader.setControllerFactory(param -> new RegUser());
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("Registration");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void loadScene(Stage stage, String title){
        FXMLLoader loader = new FXMLLoader(AuthController.class.getResource("/main/RegUser.fxml"));
        loader.setController(new RegUser());
        loader.setControllerFactory(param -> new RegUser());
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
    private void login(MouseEvent mouseEvent) {
        AuthController.loadScene((Stage) singUp.getScene().getWindow(), "Login");
    }

    @FXML
    private void home(MouseEvent mouseEvent) {
        MainWinNoAuthController.loadScene((Stage) singUp.getScene().getWindow(), "Home");
    }
}
