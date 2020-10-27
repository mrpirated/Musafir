package Classes;

import java.io.*;

public class TrainBasicInfoAdminInfo implements Serializable {
    private String train_name, src, dest, runningDays;
    private Integer train_no, ts_slr, ts_ac;

    public TrainBasicInfoAdminInfo(Integer train_no, String train_name, Integer ts_slr, Integer ts_ac, String src,
            String dest, String runningDays) {
        this.dest = dest;
        this.runningDays = runningDays;
        this.src = src;
        this.train_name = train_name;
        this.train_no = train_no;
        this.ts_ac = ts_ac;
        this.ts_slr = ts_slr;
    }

    public String getDest() {
        return dest;
    }

    public String getRunningDays() {
        return runningDays;
    }

    public String getSrc() {
        return src;
    }

    public String getTrain_name() {
        return train_name;
    }

    public Integer getTrain_no() {
        return train_no;
    }

    public Integer getTs_ac() {
        return ts_ac;
    }

    public Integer getTs_slr() {
        return ts_slr;
    }
}
