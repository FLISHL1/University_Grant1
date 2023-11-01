package main.logic;

import jakarta.persistence.*;

@Entity
@Table(name = "direction")
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "name")
    public String name;

    public Direction(){}


    public Direction(String name){
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}
