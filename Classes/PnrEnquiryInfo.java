package Classes;

import java.io.*;

public class PnrEnquiryInfo implements Serializable {
    private String name, coach_no, gender;
    private Integer type, seat_no, waiting, age;
    private char meal;

    public PnrEnquiryInfo(String name, Integer type, String coach_no, Integer seat_no, Integer waiting, Integer age,
            String gender, char meal) {
        this.name = name;
        this.type = type;
        this.coach_no = coach_no;
        this.seat_no = seat_no;
        this.waiting = waiting;
        this.age = age;
        this.gender = gender;
        this.meal = meal;
    }

    public Integer getAge() {
        return age;
    }

    public String getCoach_no() {
        return coach_no;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public Integer getSeat_no() {
        return seat_no;
    }

    public Integer getType() {
        return type;
    }

    public Integer getWaiting() {
        return waiting;
    }

    public char getMeal() {
        return meal;
    }
}
