package main.logic.dao;

import main.logic.Activity;
import main.logic.Event;
import org.hibernate.Session;

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
    public Event merge(Event event){
        return manager.merge(event);
    }
}
