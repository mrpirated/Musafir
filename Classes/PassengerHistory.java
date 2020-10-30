package Classes;

import java.io.Serializable;

public class PassengerHistory implements Serializable{
    private String name,seat;
    private int age;
    private Character gender;
    public PassengerHistory(String name,String seat,int age,Character gender){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seat = seat;
    }
    public int getAge() {
        return age;
    }
    public Character getGender() {
        return gender;
    }
    public String getName() {
        return name;
    }
    public String getSeat() {
        return seat;
    }
    
}
