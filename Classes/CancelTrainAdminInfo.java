package Classes;

import java.io.Serializable;
import java.sql.Date;

public class CancelTrainAdminInfo implements Serializable{
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
