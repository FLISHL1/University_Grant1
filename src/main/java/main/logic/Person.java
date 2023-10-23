package main.logic;

import javafx.beans.property.*;

public class Person{

    private SimpleStringProperty name;
    private SimpleIntegerProperty age;

    public Person(String name, int age){
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
    }

    public String getName(){ return name.get();}
    public void setName(String value){ name.set(value);}

    public int getAge(){ return age.get();}
    public void setAge(int value){ age.set(value);}
}