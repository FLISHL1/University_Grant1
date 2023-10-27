package main.server;

import main.logic.User.*;

import java.sql.*;

public class SqlSender {
    private static Connection sqlServer = Server.getInstance().getConnection();
    public static ResultSet getUser(String idNumber) {
        String query = "CALL getUser(?)";
        PreparedStatement prepareCall;
        try {
            prepareCall = sqlServer.prepareCall(query);
            prepareCall.setString(1, idNumber);
            ResultSet result = prepareCall.executeQuery();

            return valid(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static ResultSet getAllEvent(){
        String query = "CALL getAllEvent()";
        PreparedStatement prepareCall;
        try {
            prepareCall = sqlServer.prepareCall(query);
            ResultSet result = prepareCall.executeQuery();

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ResultSet getAllCity(){
        String query = "SELECT * FROM cities";
        PreparedStatement prepareCall;
        try {
            prepareCall = sqlServer.prepareCall(query);
            ResultSet result = prepareCall.executeQuery();

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getAllCountry(){
        String query = "CALL getAllCountry()";
        PreparedStatement prepareCall;
        try {
            prepareCall = sqlServer.prepareCall(query);
            ResultSet result = prepareCall.executeQuery();

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet createUser(){
        String query = "SELECT createUser() as idUser;";
        PreparedStatement prepareCall;
        try {
            prepareCall = sqlServer.prepareCall(query);
            ResultSet result = prepareCall.executeQuery();
            result.next();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet updateUser(User user){
        String query = "CALL updateUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prepareCall;
        try {
            prepareCall = sqlServer.prepareCall(query);
            prepareCall.setInt(1, Integer.parseInt(user.getIdNumber()));
            prepareCall.setString(2, user.getName());
            prepareCall.setString(3, user.getEmail());
            prepareCall.setDate(4, new Date(user.getDate_birthDay().getTime()));
            prepareCall.setString(5, user.getCountryCode());
            prepareCall.setString(6, user.getPhone());
            prepareCall.setString(7, user.getPassword());
            prepareCall.setString(8, user.getPhoto());
            prepareCall.setString(9, user.getSex());

            String role = null;
            if (user instanceof Jury) role = "jury";
            else if (user instanceof Participant) role = "participant";
            else if (user instanceof Organizer) role = "organizer";
            else if (user instanceof Moderation) role = "moderator";

            prepareCall.setString(10, role);

            ResultSet result = prepareCall.executeQuery();

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static ResultSet valid(ResultSet result) throws SQLException {
        if (result.next()) {
            return result;
        } else {
            return null;
        }
    }
}
