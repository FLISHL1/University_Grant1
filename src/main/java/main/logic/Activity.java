package main.logic;

import jakarta.persistence.*;
import main.logic.User.Jury;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "start_time")
    private LocalDate startTime;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "jury_activities",
            joinColumns = @JoinColumn(name = "id_activity", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_jury", referencedColumnName = "id_user"))
    private List<Jury> juries;


    public Activity(){
    }
    public String getName(){ return name;}

    public void setName(String name){ this.name = name;}
    @Override
    public String toString() {
        return name;
    }

    public List<Jury> getJuries(){
        return juries;
    }
    public void setJuries(List<Jury> juries){
        this.juries = juries;
    }
    public String getStartTime() {
        return startTime.toString();
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }
}
