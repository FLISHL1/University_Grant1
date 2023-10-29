package main.logic.User;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.sql.ResultSet;

@Entity
@Table(name = "participants")
@PrimaryKeyJoinColumn(name = "id_user")
public class Participant extends User {

    public Participant(ResultSet resultSet) {
        super(resultSet);
    }
    public Participant() {
        super();
    }

}
