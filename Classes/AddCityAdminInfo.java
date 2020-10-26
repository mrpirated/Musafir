package Classes;

import java.io.*;

public class AddCityAdminInfo implements Serializable {
    private String cityName, stationCode;

    public AddCityAdminInfo(String cityName, String stationCode) {
        this.cityName = cityName;
        this.stationCode = stationCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getStationCode() {
        return stationCode;
    }
}
