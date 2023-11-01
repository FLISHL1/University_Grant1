package main.logic.dao;

import main.logic.Direction;
import org.hibernate.cfg.Configuration;

public class DirectionDAO extends AbstractDao<Direction>{

    public DirectionDAO() {
        super(Direction.class);
    }

    @Override
    public void init() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(main.logic.Direction.class)
                .buildSessionFactory();
    }
}
