package main.logic.dao;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.RollbackException;
import main.logic.User.Jury;
import main.logic.User.Moderation;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

public class ModeratorDAO extends AbstractDao<Moderation>{

    public ModeratorDAO() {
        super(Moderation.class);
    }
    public void createReg(Moderation moderation) {
        // Create a new EntityManager
        EntityTransaction transaction = null;

        try (Session manager = this.sessionFactory.openSession()) {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // First find the student to update the object
            // You cannot insert a new student with the same id since it will be treated as a duplicate entry
            String sql = "INSERT INTO moderators (id_user) VALUES (:idUser)";
            NativeQuery query = manager.createNativeQuery(sql);
            query.setParameter("idUser", moderation.getId());
            query.executeUpdate();
            manager.update(moderation);
//            objects = manager.createQuery("from " + clazz.getName()).list();


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
/*    @Override
    public void init() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(main.logic.User.User.class)
                .addAnnotatedClass(main.logic.User.Moderation.class)
                .addAnnotatedClass(main.logic.Direction.class)
                .addAnnotatedClass(main.logic.Country.class)
                .buildSessionFactory();
    }*/
}
