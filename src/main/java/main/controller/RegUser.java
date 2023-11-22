package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.attentionWindow.AlertShow;
import main.logic.Country;
import main.logic.User.Organizer;
import main.logic.User.User;
import main.logic.dao.CountryDAO;
import main.logic.dao.ParticipantDAO;
import main.logic.User.Participant;
import main.passwordHash.PasswordHashing;
import net.synedra.validatorfx.Validator;

import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class RegUser extends Controller {
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
    private DatePicker birthDay;
    @FXML
    private CheckBox visiblePassword;
    @FXML
    private ImageView edit_photo;
    @FXML
    private ComboBox<Country> country;
    @FXML
    private RadioButton genderMen;
    @FXML
    private RadioButton genderWoman;
    @FXML
    private Button btnSignUp;
    @FXML
    private Text idUser;
    @FXML
    private ImageView participants;
    @FXML
    private ImageView jury;
    @FXML
    private ImageView icon;
    @FXML
    private ImageView profile;
    @FXML
    private Text helloText;
    @FXML
    private Text helloName;

    private Participant newUser;
    private User user;
    private ParticipantDAO participantDAO;
    Validator validator;

    public RegUser() {
        participantDAO = new ParticipantDAO();
        validator = new Validator();
        user = null;
    }

    public RegUser(User user) {
        participantDAO = new ParticipantDAO();
        validator = new Validator();
        this.user = user;
    }

    private void checkUser() {
        if (user == null) {
            profile.setOnMouseClicked(this::login);
            participants.setVisible(false);
            jury.setVisible(false);
            helloText.setVisible(false);
            helloName.setVisible(false);
        } else {
            init(icon, helloText, helloName, user);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkUser();
        CountryDAO countryDAO = new CountryDAO();
        ObservableList<Country> ct = FXCollections.observableArrayList(countryDAO.getAll());
        country.setItems(ct);
        newUser = participantDAO.create(new Participant());
        idUser.setText(Integer.toString(newUser.getId()));

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
                    if (rePasswd.isEmpty()) {
                        c.error("Повторите пароль!");
                    }
                }).decorates(rePassword)
                .immediate();


        validator.createCheck()
                .dependsOn("password", password.textProperty())
                .withMethod(c -> {
                    if (password.getText().isEmpty()) {
                        c.error("Необходимо ввести пароль");
                    } else if (!password.getText().matches("(?=.*[0-9])(?=.*\\W)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}")) {
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

    @FXML
    private void save(ActionEvent event) {
        if (!validator.containsErrors()) {
            fillUser();
            participantDAO.update(newUser);
            AlertShow.showAlert("info", "Пользовтель создан");
            login(null);
        } else {
            AlertShow.showAlert("warning", validator.createStringBinding().get());
        }
    }

    @FXML
    private void exit(ActionEvent event) {
    }

    private void fillUser() {
        newUser.setBirthDay(Date.valueOf(birthDay.getValue()));
        newUser.setName(name.getText());
        newUser.setCountry((Country) country.getValue());
        newUser.setPhone(phone.getText());
        newUser.setEmail(email.getText());
        newUser.setPassword(password.getText());
        System.out.println(genderMen.isSelected());
        newUser.setSex(genderMen.isSelected() ? genderMen.getText() : genderWoman.getText());
    }

    public void loadScene(Stage stage, String title) {
        super.loadSceneWithController("Profile.fxml", stage, title);
    }

    @FXML
    private void login(MouseEvent mouseEvent) {
        if (newUser.getName() == null) participantDAO.delete(newUser);
        if (user instanceof Organizer) new UserList(user).loadScene((Stage) profile.getScene().getWindow(), "Профиль");
        else new AuthController().loadScene((Stage) profile.getScene().getWindow(), "Профиль");
    }

    @FXML
    private void home(MouseEvent mouseEvent) {
        if (user == null) {
            participantDAO.delete(newUser);
            new MainWinNoAuthController().loadScene((Stage) profile.getScene().getWindow(), "Главное окно");
        } else {
            participantDAO.delete(newUser);
            new WindowOrg((Organizer) user).loadScene((Stage) password.getScene().getWindow(), "Главное окно");
        }
    }

    @FXML
    private void participants(MouseEvent event) {
        participantDAO.delete(newUser);
        if (participants.isVisible())
            new UserList(user).loadScene((Stage) profile.getScene().getWindow(), "Список пользователей");
    }

    @FXML
    private void jury(MouseEvent event) {
        participantDAO.delete(newUser);
        if (jury.isVisible())
            new ModeratorsAndJuryController((Organizer) user).loadScene((Stage) profile.getScene().getWindow(), "Модератор\\Жюри");
    }

}
