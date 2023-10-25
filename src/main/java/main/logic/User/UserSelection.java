package main.logic.User;

import main.attentionWindow.AlertShow;
import main.passwordHash.PasswordHashing;
import main.server.SqlSender;

import java.sql.ResultSet;

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

}
