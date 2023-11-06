package main.logic;

import jakarta.persistence.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.logic.User.Organizer;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "logo")
    private String logo;

    @Column(name = "event_name")
    private String name;

    @Column(name = "event_date_start")
    private LocalDateTime dateStart;
    @Column(name = "event_date_end")
    private LocalDateTime dateEnd;
    @ManyToOne()
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "city")
    private City city;
    @ManyToOne()
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "direction")
    private Direction direction;

    @OneToMany( fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.REMOVE, org.hibernate.annotations.CascadeType.PERSIST})
    @JoinColumn(name = "id_events")
    private List<Activity> activity = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_organizer", nullable = false)
    private Organizer organizer;

    private String description;

    public Event() {
    }

    public Organizer getOrganizer() {
        return organizer;
    }
    public void setOrganizer(Organizer user){
        this.organizer = user;
    }
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getDateStart() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return dateStart.format(formatter);
    }

    public LocalDateTime getDateStartToDate() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime date) {
        dateStart = date;
    }

    public LocalDateTime getDateEndToDate() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime date) {
        dateEnd = date;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public ImageView getLogo() {
        ImageView image = new ImageView(new Image("file:src/main/resources/main/photo/" + logo));
        image.setPreserveRatio(true);
        image.setFitWidth(150);
        image.setFitHeight(150);
        return image;
    }

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }
}
