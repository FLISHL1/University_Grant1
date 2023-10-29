package main.logic.dao;

import main.logic.User.Organizer;
import org.hibernate.cfg.Configuration;

public class OrganizerDAO extends AbstractDao<Organizer>{
    public OrganizerDAO() {
        super(Organizer.class);
    }

    @Override
    public void init() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(main.logic.User.User.class)
                .addAnnotatedClass(main.logic.User.Organizer.class)
                .addAnnotatedClass(main.logic.Country.class)
                .buildSessionFactory();
    }
}
