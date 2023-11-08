package main.logic.dao;

import jakarta.persistence.Query;
import jakarta.persistence.RollbackException;
import main.logic.Activity;
import main.logic.Event;
import main.logic.User.Moderation;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ActivityDAO extends AbstractDao<Activity>{
    Session manager;

    public ActivityDAO() {
        super(Activity.class);
    }

    public void openSession(){
        manager = this.sessionFactory.openSession();
    }
    public void closeSession(){
        manager.close();

    }
    public void refresh(Activity activity){
        manager.refresh(activity);
    }

    public List<Activity> getByModerator(Moderation user) {
        // Create a new EntityManager
        Transaction transaction = null;
        List<Activity> objects;
        try (Session manager = this.sessionFactory.openSession()) {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get all students from the table.
            // Note that the SQL is selecting from "Student" entity not the "student" table
            String hql = "from Activity WHERE idModerator = :moderator";
            Query query = manager.createQuery(hql);
            query.setParameter("moderator", user.getId());
            objects = query.getResultList();

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
}
