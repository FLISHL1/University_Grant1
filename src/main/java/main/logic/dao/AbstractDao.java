package main.logic.dao;

import jakarta.persistence.*;
import main.attentionWindow.AlertShow;
import main.logic.*;
import main.logic.User.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public abstract class AbstractDao<T> {

    protected static SessionFactory sessionFactory;
    private final Class<T> clazz;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
        sessionFactory = getInstance();

    }

    private static class SignSession {
        static SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Participant.class)
                .addAnnotatedClass(Organizer.class)
                .addAnnotatedClass(Jury.class)
                .addAnnotatedClass(Moderation.class)
                .addAnnotatedClass(Event.class)
                .addAnnotatedClass(Activity.class)
                .addAnnotatedClass(Direction.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Confirmation.class)
                .addAnnotatedClass(City.class)
                .buildSessionFactory();
    }

    ;

    private static SessionFactory getInstance() {
        return SignSession.sessionFactory;
    }

    public List<T> getAll() {
        // Create a new EntityManager
        Transaction transaction = null;
        List<T> objects;
        try (Session manager = this.sessionFactory.openSession()) {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get all students from the table.
            // Note that the SQL is selecting from "Student" entity not the "student" table
            objects = manager.createQuery("from " + clazz.getName()).list();


            // Commit the transaction
            transaction.commit();
        } catch (RollbackException ex) {
            // Commit failed. Rollback the transaction
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException(ex);
        }
        return objects;
    }

    public T getById(Integer id) {
        // Create a new EntityManager
        Transaction transaction = null;
        T object;
        try (Session manager = this.sessionFactory.openSession()) {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get all students from the table.
            // Note that the SQL is selecting from "Student" entity not the "student" table
            object = manager.get(clazz, id);

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

    public T create(T object) {
        // Create a new EntityManager
        ;
        EntityTransaction transaction = null;

        try (Session manager = this.sessionFactory.openSession()) {
            // Get a transaction
            transaction = manager.getTransaction();

            transaction.begin();
            // Save the student object
            manager.persist(object);
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

    public void update(T objectUpdate) {
        // Create a new EntityManager
        EntityTransaction transaction = null;

        try (Session manager = this.sessionFactory.openSession()) {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // First find the student to update the object
            // You cannot insert a new student with the same id since it will be treated as a duplicate entry
            manager.merge(objectUpdate);
            // Commit the transaction
            transaction.commit();
        } catch (RollbackException ex) {
            // Commit failed. Rollback the transaction
            if (transaction != null) {
                transaction.rollback();
            }
            // TODO: Decide how you want to handle this exception.
            //  Since this is a hello world project, we throw the exception.
            throw new RuntimeException(ex);
        }
    }

    public void delete(int id) {
        EntityTransaction transaction = null;

        try (Session manager = this.sessionFactory.openSession()) {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // First find the student
            T object = manager.get(clazz, id);
            if (object != null) {
                // Remove the student
                manager.remove(object);
            }
            // Commit the transaction
            transaction.commit();
        } catch (RollbackException ex) {
            // Commit failed. Rollback the transaction
            if (transaction != null) {
                transaction.rollback();
            }
            // TODO: Decide how you want to handle this exception.
            //  Since this is a hello world project, we throw the exception.
            throw new RuntimeException(ex);
        }
    }

    public void delete(T object) {
        EntityTransaction transaction = null;

        try (Session manager = this.sessionFactory.openSession()) {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // First find the student
            if (object != null) {
                // Remove the student
                manager.remove(object);
            }
            // Commit the transaction
            transaction.commit();
        } catch (RollbackException ex) {
            // Commit failed. Rollback the transaction
            if (transaction != null) {
                transaction.rollback();
            }
            // TODO: Decide how you want to handle this exception.
            //  Since this is a hello world project, we throw the exception.
            throw new RuntimeException(ex);
        }
    }

/*    public void deleteAll() {
        EntityTransaction transaction = null;

        try (Session manager = this.sessionFactory.openSession()){
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // Delete all students
            manager.createQuery("DELETE FROM User_Test ")
                    .executeUpdate();
            // Commit the transaction
            transaction.commit();
        } catch (RollbackException ex) {
            // Commit failed. Rollback the transaction
            if (transaction != null) {
                transaction.rollback();
            }
            // TODO: Decide how you want to handle this exception.
            //  Since this is a hello world project, we throw the exception.
            throw new RuntimeException(ex);
        } finally {
            // Close the EntityManager
            manager.close();
        }
    }*/
}
