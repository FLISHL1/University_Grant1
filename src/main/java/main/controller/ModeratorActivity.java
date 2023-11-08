package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.Activity;
import main.logic.User.Moderation;
import main.logic.User.Participant;
import main.logic.User.User;
import main.logic.dao.AbstractDao;
import main.logic.dao.ActivityDAO;
import main.logic.dao.ParticipantDAO;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModeratorActivity extends Controller {

    @FXML
    private Rectangle activityBlock;

    @FXML
    private VBox activityList;

    @FXML
    private Label activityName;

    @FXML
    private AnchorPane activityRow;

    @FXML
    private AnchorPane add;

    @FXML
    private ImageView addFile;

    @FXML
    private ImageView delFile;

    @FXML
    private Text helloName;

    @FXML
    private Text helloText;

    @FXML
    private ImageView icon;

    @FXML
    private TableView<Participant> tableUser;
    @FXML
    private TableView<String> tableFile;


    private Moderation user;

    private String tableStyle = ("-fx-selection-bar: red;" +
            "-fx-selection-bar-non-focused: salmon;");

    private String columnStyle = ("-fx-alignment: CENTER; " +
            "-fx-background-color: rgba(255, 255, 255, 0.5);" +
            "-fx-border-color: gray;" +
            "-fx-font-size: 12pt;" +
            "-fx-font-family: 'Comic Sans MS';");

    public ModeratorActivity(Moderation moderation) {
        user = moderation;
    }

    @FXML
    void desk(ActionEvent event) {
//        new KanBanController(user,)
    }

    @FXML
    void home(MouseEvent event) {
        new WindowModerator(user).loadScene((Stage) tableUser.getScene().getWindow(), "Главное окно модератора");
    }

    @FXML
    void profile(MouseEvent event) {
        new ProfileController(user).loadScene((Stage) tableUser.getScene().getWindow(), "Профиль");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init(icon, helloText, helloName, user);

        tableUser.getItems().addAll(new ParticipantDAO().getAll());

        TableColumn<Participant, ImageView> logoColumn = new TableColumn<Participant, ImageView>("Фото");
        logoColumn.setCellValueFactory(new PropertyValueFactory<Participant, ImageView>("photo"));
        logoColumn.setStyle(columnStyle);
        tableUser.getColumns().add(logoColumn);

        TableColumn<Participant, String> nameColumn = new TableColumn<Participant, String>("ФИО");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Participant, String>("name"));
        nameColumn.setStyle(columnStyle);
        tableUser.getColumns().add(nameColumn);

        TableColumn<Participant, String> emailColumn = new TableColumn<Participant, String>("E-mail");
        emailColumn.setCellValueFactory(new PropertyValueFactory<Participant, String>("email"));
        emailColumn.setStyle(columnStyle);
        tableUser.getColumns().add(emailColumn);


        TableColumn<Participant, String> roleColumn = new TableColumn<Participant, String>("Телефон");
        roleColumn.setCellValueFactory(new PropertyValueFactory<Participant, String>("phone"));
        roleColumn.setStyle(columnStyle);
        tableUser.getColumns().add(roleColumn);

        ActivityDAO activityDAO = new ActivityDAO();
        ArrayList<Activity> activities = new ArrayList<>(activityDAO.getByModerator(user));
        activityName.setText(activities.get(0).getName() + " " + activities.remove(0).getStartTime());
/*        activityBlock.setOnMouseClicked(t -> {

        });*/
        for (Activity activity : activities) {
            AnchorPane newActivityRow = new AnchorPane();
            initActivityRow(newActivityRow, activity);
            activityList.getChildren().add(newActivityRow);
        }
    }

    private void initActivityRow(AnchorPane newActivityRow, Activity activity) {
        newActivityRow.setPrefHeight(179);
        newActivityRow.setPrefWidth(314);

        Rectangle newActivityBlock = new Rectangle();
        newActivityBlock.setWidth(240);
        newActivityBlock.setHeight(133);
        newActivityBlock.setLayoutX(23);
        newActivityBlock.setLayoutY(23);
        newActivityBlock.setFill(Color.WHITE);
        newActivityBlock.setStroke(Color.BLACK);
        newActivityRow.getChildren().add(newActivityBlock);

        Label newActivityName = new Label();
        newActivityName.setPrefWidth(186);
        newActivityName.setPrefHeight(90);
        newActivityName.setLayoutX(50);
        newActivityName.setLayoutY(45);
        newActivityName.setText(activity.getName() + " " + activity.getStartTime());
        newActivityName.setFont(Font.font("Comic Sans MS", 17));
        newActivityName.setWrapText(true);
        newActivityRow.getChildren().add(newActivityName);

    }

    public void loadScene(Stage stage, String title) {
        super.loadScene("ModerationActivity.fxml", stage, title);
    }
}

