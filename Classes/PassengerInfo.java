package Classes;

import java.io.Serializable;

public class PassengerInfo implements Serializable{
    private String name, berthPreference;
    private Integer age;
    private Character gender;
    private float fare;

    public PassengerInfo(String name, Integer age, Character gender, String berthPreference,float fare) {
        this.name = name;
        this.age = age;
        this.berthPreference = berthPreference;
        this.gender = gender;
        this.fare = fare;
    }
    

    public Integer getAge() {
        return age;
    }

    public String getBerthPreference() {
        return berthPreference;
    }

    public Character getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }
    public float getFare() {
        return fare;
    }
    public Boolean isSenior(){
        if(age >=58 && gender.equals('F'))
        return true;
        else if(age >=60)
        return true;
        else return false;
    }
}
