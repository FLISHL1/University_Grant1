package main.logic;

import main.server.SqlSender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class City {
    private int id;
    private String name;
    private int country;


    public City(ResultSet city) {

        try {
            name = city.getString("name");
            id = city.getInt("id");
            country = city.getInt("id_country");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static ArrayList<City> getAllCity(){
        ResultSet resultSet = SqlSender.getAllCity();
        ArrayList<City> cites = new ArrayList<>();
        try {
            while (resultSet.next()){
                cites.add(new City(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cites;
    }
    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
