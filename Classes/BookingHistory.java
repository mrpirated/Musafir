package Classes;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

public class BookingHistory implements Serializable{
    private int userid;
    private String PNR,src,dest,train;
    private PassengerHistory[] passengerHistory;
    private Date date;
    
    
    public void setTrain(String train) {
        this.train = train;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setDest(String dest) {
        this.dest = dest;
    }
    
    public void setPNR(String pNR) {
        PNR = pNR;
    }
    public void setSrc(String src) {
        this.src = src;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public void setPassengerHistory(PassengerHistory[] passengerHistory) {
        this.passengerHistory = passengerHistory;
    }
    public String getTrain() {
        return train;
    }
    public Date getDate() {
        return date;
    }
    public String getDest() {
        return dest;
    }
    
    public String getPNR() {
        return PNR;
    }
    public String getSrc() {
        return src;
    }
    public int getUserid() {
        return userid;
    }
    public PassengerHistory[] getPassengerHistory() {
        return passengerHistory;
    }
    
}
