package Classes;

import java.io.*;

public class UserInfo implements Serializable{
    private String name,email,phone,gender,dd,mm,yy;
    private char[] password;
    public UserInfo(String name,String email,String phone,String gender,char[] password,String dd,String mm,String yy){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.password=password;
        this.dd=dd;
        this.mm=mm;
        this.yy=yy;
    }
    public String getDd() {
        return dd;
    }
    public String getEmail() {
        return email;
    }
    public String getGender() {
        return gender;
    }
    public String getMm() {
        return mm;
    }
    public String getName() {
        return name;
    }
    public char[] getPassword() {
        return password;
    }
    public String getPhone() {
        return phone;
    }
    public String getYy() {
        return yy;
    }
}
