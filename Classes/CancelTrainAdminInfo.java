package Classes;

public class CancelTrainAdminInfo {
    private String trainNo;
    private String from;
    private String to;

    public CancelTrainAdminInfo(String trainNo, String from, String to) {
        this.trainNo = trainNo;
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getTrainNo() {
        return trainNo;
    }
}
