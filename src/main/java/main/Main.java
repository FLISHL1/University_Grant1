package main;


import main.capcha.GenerateCapcha;
import main.controller.MainWinNoAuthController;
import main.logic.Event;
import main.logic.User.Participant;
import main.logic.User.User;
import main.logic.User.UserSelection;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        new AuthController().render();
        new MainWinNoAuthController().render();

    }
}
