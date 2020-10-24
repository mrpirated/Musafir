package Classes;

import java.io.*;
import java.sql.Timestamp;


public class AvailabilityInfo implements Serializable {
    private boolean available=false;
    private int train, sl, ac;
    private Timestamp arrival,departure;
    public AvailabilityInfo(boolean available,int train,int sl,int ac,Timestamp arrival, Timestamp departure){
        this.ac=ac;
        this.available = available;
        this.train = train;
        this.sl = sl;
        this.ac=ac;
        this.arrival= arrival;
        this.departure = departure;
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
    public int getTrain() {
        return train;
    }
    public boolean getAvailable(){
        return available;
    }
}
