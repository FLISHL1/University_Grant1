package main.logic;

import jakarta.persistence.*;
import main.logic.User.Jury;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "activities")
public class Action {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="jury_activities",
            joinColumns=  @JoinColumn(name="id_activity", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="id_jury", referencedColumnName="id_user") )
    private Set<Jury> juries = new HashSet<Jury>();

    @Column(name="name")
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
