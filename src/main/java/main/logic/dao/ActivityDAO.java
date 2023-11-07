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
    public Activity merge(Activity activity){
        return manager.merge(activity);
    }


}
