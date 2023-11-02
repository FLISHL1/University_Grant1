package main.logic.dao;

import main.logic.Activity;
import main.logic.User.Jury;
import org.hibernate.cfg.Configuration;

public class JuryDAO extends AbstractDao<Jury>{
    public JuryDAO() {
        super(Jury.class);
    }

/*    @Override
    public void init() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(main.logic.User.User.class)
                .addAnnotatedClass(main.logic.User.Jury.class)
                .addAnnotatedClass(main.logic.Direction.class)
                .addAnnotatedClass(Activity.class)
                .addAnnotatedClass(main.logic.Country.class)
                .buildSessionFactory();
    }*/
}
