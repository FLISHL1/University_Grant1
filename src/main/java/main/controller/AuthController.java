package main.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import main.animation.Shape;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

import main.attentionWindow.AlertShow;
import main.capcha.GenerateCapcha;
import main.logic.User.Moderation;
import main.logic.User.Organizer;
import main.logic.User.Participant;
import main.logic.User.User;
import main.logic.dao.*;
import net.synedra.validatorfx.Validator;

public class AuthController extends Controller {
    @FXML
    private ImageView captcha;
    @FXML
    private Button enter;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private TextField passwordV;
    @FXML
    private TextField fieldSim;
    @FXML
    private CheckBox visiblePassword;
    @FXML
    private CheckBox remember;

    private String answerCaptcha;
    private int trying;
    Validator validator = new Validator();
    Shape shapePassword;
    Shape shapeUsername;

    private Long timeStart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File f = new File("remember.txt");
        if (f.exists() && !f.isDirectory()) {
            try {
                FileReader fl = new FileReader("remember.txt");
                Scanner scan = new Scanner(fl);

                String[] str = scan.nextLine().split(" ");
                login.setText(str[0]);
                password.setText(str[1]);
                remember.setSelected(true);
                fl.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchElementException e) {
                ;
            }
        }

        shapePassword = new Shape(login);
        shapeUsername = new Shape(password);
        reloadCaptcha();
        validator.createCheck()
                .dependsOn("username", login.textProperty())
                .withMethod(c -> {
                    String userName = c.get("username");
                    if (!userName.matches("[0-9]+|")) {
                        c.error("Могут быть только цифры");
                    }
                    if (userName.isEmpty()) {
                        c.warn("Поле не может быть пустым");
                    }
                }).decorates(login)
                .immediate();

    }

    @FXML
    private void registration(MouseEvent actionEvent) {
        new RegUser().loadScene((Stage) login.getScene().getWindow(), "Registration");
    }


    @FXML
    private void reloadCaptcha() {
        Object[] lost = GenerateCapcha.create();
        answerCaptcha = (String) lost[0];
        BufferedImage image = (BufferedImage) lost[1];
        Image imageFX = SwingFXUtils.toFXImage(image, null);
        captcha.setImage(imageFX);
    }

    private boolean checkCaptcha() {
        return fieldSim.getText().equals(answerCaptcha);
    }

    @FXML
    private void styleLogin(Event event) {
        enter.setStyle("-fx-background-color: rgb(153, 255, 255); -fx-background-radius: 15");
    }

    @FXML
    private void styleLogin1(Event event) {
        enter.setStyle("-fx-background-color: rgb(0, 0, 204); -fx-background-radius: 15");

    }

    @FXML
    private void login(ActionEvent actionEvent) {
        UserDAO userDAO = new UserDAO();
        User user;
        if (timeStart != null && (System.currentTimeMillis() - timeStart) / 1000 >= 10) {
            timeStart = 0L;
        }
        if (timeStart != null && timeStart != 0) {
            AlertShow.showAlert("warning", "Вы заблокированы на " + (10 - (System.currentTimeMillis() - timeStart) / 1000) + " сек.");
            return;
        }
        if (validator.getValidationResult().getMessages().isEmpty() && checkCaptcha()) {
            if (!password.getText().equals("") && checkCaptcha()) {
                if (passwordV.isVisible())
                    user = userDAO.auth(Integer.parseInt(login.getText()), passwordV.getText());
                else
                    user = userDAO.auth(Integer.parseInt(login.getText()), password.getText());
                if (user == null) {
                    shapePassword.playAnimation();
                    AlertShow.showAlert("warning", "Либо логин, либо пароль не правилен");
//                    Что то вывести
                    trying++;
                } else {
                    User user_auth = new OrganizerDAO().getById(user.getId());
                    if (remember.isSelected()) {
                        try {
                            FileWriter fl = new FileWriter("remember.txt");
                            fl.write(user.getId() + " " + user.getPassword());
                            fl.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        File f = new File("remember.txt");
                        f.delete();
                    }
                    if (user_auth != null)
                        new WindowOrg((Organizer) user).loadScene((Stage) password.getScene().getWindow(), "Главное окно организатора");

                    user_auth = new ModeratorDAO().getById(user.getId());
                    if (user_auth != null)
                        new WindowModerator((Moderation) user).loadScene((Stage) password.getScene().getWindow(), "Главное окно модератора");

                    user_auth = new ParticipantDAO().getById(user.getId());
                    if (user_auth != null)
                        new WindowPart( user).loadScene((Stage) password.getScene().getWindow());

                    user_auth = new JuryDAO().getById(user.getId());
                    if (user_auth != null)
                        new WindowPart( user).loadScene((Stage) password.getScene().getWindow());
                }
            }
        } else {
            shapePassword.playAnimation();
            shapeUsername.playAnimation();
            reloadCaptcha();
            AlertShow.showAlert("info", "Логин должен содержать только цифры!\nЛибо же каптча ввдена не верно");
            trying++;
        }
        if (trying == 3) {
            timeStart = System.currentTimeMillis();
            AlertShow.showAlert("warning", "Вы заблокированы на " + (10 - (System.currentTimeMillis() - timeStart) / 1000));
            trying = 0;
        }
    }

    @FXML
    private void visiblePassword(ActionEvent event) {
        if (visiblePassword.isSelected()) {
            passwordV.setText(password.getText());
            passwordV.setVisible(true);
        } else {
            password.setText(passwordV.getText());
            passwordV.setVisible(false);
        }

    }

    public void home(MouseEvent mouseEvent) {
        new MainWinNoAuthController().loadScene((Stage) login.getScene().getWindow(), "Home");
    }

    public void loadScene(Stage stage, String title) {
        super.loadSceneWithController("Login.fxml", stage, title);

    }
}
