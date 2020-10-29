package Classes;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;

public class PassengersDetailForm implements Serializable {
    // PassengerTicketDetails()
    private String name, Username, trainNo,trainName;
    private LocalDate date;
    private Integer noOfPassenger,type,src,dest;
    private PassengerInfo[] passengerInfo; 


    public PassengersDetailForm(String name, String trainNo,String trainName, int src, int dest,
            LocalDate date, Integer noOfPassenger,PassengerInfo[] passengerInfo,int type) {
        this.name = name;
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.src = src;
        this.dest = dest;
        this.date = date;
        this.noOfPassenger = noOfPassenger;
        this.passengerInfo = passengerInfo;
        this.type = type;
        passengerInfo = new PassengerInfo[noOfPassenger];
    }

    public LocalDate getDate() {
        return date;
    }

    public int getDest() {
        return dest;
    }

    public Integer getNoOfPassenger() {
        return noOfPassenger;
    }

    public int getSrc() {
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
    
    public Integer getType() {
        return type;
    }
}
