package main.logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.server.SqlSender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Event {
    public SimpleIntegerProperty id;
    public SimpleStringProperty name;
    public SimpleStringProperty date;
    public SimpleStringProperty days;
    public SimpleStringProperty city;
    public ImageView logo;
    public SimpleStringProperty direction;



    public Event(ResultSet eventDB){

        try {
            id = new SimpleIntegerProperty(eventDB.getInt("id"));
            name = new SimpleStringProperty(eventDB.getString("name"));
            date = new SimpleStringProperty(eventDB.getString("date"));
            days = new SimpleStringProperty(eventDB.getString("days"));
            city = new SimpleStringProperty(eventDB.getString("city"));
            direction = new SimpleStringProperty(eventDB.getString("direction"));
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            logo = new ImageView(new Image("file:src/main/resources/main/photo/" + eventDB.getString("logo")));
            logo.setPreserveRatio(true);
            System.out.println(logo.getImage().getUrl());
            logo.setFitWidth(150);
            logo.setFitHeight(150);
//            logo = eventDB.getString("logo");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Event(){


    }

    public Integer getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getDays() {
        return days.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getDirection() {
        return direction.get();
    }

    public ImageView getLogo(){ return logo;}

    public static ArrayList<Event> getAllEvents(){
        ResultSet resultSet = SqlSender.getAllEvent();
        ArrayList<Event> events = new ArrayList<>();
        try {
            while (resultSet.next()){
                events.add(new Event(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }
}
