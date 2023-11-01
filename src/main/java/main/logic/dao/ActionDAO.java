package main.logic.dao;

import jakarta.persistence.RollbackException;
import main.logic.Action;
import main.logic.Event;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ActionDAO extends AbstractDao<Action>{
    public ActionDAO() {
        super(Action.class);
    }
    public String getNameById(Integer id) {
        // Create a new EntityManager
        Transaction transaction = null;
        Event object;
        String name = null;
        try (Session manager = this.sessionFactory.openSession()){
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get all students from the table.
            // Note that the SQL is selecting from "Student" entity not the "student" table
            object = manager.load(Event.class, id);
            name = object.getName();
            // Commit the transaction
            transaction.commit();
        } catch (RollbackException ex) {
            // Commit failed. Rollback the transaction
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException(ex);
        }
        return name;
    }
    @Override
    public void init() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(main.logic.User.User.class)
                .addAnnotatedClass(main.logic.User.Jury.class)
                .addAnnotatedClass(main.logic.Direction.class)
                .addAnnotatedClass(main.logic.Action.class)
                .addAnnotatedClass(main.logic.Country.class)
                .buildSessionFactory();
    }
}
