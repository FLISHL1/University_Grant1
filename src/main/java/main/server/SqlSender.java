package main.server;

import main.logic.User;

import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private static ResultSet valid(ResultSet result) throws SQLException {
        if (result.next()) {
            return result;
        } else {
            return null;
        }
    }
}
