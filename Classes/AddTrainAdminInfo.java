package Classes;

public class AddTrainAdminInfo {
    private String trainNo;
    private String trainName;
    private String src;
    private String dest;
    private Integer runningDays;
    private Integer noOfHalts;
    private Integer ts_slr;
    private Integer ts_ac;

    public AddTrainAdminInfo(String trainNo, String trainName, String src, String dest, Integer runningDays,
            Integer noOfHalts, Integer ts_slr, Integer ts_ac) {
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.src = src;
        this.dest = dest;
        this.runningDays = runningDays;
        this.noOfHalts = noOfHalts;
        this.ts_slr = ts_slr;
        this.ts_ac = ts_ac;
    }

    public String getDest() {
        return dest;
    }

    public Integer getNoOfHalts() {
        return noOfHalts;
    }

    public Integer getRunningDays() {
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
}
