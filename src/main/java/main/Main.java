package main;


import main.controller.MainWinNoAuthController;
import main.logic.dao.OrganizerDAO;


public class Main {
    public static void main(String[] args) {
        OrganizerDAO organizerDAO = new OrganizerDAO();
//        new WindowOrg((Organizer) organizerDAO.getById(100)).render();

        new MainWinNoAuthController().render();
//        System.out.println(User.getUser("00100").getName());
    }


}
