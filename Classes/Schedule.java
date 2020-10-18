package Classes;

import java.io.*;

public class Schedule implements Serializable {
    private String source;
    private String dest;
    private String date;

    public Schedule(String source, String dest, String date) {
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
