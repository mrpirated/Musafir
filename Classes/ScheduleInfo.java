package Classes;

import java.io.*;

public class ScheduleInfo implements Serializable {
    private String source;
    private String dest;
    private String date;

    public ScheduleInfo(String source, String dest, String date) {
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

    public String getDate() {
        return date;
    }
}
