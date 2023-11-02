package main.logic.User;


import jakarta.persistence.*;
import main.logic.Application;
import main.logic.Direction;

import java.sql.ResultSet;
import java.util.List;

@Entity
@Table(name = "moderators")
@PrimaryKeyJoinColumn(name = "id_user")
public class Moderation extends User {
    @ManyToOne()
    @JoinColumn(name = "direction")
    private Direction direction;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_moderator")
    private List<Application> applications;
    public Moderation(ResultSet idUser) {
        super(idUser);
    }

    public Moderation() {
        super();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
