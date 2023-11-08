package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.User.User;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public abstract class Controller implements Initializable {


    protected void init(ImageView icon, Text helloText, Text helloName, User user){
        if (icon != null) icon.setImage(user.getPhoto().getImage());
        if (helloName != null) helloName.setText((user.getSex().toLowerCase().contains("муж") ? "Дорогой " : "Дорогая ") + user.getName());
        if (helloText != null) {
            String hello = "";
            int hour = new Date().getHours();
            System.out.println(hour);
            if (hour >= 5 && hour < 12) hello = "Доброе утро!";
            else if (hour >= 12 && hour < 16) hello = "Добрый день!";
            else if (hour >= 16) hello = "Доброе вечер!";
            else if (hour < 5) hello = "Доброй ночи!";
            helloText.setText(hello);
        }
    }
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
