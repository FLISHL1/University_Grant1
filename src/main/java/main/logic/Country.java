package main.logic;

import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "country_name")
    private String name;
    @Column(name = "english_name")
    private String nameEn;
    @Column(name = "code")
    private String code;
    @Column(name = "code2")
    private int codeInt;

    public Country() {

    }

/*    public static ArrayList<Country> getAllCountry(){
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
    }*/
    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getCodeInt() {
        return codeInt;
    }

    public void setCodeInt(int codeInt) {
        this.codeInt = codeInt;
    }

    public int getId() {
        return id;
    }
}
