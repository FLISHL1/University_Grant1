package main.logic.User;


import jakarta.persistence.*;
import main.logic.Action;
import main.logic.Direction;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jury")
@PrimaryKeyJoinColumn(name = "id_user")
public class Jury extends User {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direction")
    private Direction direction;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="jury_activities",
            joinColumns=  @JoinColumn(name="id_jury", referencedColumnName="id_user"),
            inverseJoinColumns= @JoinColumn(name="id_activity", referencedColumnName="id") )
    private Set<Action> actions = new HashSet<Action>();
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

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
