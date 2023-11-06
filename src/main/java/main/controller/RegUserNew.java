package main.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.Activity;
import main.logic.Country;
import main.logic.Direction;
import main.logic.User.Jury;
import main.logic.User.Moderation;
import main.logic.User.User;
import main.logic.dao.*;
import main.passwordHash.PasswordHashing;
import net.synedra.validatorfx.Validator;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class RegUserNew  extends Controller {
    @FXML
    private Text helloText;
    @FXML
    private Text helloName;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private ComboBox<Direction> direction;
    @FXML
    private ChoiceBox<Activity> action;
    @FXML
    private CheckBox checkEvent;
    @FXML
    private Text idUser;
    @FXML
    private TextField password;
    @FXML
    private PasswordField rePassword;
    @FXML
    private TextField rePasswordV;
    @FXML
    private CheckBox visiblePassword;
    @FXML
    private RadioButton genderMen;
    @FXML
    private RadioButton roleJury;
    @FXML
    private DatePicker birth_date;
    @FXML
    private ComboBox<Country> country;
    @FXML
    private RadioButton genderWoman;
    private User user;
    private User newUser;
    private Validator validator;
    private Direction directionValue;
    public RegUserNew(){}

    public RegUserNew(User user){
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloName.setText((user.getSex().contains("муж")?"Дорогой ":"Дорогая ") + user.getName());
        String hello = "";
        int hour = new java.util.Date().getHours();
        if (hour >= 5 && hour < 12) hello = "Доброе утро!";
        else if (hour >= 12 && hour < 17) hello = "Добрый день!";
        else if (hour >= 17 && hour < 24) hello = "Добрый вечер!";
        else if (hour < 5) hello = "Доброй ночи!";

        helloText.setText(hello);
        validator = new Validator();
        UserDAO userDAO = new UserDAO();
        newUser = userDAO.create(new User());
        DirectionDAO directionDAO = new DirectionDAO();
        direction.setItems(FXCollections.observableList(directionDAO.getAll()));
        CountryDAO countryDAO = new CountryDAO();
        country.setItems(FXCollections.observableList(countryDAO.getAll()));
        ActivityDAO actionDAO = new ActivityDAO();
        action.setItems(FXCollections.observableList(actionDAO.getAll()));
        idUser.setText(Integer.toString(newUser.getId()));
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
                        c.error("Необходимо ввести пароль");
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
                        c.error("Необходимо ввести электронную почту");
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
                        c.error("Необходимо ввести номер телефона (8(999)-999-99-99)");
                    } else if(!phone.matches("[+]?(8|7)\\([0-9]{3}\\)-[0-9]{3}-[0-9]{2}-[0-9]{2}")) {
                        c.error("Не верный формат номера телефона (8(999)-999-99-99)");
                    }}).decorates(phone)
                .immediate();

        validator.createCheck()
                .dependsOn("country", country.valueProperty())
                .withMethod(c ->{
                    if(country.getValue() == null){
                        c.error("Укажите страну!");
                    }
                }).decorates(country)
                .immediate();

        validator.createCheck()
                .dependsOn("date", birth_date.valueProperty())
                .withMethod(c ->{
                    if(birth_date.getValue() == null){
                        c.error("Укажите дату рождения!");
                    }
                }).decorates(birth_date)
                .immediate();

        validator.createCheck()
                .dependsOn("name", name.textProperty())
                .withMethod(c ->{
                    String name = c.get("name");
                    if (name.isEmpty()){
                        c.error("Заполните ФИО");
                    }

                }).decorates(name)
                .immediate();
    }



    public void test(ActionEvent event) {
        for (Direction direction1: direction.getItems()){
            if (direction1.name.equals(direction.getEditor().getText())){
                directionValue = direction1;
                return;
            }
        }
        directionValue = new Direction(direction.getEditor().getText());

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
    private User fillUser(User user){
        user.setBirthDay(Date.valueOf(birth_date.getValue()));
        user.setName(name.getText());
        user.setCountry((Country) country.getValue());
        user.setPhone(phone.getText());
        user.setEmail(email.getText());
        user.setPassword(PasswordHashing.HashPassword(password.getText()));
        user.setSex(genderMen.isSelected()?genderMen.getText():genderWoman.getText());
        if (user instanceof Jury)
                ((Jury) user).setDirection(directionValue);
        else if (user instanceof Moderation)
            ((Moderation) user).setDirection(directionValue);

        return user;
    }

    public void save(MouseEvent event){
        Jury newJury;
        Moderation newModeration;
        if (roleJury.isSelected()){
            newJury = new Jury();
            newJury.setIdNumber(newUser.getId());
            newJury = (Jury) fillUser(newJury);
            System.out.println(newJury.getId());
            new JuryDAO().createReg(newJury);
        } else {
            newModeration = new Moderation();
            newModeration.setIdNumber(newUser.getId());
            newModeration = (Moderation) fillUser(newModeration);
            new ModeratorDAO().createReg(newModeration);
        }
    }


    public void loadScene(Stage stage, String title) {
        super.loadScene("RegModJur.fxml", stage, title);
    }
}
