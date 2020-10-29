package Classes;

import java.sql.*;
import java.io.*;

public class AddTrainAdminNextInfo implements Serializable {
    private String station;
    private java.sql.Time arrival, departure;
    private Integer distance, day, platform, fare, number;

    public AddTrainAdminNextInfo(Integer number, String station, java.sql.Time arrival, java.sql.Time departure,
            Integer distance, Integer day, Integer platform, Integer fare) {
        this.number = number;
        this.station = station;
        this.arrival = arrival;
        this.departure = departure;
        this.distance = distance;
        this.day = day;
        this.platform = platform;
        this.fare = fare;
    }

    public java.sql.Time getArrival() {
        return arrival;
    }

    public Integer getDay() {
        return day;
    }

    public java.sql.Time getDeparture() {
        return departure;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getFare() {
        return fare;
    }

    public Integer getPlatform() {
        return platform;
    }

    public String getStation() {
        return station;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
