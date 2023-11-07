package main.logic.dao;

import main.logic.Confirmation;

public class ApplicationDAO extends AbstractDao<Confirmation>{
    public ApplicationDAO() {
        super(Confirmation.class);
    }
}
