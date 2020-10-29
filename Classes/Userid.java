package Classes;

import java.io.Serializable;

public class Userid implements Serializable{
    private int userid;
    private String username;  
    public Userid(int userid,String username){
        this.userid = userid;
        this.username = username;
    }
    public int getUserid() {
        return userid;
    }
    public String getUsername() {
        return username;
    }
}
