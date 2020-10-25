package Classes;

import java.io.*;
import java.sql.Date;
import java.util.*;

public class ScheduleEnq implements Serializable {
    private String source;
    private String dest;
    private Date date;

    public ScheduleEnq(String source, String dest, Date date) {
        this.source = source;
        this.dest = dest;
        this.date = date;
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
}
