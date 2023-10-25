package main.logic.User;

import main.logic.User.User;

import java.sql.ResultSet;

public class Organizer extends User {
    public Organizer(ResultSet idUser) {
        super(idUser);
    }
}
