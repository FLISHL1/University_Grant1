package main.logic.User;


import jakarta.persistence.*;
import main.logic.Event;

import java.sql.ResultSet;
import java.util.List;

@Entity
@Table(name = "organizers")
@PrimaryKeyJoinColumn(name = "id_user")
public class Organizer extends User {

    public Organizer() {
        super();
    }

}
