package main.logic.User;


import jakarta.persistence.*;
import main.logic.Activity;
import main.logic.Direction;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jury")
@PrimaryKeyJoinColumn(name = "id_user")
public class Jury extends User {
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "direction")
    private Direction direction;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "jury_activities",
            joinColumns = @JoinColumn(name = "id_jury", referencedColumnName = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_activity", referencedColumnName = "id"))
    private Set<Activity> activities = new HashSet<Activity>();

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
    public Direction getDirectionToObj() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
