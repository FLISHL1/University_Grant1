package main.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller implements Initializable {


    public void loadScene(String nameFXML, Stage stage, String title) {
        FXMLLoader loader = new FXMLLoader(AuthController.class.getResource("/main/" + nameFXML));
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

    public void loadSceneWithController(String nameFXML, Stage stage, String title) {
        FXMLLoader loader = new FXMLLoader(AuthController.class.getResource("/main/" + nameFXML));
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
}
