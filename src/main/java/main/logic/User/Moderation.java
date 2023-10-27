package main.logic.User;

import main.logic.User.User;

import java.sql.ResultSet;

public class Moderation extends User {
    public Moderation(ResultSet idUser) {
        super(idUser);
    }

    public Moderation() {
        super();
    }

}
