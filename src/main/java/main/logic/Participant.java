package main.logic;

import java.sql.ResultSet;

public class Participant extends User{

    public Participant(ResultSet idUser) {
        super(idUser);
    }
}
