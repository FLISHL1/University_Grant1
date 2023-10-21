package main.logic;

import java.sql.ResultSet;

public class Moderation extends User{
    public Moderation(ResultSet idUser) {
        super(idUser);
    }
}
