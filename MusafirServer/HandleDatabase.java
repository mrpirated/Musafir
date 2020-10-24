package MusafirServer;

import java.sql.*;
import java.time.*;


public class HandleDatabase {
    public static void main(String[] args) {
        Conn c1 = new Conn();

        String query = "SELECT * FROM `trains_basic_details`", query2;
        DayOfWeek weekday;
        int val;
        String runningdays;
        try {

            LocalDate date = LocalDate.now();
            LocalDate end = date.plusDays(30);
            System.out.println(date + " " + end);

            ResultSet rs;
            while (date.compareTo(end) <= 0) {
                weekday = DayOfWeek.from(date);
                val = weekday.getValue() - 1;
                rs = c1.s.executeQuery(query);
                while (rs.next()) {
                    runningdays = (String) rs.getString("runningDays");
                    if (runningdays.charAt(val) == '1') {
                        String trainno = rs.getString("train_no");
                        int sl = rs.getInt("ts_slr");
                        int ac = rs.getInt("ts_ac");
                        query2 = "INSERT INTO `month` ( `date`, `train`, `reroute`, `Total_S`, `Total_AC`, `Avail_S`, `Avail_AC`) VALUES ('"
                                + date + "','" + trainno + "' , '0', '" + sl + "', '" + ac + "', '" + sl + "', '" + ac
                                + "')";
                        Conn c2 = new Conn();
                        c2.s.executeUpdate(query2);
                        System.out.println(date + " inserted");
                    }
                }

                date = date.plusDays(1);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
