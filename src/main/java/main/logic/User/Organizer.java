package main.logic.User;


import jakarta.persistence.*;
import main.logic.Confirmation;

import java.util.List;

@Entity
@Table(name = "organizers")
@PrimaryKeyJoinColumn(name = "id_user")
public class Organizer extends User {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_organizator")
    private List<Confirmation> confirmations;
    public Organizer() {
        super();
    }

}
