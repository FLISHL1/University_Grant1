package main.logic;

import main.server.SqlSender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Country {
    private int id;
    private String name;
    private String nameEn;
    private String code;
    private int codeInt;

    public Country(ResultSet country) {

        try {
            name = country.getString("name");
            id = country.getInt("id");
            nameEn = country.getString("nameEn");
            code = country.getString("code");
            codeInt = country.getInt("code2");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static ArrayList<Country> getAllCountry(){
        ResultSet resultSet = SqlSender.getAllCountry();
        ArrayList<Country> countries = new ArrayList<>();
        try {
            while (resultSet.next()){
                countries.add(new Country(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countries;
    }
    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
