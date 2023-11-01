package main.logic.dao;

import main.logic.User.Moderation;
import org.hibernate.cfg.Configuration;

public class ModeratorDAO extends AbstractDao<Moderation>{

    public ModeratorDAO() {
        super(Moderation.class);
    }

    @Override
    public void init() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(main.logic.User.User.class)
                .addAnnotatedClass(main.logic.User.Moderation.class)
                .addAnnotatedClass(main.logic.Direction.class)
                .addAnnotatedClass(main.logic.Country.class)
                .buildSessionFactory();
    }
}
