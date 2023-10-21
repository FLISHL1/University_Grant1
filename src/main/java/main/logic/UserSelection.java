package main.logic;

import main.controller.AlertShow;
import main.passwordHash.PasswordHashing;
import main.server.SqlSender;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSelection {
    public static User getUser(String idUser)  {
        try {
            ResultSet resultSet = SqlSender.getUser(idUser);
            if (resultSet != null) {
                switch (resultSet.getString("type_user")) {
                    case "participant":
                        return new Participant(resultSet);
                    case "moderator":
                        return new Moderation(resultSet);
                    case "organizer":
                        return new Organizer(resultSet);
                    case "jury":
                        return new Jury(resultSet);
                }
            } else {
                System.out.println("Пользователь не найден");

            }
        }catch (Exception e){

        }
        return null;
    }
    public static User checkAuth(String idUser, String password){
        User user = UserSelection.getUser(idUser);
        System.out.println(password);
        if (user != null && PasswordHashing.checkPass(password, user.password)){
            return user;
        } else {
            AlertShow.showAlert("info", "Ошибка", "Вы ввели не правильный логин или пароль");
            return null;
        }
    }
}
