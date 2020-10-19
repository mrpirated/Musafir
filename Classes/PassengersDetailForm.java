package Classes;

import java.io.*;

public class PassengersDetailForm implements Serializable {
    private String username;
    private String trainNo;
    private String date;
    private String src;
    private String dest;
    private String quota;
    private String mobile;
    private Integer noOfPassengers;
    PassengerInfo[] passengers = new PassengerInfo[noOfPassengers];

    public PassengersDetailForm(String username, String trainNo, String date, String src, String dest, String quota,
            String mobile, Integer noOfPassengers) {
        this.username = username;
        this.date = date;
        this.dest = dest;
        this.mobile = mobile;
        this.noOfPassengers = noOfPassengers;
        this.quota = quota;
        this.src = src;
        this.trainNo = trainNo;
    }

    public String getDate() {
        return date;
    }

    public String getDest() {
        return dest;
    }

    public String getMobile() {
        return mobile;
    }

    public Integer getNoOfPassengers() {
        return noOfPassengers;
    }

    public PassengerInfo[] getPassengers() {
        return passengers;
    }

    public String getQuota() {
        return quota;
    }

    public String getSrc() {
        return src;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public String getUsername() {
        return username;
    }
}
