package main.logic;

import main.logic.User.*;
import main.logic.dao.EventDAO;
import main.logic.dao.UserDAO;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Test {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        UserDAO userDAO1 = new UserDAO();
        UserDAO userDAO2 = new UserDAO();
        UserDAO userDAO3 = new UserDAO();

//        EventDAO userDao = new EventDAO();
//        userDao.openSession();
//        Event event = userDao.getById(1);
//        userDao.closeSession();
//        userDao.openSession();
//        event = userDao.merge(event);
//        System.out.println(event.getUser().getName());
//        userDao.closeSession();
//        System.out.println(event.getUser().getName());
//        System.out.println(event.getUser().getEmail());
//        for(Event user: userDao.getAll())
//            System.out.println(user.getUser());


//        System.out.println(userManager.getUser(127).getName());
    }
}
