package main.logic.dao;

import main.logic.Country;
import org.hibernate.cfg.Configuration;

public class CountryDAO extends AbstractDao<Country>{

    public CountryDAO() {
        super(Country.class);
    }

    @Override
    public void init() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(main.logic.Country.class)
                .buildSessionFactory();
    }
}
