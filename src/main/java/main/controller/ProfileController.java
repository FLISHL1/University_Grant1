package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.attentionWindow.AlertShow;
import main.logic.Country;
import main.logic.dao.CountryDAO;
import main.logic.User.Organizer;
import main.logic.User.User;
import main.logic.dao.UserDAO;
import main.passwordHash.PasswordHashing;
import net.synedra.validatorfx.Validator;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ProfileController implements Initializable, Controller {
    public TextField email;
    public Text helloText;
    public ImageView photo;
    public PasswordField rePassword;
    public TextField password;
    public TextField rePasswordV;
    public CheckBox visiblePassword;
    public Text nameText;
    public TextField name;
    public TextField phone;
    public DatePicker birthDay;
    public ChoiceBox country;

    @FXML
    private RadioButton genderMen;
    @FXML
    private RadioButton genderWoman;
    public Text idUser;
    private User user;
    UserDAO userDAO;
    private Validator validator = new Validator();


    public ProfileController(User user) {
        this.user = user;
        userDAO = new UserDAO();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        photo.setImage(user.getPhoto().getImage());
        nameText.setText((user.getSex().contains("муж") ? "Дорогой " : "Дорогая ") + user.getName());
        String hello = "";
        int hour = new Date().getHours();
        System.out.println(hour);
        if (hour >= 5 && hour < 12) hello = "Доброе утро!";
        else if (hour >= 12 && hour < 16) hello = "Добрый день!";
        else if (hour >= 16 && hour < 0) hello = "Доброе вечер!";
        else if (hour >= 0 && hour < 5) hello = "Доброй ночи!";
        helloText.setText(hello);
        idUser.setText(Integer.toString(user.getId()));
        name.setText(user.getName());
        email.setText(user.getEmail());
        CountryDAO countryDAO = new CountryDAO();
        ObservableList<Country> ct = FXCollections.observableArrayList(countryDAO.getAll());
        country.setItems(ct);
        for (Country c : ct) {
            if (c.getId() == user.getCountryCode())
                country.setValue(c);
        }
        phone.setText(user.getPhone());

        if (user.getSex().contains("м")) genderMen.setSelected(true);
        else genderWoman.setSelected(true);

        birthDay.setValue(Instant.ofEpochMilli(user.getBirthDay().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());

        validator.createCheck()
                .dependsOn("password", password.textProperty())
                .dependsOn("rePassword", rePassword.textProperty())
                .dependsOn("rePasswordV", rePasswordV.textProperty())
                .withMethod(c -> {
                    String rePasswd;
                    if (visiblePassword.isSelected()) {
                        rePasswd = rePasswordV.getText();
                    } else {
                        rePasswd = rePassword.getText();
                    }
                    if (!rePasswd.equals(c.get("password"))) {
                        c.error("Пароли должны совпадать");
                    }
                }).decorates(rePassword)
                .immediate();


        validator.createCheck()
                .dependsOn("password", password.textProperty())
                .withMethod(c -> {
                    if (!password.getText().isEmpty() && !password.getText().matches("(?=.*[0-9])(?=.*\\W)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}")) {
                        c.error("Не менее 6 символов \n Заглавные и строчные буквы \n Не менее одного спецсимвола \n Не менее одной цифры");
                    }
                }).decorates(password)
                .immediate();

        validator.createCheck()
                .dependsOn("email", email.textProperty())
                .withMethod(c -> {
                    String email = c.get("email");
                    if (email.isEmpty()) {
                        c.error("Необходимо ввести электронную почту");
                    } else if (!email.matches(".+@.+\\..+")) {
                        c.error("Не верный формат почты");
                    }
                }).decorates(email)
                .immediate();
        validator.createCheck()
                .dependsOn("phone", phone.textProperty())
                .withMethod(c -> {
                    String phone = c.get("phone");
                    if (phone.isEmpty()) {
                        c.error("Необходимо ввести номер телефона (8(999)-999-99-99)");
                    } else if (!phone.matches("[+]?(8|7)\\([0-9]{3}\\)-[0-9]{3}-[0-9]{2}-[0-9]{2}")) {
                        c.error("Не верный формат номера телефона (8(999)-999-99-99)");
                    }
                }).decorates(phone)
                .immediate();

        validator.createCheck()
                .dependsOn("country", country.valueProperty())
                .withMethod(c -> {
                    if (country.getValue() == null) {
                        c.error("Укажите страну!");
                    }
                }).decorates(country)
                .immediate();

        validator.createCheck()
                .dependsOn("date", birthDay.valueProperty())
                .withMethod(c -> {
                    if (birthDay.getValue() == null) {
                        c.error("Укажите дату рождения!");
                    }
                }).decorates(birthDay)
                .immediate();

        validator.createCheck()
                .dependsOn("name", name.textProperty())
                .withMethod(c -> {
                    String name = c.get("name");
                    if (name.isEmpty()) {
                        c.error("Заполните ФИО");
                    }

                }).decorates(name)
                .immediate();
    }

    @FXML
    private void visiblePassword(ActionEvent event) {
        if (visiblePassword.isSelected()) {
            rePasswordV.setText(rePassword.getText());
            rePasswordV.setVisible(true);
        } else {
            rePassword.setText(rePasswordV.getText());
            rePasswordV.setVisible(false);
        }

    }

    @Override
    public void loadScene(Stage stage, String title) {
        FXMLLoader loader = new FXMLLoader(MainWinNoAuthController.class.getResource("/main/Profile.fxml"));
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


    @FXML
    private void save(ActionEvent event) {
        if (!validator.containsErrors()) {
            fillUser();
            userDAO.update(user);

        } else {
            AlertShow.showAlert("info", "Не правильно ввели данные", validator.createStringBinding().get());
        }
    }

    private void fillUser() {
        user.setBirthDay(java.sql.Date.valueOf(birthDay.getValue()));
        user.setName(name.getText());
        user.setCountry((Country) country.getValue());
        user.setPhone(phone.getText());
        user.setEmail(email.getText());
        if (!password.getText().isEmpty())
            user.setPassword(PasswordHashing.HashPassword(password.getText()));
        user.setSex(genderMen.isSelected()?genderMen.getText():genderWoman.getText());

    }

    @FXML
    private void home(MouseEvent event){
        new WindowOrg((Organizer) user).loadScene((Stage) password.getScene().getWindow(), "Главное окно организатора");
    }

    @FXML
    private void participants(MouseEvent event){
        new UserList(user).loadScene((Stage) password.getScene().getWindow(), "Участники");

    }

    @FXML
    private void jury(MouseEvent event){
        new ModeratorsAndJuryController((Organizer) user).loadScene((Stage) email.getScene().getWindow(), "Регистрация жури\\модератора");
    }
}