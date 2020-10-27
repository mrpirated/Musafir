package Classes;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;

public class PassengersDetailForm implements Serializable {
    // PassengerTicketDetails()
    private String name, Username, trainNo, src, dest,trainName;
    private LocalDate date;
    private Integer noOfPassenger;
    private PassengerInfo[] passengerInfo = new PassengerInfo[noOfPassenger];


    public PassengersDetailForm(String name, String trainNo,String trainName, String src, String dest,
            LocalDate date, Integer noOfPassenger,PassengerInfo[] passengerInfo) {
        this.name = name;
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.src = src;
        this.dest = dest;
        this.date = date;
        this.noOfPassenger = noOfPassenger;
        this.passengerInfo = passengerInfo;
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
    public PassengerInfo[] getPassengerInfo() {
        return passengerInfo;
    }
}
