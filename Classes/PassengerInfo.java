package Classes;

import java.io.Serializable;

public class PassengerInfo implements Serializable{
    private String name, berthPreference;
    private Integer age;
    private Character gender;
    private Boolean full = true;

    public PassengerInfo(String name, Integer age, Character gender, String berthPreference) {
        this.name = name;
        this.age = age;
        this.berthPreference = berthPreference;
        this.gender = gender;
    }
    public PassengerInfo(String name, Integer age, Character gender, String berthPreference, Boolean full) {
        this.name = name;
        this.age = age;
        this.berthPreference = berthPreference;
        this.gender = gender;
        this.full = full;
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
    public Boolean getFull() {
        return full;
    }
    public Boolean isSenior(){
        if(age >=58 && gender.equals('F'))
        return true;
        else if(age >=60)
        return true;
        else return false;
    }
}
