package Classes;

import java.io.Serializable;

public class CancelTicket implements Serializable{
    private String PNR,name;
    private int userid;
    public CancelTicket(String PNR,String name,int userid){
        this.PNR = PNR;
        this.name = name;
        this.userid = userid;
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

}
