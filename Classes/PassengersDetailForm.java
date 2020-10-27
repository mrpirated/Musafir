package Classes;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;

public class PassengersDetailForm implements Serializable {
    // PassengerTicketDetails()
    private String name, Username, trainNo, src, dest,trainName;
    private LocalDate date;
    private Integer noOfPassenger;

    public PassengersDetailForm(String name, String Username, String trainNo,String trainName, String src, String dest,
            LocalDate date, Integer noOfPassenger) {
        this.name = name;
        this.Username = Username;
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.src = src;
        this.dest = dest;
        this.date = date;
        this.noOfPassenger = noOfPassenger;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDest() {
        return dest;
    }

    public Integer getNoOfPassenger() {
        return noOfPassenger;
    }

    public String getSrc() {
        return src;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return Username;
    }
    public String getTrainName() {
        return trainName;
    }
}
