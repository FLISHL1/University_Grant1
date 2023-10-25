package main;


import main.capcha.GenerateCapcha;
import main.controller.MainWinNoAuthController;
import main.logic.Event;
import main.logic.User.User;
import main.logic.User.UserSelection;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        GenerateCapcha.create("image.png");
//        new AuthController().render();
        new MainWinNoAuthController().render();
        User user = UserSelection.getUser("0001");
        ArrayList<Event> events = Event.getAllEvents();
        System.out.println(events.size());
    }
}
