package Classes;

import java.io.*;

public class LoginInfo implements Serializable {

    private String username;
    private char[] password;
    private Boolean admin;

    public LoginInfo(String username, char[] password) {
        this.password = password;
        this.username = username;
        admin = false;
    }

    public LoginInfo(String username, char[] password, Boolean admin) {
        this.password = password;
        this.username = username;
        this.admin = admin;
    }

    public char[] getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getAdmin() {
        return admin;
    }

}
