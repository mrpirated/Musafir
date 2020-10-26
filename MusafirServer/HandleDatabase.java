package MusafirServer;

import java.sql.*;
import java.time.*;

public class HandleDatabase {

    public HandleDatabase() {
        Conn c1 = new Conn();
        String query = "SELECT * FROM `month` ORDER BY `month`.`date` ASC", query2;
        try {
            long d = System.currentTimeMillis();
            Date td = new Date(d);
            LocalDate today = td.toLocalDate();
            ResultSet rs = c1.s.executeQuery(query);
            int count = 0;
            rs.next();
            Date dt = (Date) rs.getDate("date");
            LocalDate date = dt.toLocalDate();
            LocalDate temp = date;
            while (date.compareTo(today) < 0) {
                date = date.plusDays(1);
                count++;
            }

            for (int i = 0; i < count; i++) {
                query2 = "DELETE FROM `month` WHERE `date` = '" + temp + "' ";
                c1.s.executeUpdate(query2);
                temp = temp.plusDays(1);
            }
            LocalDate start = today.plusDays(30 - count);
            LocalDate end = today.plusDays(30);
            query = "SELECT * FROM `trains_basic_details`";
            DayOfWeek weekday;
            int val;
            String runningdays;
            if (start.compareTo(end) != 0) {
                start.plusDays(1);
                while (start.compareTo(end) <= 0) {
                    weekday = DayOfWeek.from(start);
                    val = weekday.getValue() - 1;
                    rs = c1.s.executeQuery(query);
                    while (rs.next()) {
                        runningdays = (String) rs.getString("runningDays");
                        if (runningdays.charAt(val) == '1') {
                            String trainno = rs.getString("train_no");
                            int sl = rs.getInt("ts_slr");
                            int ac = rs.getInt("ts_ac");
                            query2 = "INSERT INTO `month` ( `date`, `train`, `reroute`, `Total_S`, `Total_AC`, `Avail_S`, `Avail_AC`) VALUES ('"
                                    + start + "','" + trainno + "' , '0', '" + sl + "', '" + ac + "', '" + sl + "', '"
                                    + ac + "')";
                            Conn c2 = new Conn();
                            c2.s.executeUpdate(query2);
                            System.out.println(start + " inserted");
                        }
                    }

                    start = start.plusDays(1);

                }
            }

            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void NewTrain(int train) {
        Conn c1 = new Conn();

        String query = "SELECT * FROM `trains_basic_details` WHERE train = '" + train + "'", query2;
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

                date = date.plusDays(1);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
