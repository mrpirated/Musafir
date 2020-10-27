package Classes;

import java.io.*;
import java.time.*;
import java.sql.Date;
import java.sql.Timestamp;


public class AvailabilityInfo implements Serializable {
    private boolean available=false;
    private int  sl, ac, day1,day2;
    private float fare;
    private Timestamp arrival,departure;
    private String trainName,train;
    private Date date;
    public AvailabilityInfo(boolean available,String train,String trainName,int sl,int ac,Timestamp arrival, Timestamp departure,Date date,int day1,int day2,float fare){
        this.ac=ac;
        this.available = available;
        this.train = train;
        this.sl = sl;
        this.ac=ac;
        this.arrival= arrival;
        this.departure = departure;
        this.trainName = trainName;
        this.date = date;
        this.day1 = day1;
        this.day2 = day2;
        this.fare = fare;
    }
    public int getAc() {
        return ac;
    }
    public Timestamp getArrival() {
        return arrival;
    }
    public Timestamp getDeparture() {
        return departure;
    }
    public int getSl() {
        return sl;
    }
    public String getTrain() {
        return train;
    }
    public boolean getAvailable(){
        return available;
    }
    public String getTrainName() {
        return trainName;
    }
    public LocalDate getDate() {
        return date.toLocalDate();
    }
    public int getDay1() {
        return day1;
    }
    public int getDay2() {
        return day2;
    }
    public float getFare() {
        return fare;
    }
}
