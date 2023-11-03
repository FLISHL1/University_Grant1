package main.attentionWindow;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationDialogExample extends Application implements Initializable {
    private String lableText;
    private Alert alert;
    private ButtonType result;

    @Override
    public void start(Stage primaryStage) {
        alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Подтверждение");
        alert.setContentText(lableText);

        ButtonType buttonTypeYes = new ButtonType("Да");
        ButtonType buttonTypeNo = new ButtonType("Нет");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.showAndWait();

    }

    public Alert getAlert() {
        return alert;
    }

    public void render(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                start(new Stage());
            }
        });

    }
    public String getLableText() {
        return lableText;
    }

    public void setLableText(String lableText) {
        this.lableText = lableText;
    }
    public ButtonType getResult(){
        return alert.getResult();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}