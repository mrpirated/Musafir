package Classes;

import java.util.*;
import java.sql.*;
import java.io.*;

public class BookingHistory2FinalInfo implements Serializable {
    private String trainNo, trainName, src, dest, pnr;
    private java.sql.Date doj;
    private Vector<BookingHistory2TicketInfo> passengersInfo;

    public BookingHistory2FinalInfo(String trainNo, String trainName, String src, String dest,
            Vector<BookingHistory2TicketInfo> passengersInfo, java.sql.Date doj, String pnr) {
        this.trainName = trainName;
        this.trainNo = trainNo;
        this.dest = dest;
        this.passengersInfo = passengersInfo;
        this.src = src;
        this.doj = doj;
        this.pnr = pnr;
    }

    public String getDest() {
        return dest;
    }

    public Vector<BookingHistory2TicketInfo> getPassengersInfo() {
        return passengersInfo;
    }

    public String getSrc() {
        return src;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public java.sql.Date getDoj() {
        return doj;
    }

    public String getPnr() {
        return pnr;
    }
}
