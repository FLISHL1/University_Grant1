package main.logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
    public String logo;
    public SimpleStringProperty direction;



    public Event(ResultSet eventDB){
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        date = new SimpleStringProperty();
        days = new SimpleStringProperty();
        city = new SimpleStringProperty();
        direction = new SimpleStringProperty();
        try {
            id.set(eventDB.getInt("id"));
            name.set(eventDB.getString("name"));
            date.set(eventDB.getString("date"));
            days.set(eventDB.getString("days"));
            city.set(eventDB.getString("city"));
            logo = eventDB.getString("logo");
            direction.set(eventDB.getString("direction"));

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
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
