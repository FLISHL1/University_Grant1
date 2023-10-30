package main;


import main.attentionWindow.ConfirmationDialogExample;
import main.controller.WindowOrg;
import main.logic.dao.OrganizerDAO;
import main.logic.User.Organizer;


public class Main {
    public static void main(String[] args) {
        OrganizerDAO organizerDAO = new OrganizerDAO();
        new WindowOrg((Organizer) organizerDAO.getById(100)).render();

        System.out.println(0);
//        new MainWinNoAuthController().render();
//        System.out.println(User.getUser("00100").getName());
    }


}
