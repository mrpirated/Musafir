package Classes;

import java.io.*;

public class CancelTicketInfo implements Serializable {
    private String username;
    private String password;
    private String pnr;

    public CancelTicketInfo(String username, String password, String pnr) {
        this.password = password;
        this.pnr = pnr;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getPnr() {
        return pnr;
    }

    public String getUsername() {
        return username;
    }
}
