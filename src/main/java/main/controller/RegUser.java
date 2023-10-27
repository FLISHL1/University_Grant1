package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.logic.Country;
import main.logic.User.Participant;
import main.logic.User.User;
import net.synedra.validatorfx.Validator;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class RegUser extends Application implements Initializable, Controller {
    @FXML
    private TextField name;
    @FXML
    private TextField password;
    @FXML
    private PasswordField rePassword;
    @FXML
    private TextField rePasswordV;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private DatePicker birth_date;
    @FXML
    private CheckBox visiblePassword;
    @FXML
    private Button photo;
    @FXML
    private ChoiceBox country;
    @FXML
    private RadioButton genderMen;
    @FXML
    private Button singUp;
    @FXML
    private TextField idNumber;

    private User newUser;

    Validator validator = new Validator();
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
        ObservableList<Country> ct = FXCollections.observableArrayList(Country.getAllCountry());
        country.setItems(ct);
//        newUser = new Participant();
//        idNumber.setText(newUser.getIdNumber());
        validator.createCheck()
                .dependsOn("password", password.textProperty())
                .dependsOn("rePassword", rePassword.textProperty())
                .dependsOn("rePasswordV", rePasswordV.textProperty())
                .withMethod(c -> {
                    String rePasswd;
                    if (visiblePassword.isSelected()){
                        rePasswd = rePasswordV.getText();
                    }
                    else {
                        rePasswd = rePassword.getText();
                    }
                    if (!rePasswd.equals(c.get("password"))){
                        c.error("Пароли должны совпадать");
                    }
                    if (rePasswd.isEmpty()){
                        c.error("Повторите пароль!");
                    }
                }).decorates(rePassword)
                .immediate();


        validator.createCheck()
                .dependsOn("password", password.textProperty())
                .withMethod(c -> {
                    if (password.getText().isEmpty()){
                        c.warn("Необходимо ввести пароль");
                    } else if (!password.getText().matches("(?=.*[0-9])(?=.*\\W)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}")){
                        c.error("Не менее 6 символов \n Заглавные и строчные буквы \n Не менее одного спецсимвола \n Не менее одной цифры");
                    }
                }).decorates(password)
                .immediate();

        validator.createCheck()
                .dependsOn("email", email.textProperty())
                .withMethod(c ->{
                    String email = c.get("email");
                    if (email.isEmpty()){
                        c.warn("Необходимо ввести электронную почту");
                    } else if(!email.matches(".+@.+\\..+")){
                        c.error("Не верный формат почты");
                    }
                }).decorates(email)
                .immediate();
        validator.createCheck()
                .dependsOn("phone", phone.textProperty())
                .withMethod(c ->{
                    String phone = c.get("phone");
                    if (phone.isEmpty()){
                        c.warn("Необходимо ввести номер телефона (8(999)-999-99-99)");
                    } else if(!phone.matches("[+]?(8|7)\\([0-9]{3}\\)-[0-9]{3}-[0-9]{2}-[0-9]{2}")) {
                        c.error("Не верный формат номера телефона (8(999)-999-99-99)");
                    }}).decorates(phone)
                .immediate();

        validator.createCheck()
                .dependsOn("country", country.converterProperty())
                .withMethod(c ->{
                    if(country.getValue() == null){
                        c.warn("Укажите страну!");
                    }
                }).decorates(country)
                .immediate();

        validator.createCheck()
                .dependsOn("date", birth_date.accessibleTextProperty())
                .withMethod(c ->{
                    if(birth_date.getEditor().getText().isEmpty()){
                        c.warn("Укажите дату рождения!");
                    }
                }).decorates(birth_date)
                .immediate();

        validator.createCheck()
                .dependsOn("name", name.textProperty())
                .withMethod(c ->{
                    String name = c.get("name");
                    if (name.isEmpty()){
                        c.warn("Заполните ФИО");
                    }

                }).decorates(birth_date)
                .immediate();
    }

    @FXML
    private void visiblePassword(ActionEvent event){
        if (visiblePassword.isSelected()){
            rePasswordV.setText(rePassword.getText());
            rePasswordV.setVisible(true);
        } else{
            rePassword.setText(rePasswordV.getText());
            rePasswordV.setVisible(false);
        }

    }

    @FXML
    private void register(ActionEvent event){

    }
    @FXML
    private void singUp(ActionEvent event){

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
