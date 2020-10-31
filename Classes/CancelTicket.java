package Classes;

import java.io.Serializable;
import java.sql.Date;

public class CancelTicket implements Serializable{
    private String PNR,name,src,dest,train;
    private int userid;
    private Date date;
    public CancelTicket(String PNR,String name,int userid){
        this.PNR = PNR;
        this.name = name;
        this.userid = userid;
    }
    
    
    public void setDate(Date date) {
        this.date = date;
    }
    public void setDest(String dest) {
        this.dest = dest;
    }
    public void setSrc(String src) {
        this.src = src;
    }
    public void setTrain(String train) {
        this.train = train;
    }
    public String getName() {
        return name;
    }
    public String getPNR() {
        return PNR;
    }
    public int getUserid() {
        return userid;
    }
    public String getDest() {
        return dest;
    }
    public String getSrc() {
        return src;
    }
    public String getTrain() {
        return train;
    }
    public Date getDate() {
        return date;
    }
    

}
