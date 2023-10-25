package main.logic.User;

import main.logic.User.User;

import java.sql.ResultSet;

public class Participant extends User {

    public Participant(ResultSet idUser) {
        super(idUser);
    }
}
