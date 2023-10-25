package main.logic.User;

import main.logic.User.User;

import java.sql.ResultSet;

public class Jury extends User {

    public Jury(ResultSet idUser) {
        super(idUser);
    }
}
