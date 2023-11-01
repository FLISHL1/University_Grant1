package main.logic.dao;

import jakarta.persistence.RollbackException;
import main.logic.Event;
import main.logic.User.User;
import main.passwordHash.PasswordHashing;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UserDAO extends AbstractDao<User>{
    Session manager;


    public UserDAO() {
        super(User.class);
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

    public User auth(Integer id, String password){
        Transaction transaction = null;
        User user = null;
        try (Session manager = sessionFactory.openSession()){
            transaction = manager.getTransaction();
            
            transaction.begin();
            
            user = getById(id);
            if (!PasswordHashing.checkPass(password, user.getPassword())){
                user = null;
            }
            
            transaction.commit();
        } catch (RollbackException e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        return user;
    }

    @Override
    public void init() {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(main.logic.User.User.class)
                .addAnnotatedClass(main.logic.User.Participant.class)
                .addAnnotatedClass(main.logic.User.Organizer.class)
                .addAnnotatedClass(main.logic.User.Jury.class)
                .addAnnotatedClass(main.logic.Direction.class)
                .addAnnotatedClass(main.logic.User.Moderation.class)
                .addAnnotatedClass(main.logic.Country.class)
                .addAnnotatedClass(main.logic.Action.class)
                .buildSessionFactory();
    }
}
