package main.logic;

import jakarta.persistence.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.logic.User.Organizer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private Date dateStart;
    @Column(name = "event_date_end")
    private Date dateEnd;
    @ManyToOne()
    @JoinColumn(name = "city")
    private City city;
    @ManyToOne
    @JoinColumn(name = "direction")
    private Direction direction;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_events")
    private List<Activity> activity = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_organizer", nullable = false)
    private Organizer organizer;

    private String description;

    public Event() {
    }

    public Organizer getUser() {
        return organizer;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public String getCity() {
        return city.getName();
    }

    public String getDateStart() {
        SimpleDateFormat dat = new SimpleDateFormat("dd.MM.yyyy");

        return dat.format(dateStart);
    }

    public String getDirection() {
        return direction.name;
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
}
