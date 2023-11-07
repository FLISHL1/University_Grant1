package main.attentionWindow;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

public class AlertShow {
    public static void showAlert(String type, String title, String message, Stage stage){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert.AlertType typeAlert = switch (type){
                    case "info" -> Alert.AlertType.INFORMATION;
                    case "error" -> Alert.AlertType.ERROR;
                    case "warning" -> Alert.AlertType.WARNING;
                    case "conf" -> Alert.AlertType.CONFIRMATION;
                    default -> throw new IllegalStateException("Unexpected value: " + type);
                };
                Alert alert = new Alert(typeAlert);
                alert.setTitle(title);
                alert.setContentText(message);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("main/alert.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");
                alert.initOwner(stage);
                alert.show();
            }
        });
    }
    public static void showAlert(String type, String title, String message){
                Alert.AlertType typeAlert = switch (type){
                    case "info" -> Alert.AlertType.INFORMATION;
                    case "error" -> Alert.AlertType.ERROR;
                    case "warning" -> Alert.AlertType.WARNING;
                    case "conf" -> Alert.AlertType.CONFIRMATION;
                    default -> throw new IllegalStateException("Unexpected value: " + type);
                };
                Alert alert = new Alert(typeAlert);
                alert.setTitle(title);
                alert.setContentText(message);
                DialogPane dialogPane = alert.getDialogPane();

                dialogPane.getStylesheets().add(AlertShow.class.getResource("/main/alert.css").toExternalForm());
                dialogPane.getStyleClass().add("alert");
                alert.show();

    }
    private Alert alert;
    public AlertShow showAlertConf(String message){
        Alert.AlertType typeAlert = Alert.AlertType.CONFIRMATION;

        alert = new Alert(typeAlert, message, ButtonType.YES, ButtonType.NO);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(AlertShow.class.getResource("/main/alert.css").toExternalForm());
        dialogPane.getStyleClass().add("alert");
        alert.showAndWait();

        return this;
    }

    public AlertShow showAlertConfApplication(String message){
        Alert.AlertType typeAlert = Alert.AlertType.CONFIRMATION;
        ButtonType openEvent = new ButtonType("Открыть мерприятие", ButtonBar.ButtonData.YES);
        ButtonType applyApplication = new ButtonType("Открыть мерприятие", ButtonBar.ButtonData.YES);
        alert = new Alert(typeAlert, message, ButtonType.YES, ButtonType.NO);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(AlertShow.class.getResource("/main/alert.css").toExternalForm());
        dialogPane.getStyleClass().add("alert");
        alert.showAndWait();

        return this;
    }
    public ButtonType getConf(){
        return alert.getResult();
    }
}
