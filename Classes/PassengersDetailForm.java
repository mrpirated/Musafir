package Classes;

import java.io.*;
import java.sql.Date;

public class PassengersDetailForm implements Serializable {
    // PassengerTicketDetails()
    private String name, Username, trainNo, type, src, dest;
    private Date date;
    private Integer noOfPassenger;

    public PassengersDetailForm(String name, String Username, String trainNo, String type, String src, String dest,
            Date date, Integer noOfPassenger) {
        this.name = name;
        this.Username = Username;
        this.trainNo = trainNo;
        this.type = type;
        this.src = src;
        this.dest = dest;
        this.date = date;
        this.noOfPassenger = noOfPassenger;
    }

    public Date getDate() {
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

    public String getType() {
        return type;
    }

    public String getUsername() {
        return Username;
    }
}
