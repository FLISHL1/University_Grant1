package main.logic.User;

import main.passwordHash.PasswordHashing;
import main.server.Server;
import main.server.SqlSender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public abstract class User {
    private String name;

    private String email;
    private String phone;
    private Date date_birthDay;
    private String countryCode;
    private String sex;
    private String photo;
    private String idNumber;
    private String password;

    public User(ResultSet user) {

            try {
                name = user.getString("name");
                email = user.getString("email");
                phone = user.getString("phone");
                countryCode = user.getString("country");
                date_birthDay = user.getDate("birth_date");
                photo = user.getString(photo);
                sex = user.getString("gender");
                idNumber = user.getString("id");
                password = user.getString("password");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
    public User(){
        idNumber = createUser();
    }

    public String createUser(){
        ResultSet resultSet = SqlSender.createUser();
        String idUser;

        try {
            idUser = resultSet.getString("idUser");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idUser;
    }

    public static User getUser(String idUser){
        return UserSelection.getUser(idUser);
    }

    public static User checkAuth(String idUser, String password){
        User user = User.getUser(idUser);
        System.out.println(password);
        if (user != null && PasswordHashing.checkPass(password, user.password)){
            return user;
        } else {
            return null;
        }
    }

    public String getPassword(){ return password;}
    public void setPassword(String newPassword){ password = newPassword;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate_birthDay() {
        return date_birthDay;
    }

    public void setDate_birthDay(Date date_birthDay) {
        this.date_birthDay = date_birthDay;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoto() {
        return photo;
    }
}
