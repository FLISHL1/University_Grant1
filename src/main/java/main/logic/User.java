package main.logic;

import main.passwordHash.PasswordHashing;
import main.server.Server;
import main.server.SqlSender;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class User {
    public String name;

    public String email;
    public String phone;
    public String date_birthDay;
    public String countryCode;
    public String sex;
    public String idNumber;
    public String password;

    public User(ResultSet user) {

            try {
                name = user.getString("name");
                email = user.getString("email");
                phone = user.getString("phone");
                countryCode = user.getString("country");
                sex = user.getString("gender");
                idNumber = user.getString("id");
                password = user.getString("password");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }


}
