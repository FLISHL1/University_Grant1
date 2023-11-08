package main;


import main.attentionWindow.AlertShow;
import main.controller.MainWinNoAuthController;
import main.controller.WindowOrg;
import main.logic.dao.EventDAO;
import main.logic.dao.OrganizerDAO;


public class Main {
    public static void main(String[] args) {
        new MainWinNoAuthController().render();
    }


}
