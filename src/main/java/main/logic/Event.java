package main.logic;

import main.server.SqlSender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Event {
    public String id;
    public String name;
    public String date;
    public String days;
    public String city;
    public String logo;

    public String direction;

    public Event(ResultSet eventDB){
        try {
            id = eventDB.getString("id");
            name = eventDB.getString("name");
            date = eventDB.getString("date");
            days = eventDB.getString("days");
            city = eventDB.getString("city");
            logo = eventDB.getString("logo");
            direction = eventDB.getString("direction");

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
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
