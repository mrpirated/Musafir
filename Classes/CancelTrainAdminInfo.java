package Classes;

import java.util.Date;

public class CancelTrainAdminInfo {
    private String trainNo;
    private Date from;
    private Date to;

    public CancelTrainAdminInfo(String trainNo, Date from, Date to) {
        this.trainNo = trainNo;
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public String getTrainNo() {
        return trainNo;
    }
}
