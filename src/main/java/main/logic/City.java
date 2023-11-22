package main.logic;

import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column()
    private String name;

    public City(String name) {
        this.name = name;
    }

    public City() {

    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
