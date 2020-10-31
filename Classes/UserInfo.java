package Classes;

import java.io.*;

public class UserInfo implements Serializable {
    private String name, email, phone, dd, mm, yy;
    private char[] password;
    private char gender;

    public UserInfo(String name, String email, String phone, char gender, char[] password, String dd, String mm,
            String yy) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.dd = dd;
        this.mm = mm;
        this.yy = yy;
        this.gender = gender;
    }

    public String getDd() {
        return dd;
    }

    public String getEmail() {
        return email;
    }

    public char getGender() {
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
