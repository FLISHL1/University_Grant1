package main.logic.dao;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.RollbackException;
import main.logic.Event;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class EventDAO extends AbstractDao<Event>{
    Session manager;
    public EventDAO() {

        super(Event.class);
    }
    public void openSession(){
        manager = this.sessionFactory.openSession();
    }
    public void closeSession(){
        manager.close();

    }
    public Event merge(Event event){
        return manager.merge(event);
    }
    public void refresh(Event event){
        manager.refresh(event);
    }

    @Override
    public Event getById(Integer id) {
        // Create a new EntityManager
        Transaction transaction = null;
        Event object;
        try (Session manager = this.sessionFactory.openSession()){
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get all students from the table.
            // Note that the SQL is selecting from "Student" entity not the "student" table
            object = manager.get(Event.class, id);

            // Commit the transaction
            transaction.commit();
        } catch (RollbackException ex) {
            // Commit failed. Rollback the transaction
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException(ex);
        }
        return object;
    }

    public Event create(Event event) {
        // Create a new EntityManager
        ;
        EntityTransaction transaction = null;

        try (Session manager = this.sessionFactory.openSession()){
            // Get a transaction
            transaction = manager.getTransaction();

            transaction.begin();
            // Save the student object
            manager.saveOrUpdate(event);
            // Commit the transaction
            transaction.commit();
        } catch (RollbackException ex) {
            // Commit failed. Rollback the transaction
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(ex);
        }
        return event;
    }
/*    @Override
    public void init() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(main.logic.User.Organizer.class)
                .addAnnotatedClass(main.logic.User.User.class)
                .addAnnotatedClass(main.logic.Direction.class)
                .addAnnotatedClass(main.logic.Event.class)
                .addAnnotatedClass(main.logic.City.class)
                .addAnnotatedClass(main.logic.Country.class)
                .buildSessionFactory();
    }*/


}
