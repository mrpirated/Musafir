package Classes;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class ScheduleEnq implements Serializable {
    private String source;
    private String dest;
    private Date date;
    private boolean tatkal;

    public ScheduleEnq(String source, String dest, Date date) {
        this.source = source;
        this.dest = dest;
        this.date = date;
        
    }
    public ScheduleEnq(String source, String dest,boolean tatkal) {
        this.source = source;
        this.dest = dest;
        LocalDate today = LocalDate.now();
        date = Date.valueOf(today.plusDays(1));
        this.tatkal = tatkal;
    }
    

    public String getSource() {
        return source;
    }

    public String getDest() {
        return dest;
    }

    public Date getDate() {
        return date;
    }
    public boolean getTatkal(){
        return tatkal;
    }
}
