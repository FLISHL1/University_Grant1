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
    public Integer id;
    public ImageView logo;

    public String  name;
    public String date;
    public String days;
    public String city;
    public String direction;



    public Event(ResultSet eventDB){

        try {
            id = eventDB.getInt("id");
            name = eventDB.getString("name");
            date = eventDB.getString("date");
            days = eventDB.getString("days");
            city = eventDB.getString("city");
            direction = eventDB.getString("direction");
            logo = new ImageView(new Image("file:src/main/resources/main/photo/" + eventDB.getString("logo")));
            logo.setPreserveRatio(true);
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
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDays() {
        return days;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getDirection() {
        return direction;
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
