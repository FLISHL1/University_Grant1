package main;


import javafx.stage.Stage;
import main.capcha.GenerateCapcha;
import main.controller.AuthController;
import main.controller.MainWinNoAuthController;
import main.logic.Event;
import main.logic.Participant;
import main.logic.User;
import main.logic.UserSelection;
import main.passwordHash.PasswordHashing;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        new AuthController().render();
        new MainWinNoAuthController().render();
        User user = UserSelection.getUser("0001");
        ArrayList<Event> events = Event.getAllEvents();
        System.out.println(events.size());
    }
}
