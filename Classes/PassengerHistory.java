package Classes;

import java.io.Serializable;

public class PassengerHistory implements Serializable{
    private String name,seat;
    private int age;
    private Character gender;
    private float fare;
    public PassengerHistory(String name,String seat,int age,Character gender,float fare){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seat = seat;
        this.fare = fare;
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
    public float getFare() {
        return fare;
    }
    
}
