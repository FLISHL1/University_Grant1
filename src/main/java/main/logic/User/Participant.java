package main.logic.User;

import main.logic.User.User;

import java.sql.ResultSet;

public class Participant extends User {

    public Participant(ResultSet resultSet) {
        super(resultSet);
    }
    public Participant() {
        super();
    }

}
