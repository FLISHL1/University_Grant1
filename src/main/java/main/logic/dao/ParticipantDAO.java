package main.logic.dao;

import main.logic.User.Participant;
import org.hibernate.cfg.Configuration;

public class ParticipantDAO extends AbstractDao<Participant>{


    public ParticipantDAO() {
        super(Participant.class);
    }

/*    @Override
    public void init() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(main.logic.User.Participant.class)
                .addAnnotatedClass(main.logic.Country.class)
                .buildSessionFactory();    }*/
}
