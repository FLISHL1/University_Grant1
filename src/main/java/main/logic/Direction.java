package main.logic;

import jakarta.persistence.*;

@Entity
@Table(name = "direction")
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String name;
}
