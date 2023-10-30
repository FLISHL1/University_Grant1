package main.logic;

import main.logic.User.Organizer;
import main.logic.User.Participant;
import main.logic.User.User;
import main.logic.dao.EventDAO;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Test {
    public static void main(String[] args) {
        EventDAO userDao = new EventDAO();
        userDao.openSession();
        Event event = userDao.getById(1);
        userDao.closeSession();
        userDao.openSession();
        event = userDao.merge(event);
        System.out.println(event.getUser().getName());
        userDao.closeSession();
        System.out.println(event.getUser().getName());
        System.out.println(event.getUser().getEmail());
//        for(Event user: userDao.getAll())
//            System.out.println(user.getUser());


//        System.out.println(userManager.getUser(127).getName());
    }
}
