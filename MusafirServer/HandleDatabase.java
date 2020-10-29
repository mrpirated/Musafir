package MusafirServer;

import java.sql.*;
import java.time.*;

public class HandleDatabase {

    public HandleDatabase() {
        Conn c1 = new Conn(),c2,c3;
        String query = "SELECT * FROM `month` ORDER BY `month`.`date` ASC", query2,query3,query4;
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
                start = start.plusDays(1);
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
                            c2 = new Conn();
                            c2.s.executeUpdate(query2);
                            System.out.println(start + " inserted");
                        }
                    }

                    start = start.plusDays(1);

                }
            }
            LocalDateTime ldt = LocalDateTime.now();
            int index;
            Timestamp now = Timestamp.valueOf(ldt),dept;
            c1 = new Conn();
            query2 = "SELECT * FROM month WHERE date = '" + today + "'";
            ResultSet rs1 = c1.s.executeQuery(query2),rs2;
            while(rs1.next()){
                index = rs1.getInt("index_no");
                c2 = new Conn();
                query3 = "SELECT * FROM src_dest_table where train_no ='" + rs1.getString("train_no") + "'AND station_no = 1";
                rs2 = c2.s.executeQuery(query3);
                dept = rs2.getTimestamp("departure");
                if(totaltime(now)>totaltime(dept)){
                    query4 = "DELETE FROM `month` WHERE index_no = '" + index + "'";
                    c3 = new Conn();
                    c3.s.executeUpdate(query4);

                }
            }

            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    long totaltime(Timestamp t){
        return t.getMinutes()+60*t.getHours();
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
