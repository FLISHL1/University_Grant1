package main.logic.User;

import jakarta.persistence.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    protected Integer idNumber;

    protected String name;

    protected String email;
    protected String phone;
    @Column(name = "birth_date")
    protected Date birthDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="country")
    protected Country country;
    @Column(name="gender")
    protected String sex;
    protected String photo;
    protected String password;

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
    public User(){}

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
    public String getRole(){
        String role = null;
        if (this instanceof Moderation){
            role = "Moderator";
        } else if (this instanceof Jury){
            role = "Jury";
        }
        return role;
    }
    public ImageView getPhoto() {
        ImageView image = new ImageView(new Image("file:src/main/resources/main/photo/Users/" + photo));
        image.setPreserveRatio(true);
        image.setFitWidth(150);
        image.setFitHeight(150);
        return image;
    }
}
