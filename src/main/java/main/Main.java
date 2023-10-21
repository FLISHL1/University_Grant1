package main;


import javafx.stage.Stage;
import main.capcha.GenerateCapcha;
import main.controller.AuthController;
import main.logic.Participant;
import main.logic.User;
import main.logic.UserSelection;
import main.passwordHash.PasswordHashing;

public class Main {
    public static void main(String[] args) {
        new AuthController().render();
        User user = UserSelection.getUser("0001");
    }
}
