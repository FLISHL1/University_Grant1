package main.logic.dao;

import main.logic.Event;
import org.hibernate.cfg.Configuration;

public class EventDAO extends AbstractDao<Event>{

    public EventDAO() {
        super(Event.class);
    }

    @Override
    public void init() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(main.logic.User.Organizer.class)
                .addAnnotatedClass(main.logic.User.User.class)
                .addAnnotatedClass(main.logic.Direction.class)
                .addAnnotatedClass(main.logic.Event.class)
                .addAnnotatedClass(main.logic.City.class)
                .addAnnotatedClass(main.logic.Country.class)
                .buildSessionFactory();
    }


}
