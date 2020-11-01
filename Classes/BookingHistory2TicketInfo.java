package Classes;

import java.io.*;

public class BookingHistory2TicketInfo implements Serializable {
    private String name, gender, seat;
    private Integer age, cancelled;

    public BookingHistory2TicketInfo(String name, Integer age, String gender, String seat, Integer cancelled) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seat = seat;
        this.cancelled = cancelled;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getSeat() {
        return seat;
    }

    public Integer getCancelled() {
        return cancelled;
    }
}
