package Classes;

import java.sql.Date;
import java.io.*;

public class PassengerInfoAdminInfo implements Serializable {
    private String train;
    private java.sql.Date date;

    public PassengerInfoAdminInfo(String train, java.sql.Date date) {
        this.date = date;
        this.train = train;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public String getTrain() {
        return train;
    }
}
