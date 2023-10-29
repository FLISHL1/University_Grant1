package main.logic.User;

import jakarta.persistence.*;
import main.logic.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idNumber;

    private String name;

    private String email;
    private String phone;
    @Column(name = "birth_date")
    private Date birthDay;

    @ManyToOne
    @JoinColumn(name="country")
    private Country country;
    @Column(name="gender")
    private String sex;
    private String photo;
    private String password;

    public User(ResultSet user) {

            try {
                name = user.getString("name");
                System.out.println(name);
                email = user.getString("email");
                phone = user.getString("phone");
//                countryCode = user.getInt("country");
                birthDay = user.getDate("birth_date");
                photo = user.getString("photo");
                sex = user.getString("gender");
                idNumber = user.getInt("id");
                password = user.getString("password");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
//    public User(){
//        idNumber = createUser();
//    }
    public User(){}

//    public String createUser(){
//        ResultSet resultSet = SqlSender.createUser();
//        String idUser;
//
//        try {
//            idUser = resultSet.getString("idUser");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return idUser;
//    }
//    public void updateUser(){
//        String role = null;
//        if (this instanceof Jury) role = "jury";
//        else if (this instanceof Participant) role = "participant";
//        else if (this instanceof Organizer) role = "organizer";
//        else if (this instanceof Moderation) role = "moderator";
//        SqlSender.updateUser(this, role);
//    }
//
//    public void dropUser(){
//        SqlSender.dropUser(this);
//    }
//    public static User getUser(String idUser){
//        return UserSelection.getUser(idUser);
//    }
//
//    public static User checkAuth(String idUser, String password){
//        User user = User.getUser(idUser);
//        System.out.println(PasswordHashing.checkPass(password, user.password));
//        System.out.println(user.password);
//        if (user != null && PasswordHashing.checkPass(password, user.password)){
//            return user;
//        } else {
//            return null;
//        }
//    }

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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
    public Country getCountry() {
        return country;
    }

    public Integer getCountryCode() {
        return country.getId();
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }



    public Integer getId() {
        return idNumber;
    }


    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoto() {
        return photo;
    }
}
