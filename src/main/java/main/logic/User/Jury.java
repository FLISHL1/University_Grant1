package main.logic.User;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.sql.ResultSet;
@Entity
@Table(name = "jury")
@PrimaryKeyJoinColumn(name = "id_user")
public class Jury extends User {
    private Integer direction;


    public Jury(ResultSet idUser) {
        super(idUser);
    }

    public Jury() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.getName());
        stringBuilder.append(this.getId());
        return stringBuilder.toString();
    }

    public String getDirection() {
        return direction.toString();
    }
}
