package main.logic;

import jakarta.persistence.*;
import main.logic.User.Jury;

import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "id_moderator")
    private Integer idModerator;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity")
    private List<Confirmation> confirmations;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "jury_activities",
            joinColumns = @JoinColumn(name = "id_activity", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_jury", referencedColumnName = "id_user"))
    private List<Jury> juries;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
    @JoinColumn(name = "id_events")
    private Event event;


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
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Confirmation> getApplications() {
        return confirmations;
    }

    public void setApplications(List<Confirmation> confirmations) {
        this.confirmations = confirmations;
    }

    public Integer getIdModerator() {
        return idModerator;
    }

    public void setIdModerator(Integer idModerator) {
        this.idModerator = idModerator;
    }
}
