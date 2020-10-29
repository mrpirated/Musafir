package Classes;

import java.util.*;

import Musafir.date;

public class RerouteTrainAdminInfo {
    private String trainNo;
    private Vector<AddTrainAdminNextInfo> reroutedStations = new Vector<AddTrainAdminNextInfo>();
    private java.sql.Date dateFrom, dateTo;

    public RerouteTrainAdminInfo(String trainNo, java.sql.Date dateFrom, java.sql.Date dateTo,
            Vector<AddTrainAdminNextInfo> reroutedStations) {
        this.trainNo = trainNo;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.reroutedStations = reroutedStations;
    }

    public java.sql.Date getDateFrom() {
        return dateFrom;
    }

    public java.sql.Date getDateTo() {
        return dateTo;
    }

    public Vector<AddTrainAdminNextInfo> getReroutedStations() {
        return reroutedStations;
    }

    public String getTrainNo() {
        return trainNo;
    }
}
