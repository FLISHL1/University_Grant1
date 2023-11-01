package main.logic;

import jakarta.persistence.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.logic.User.Organizer;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "logo")
    public String logo;

    @Column(name = "event_name")
    public String  name;

    @Column(name = "event_date")
    public Date date;
    @Column(name = "event_days")
    public String days;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city")
    public City city;
    @ManyToOne
    @JoinColumn(name = "direction")
    public Direction direction;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="id_organizer", nullable=false)
    public Organizer organizer;

    public String description;

    public Event(){
    }

    public Organizer getUser(){return organizer;}
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDays() {
        return days;
    }

    public String getCity() {
        return city.getName();
    }

    public String getDate() {
        SimpleDateFormat dat = new SimpleDateFormat("dd.MM.yyyy");

        return dat.format(date);
    }

    public String getDirection() {
        return direction.name;
    }

    public ImageView getLogo()
    {
        ImageView image = new ImageView(new Image("file:src/main/resources/main/photo/" + logo));
        image.setPreserveRatio(true);
        image.setFitWidth(150);
        image.setFitHeight(150);
        return image;}

}
