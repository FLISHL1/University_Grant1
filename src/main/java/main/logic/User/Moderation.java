package main.logic.User;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.sql.ResultSet;

@Entity
@Table(name = "moderators")
@PrimaryKeyJoinColumn(name = "id_user")
public class Moderation extends User {
    public Moderation(ResultSet idUser) {
        super(idUser);
    }

    public Moderation() {
        super();
    }

}
