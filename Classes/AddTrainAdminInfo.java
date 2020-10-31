package Classes;

import java.io.*;
import java.sql.Date;

public class AddTrainAdminInfo implements Serializable {
    private String trainNo;
    private String trainName;
    private String src;
    private String dest;
    private String runningDays;
    private Integer noOfHalts;
    private Integer ts_slr;
    private Integer ts_ac;
    private Date date;

    public AddTrainAdminInfo(String trainNo, String trainName, String src, String dest, String runningDays,
            Integer noOfHalts, Integer ts_slr, Integer ts_ac,Date date) {
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.src = src;
        this.dest = dest;
        this.runningDays = runningDays;
        this.noOfHalts = noOfHalts;
        this.ts_slr = ts_slr;
        this.ts_ac = ts_ac;
        this.date = date;
    }

    public String getDest() {
        return dest;
    }

    public Integer getNoOfHalts() {
        return noOfHalts;
    }

    public String getRunningDays() {
        return runningDays;
    }

    public String getSrc() {
        return src;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public Integer getTs_ac() {
        return ts_ac;
    }

    public Integer getTs_slr() {
        return ts_slr;
    }
    public Date getDate() {
        return date;
    }
}
