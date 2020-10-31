package MusafirServer;

import java.sql.*;
import java.time.*;

import Classes.BookingHistory;
import Classes.PassengerHistory;

public class HandleDatabase {

    public void Serverside() {
        Conn c1 = new Conn(), c2, c3, c4, c5,c6;
        LocalDateTime ldt = LocalDateTime.now();
        int index;
        Timestamp now = Timestamp.valueOf(ldt), dept;
        String PNR,trainno;
        String query = "SELECT * FROM `month` ORDER BY `month`.`date` ASC", query2, query3, query4, query5, query6,query7;
        try {
            long d = System.currentTimeMillis();
            Date td = new Date(d);
            LocalDate today = td.toLocalDate();
            ResultSet rs = c1.s.executeQuery(query), rs1, rs2, rs3, rs4, rs5,rs6;
            
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
                BookingHistory bookingHistory;
                query2 = "SELECT * FROM month WHERE date = '" + temp + "'";
                c1 = new Conn();
                rs1 = c1.s.executeQuery(query2);
                while (rs1.next()) {
                    
                    index = rs1.getInt("index_no");
                    trainno = rs1.getString("train");
                    c6 = new Conn();
                    query7 = "SELECT * FROM trains_basic_details WHERE tra1n_no = '" + trainno + "'";
                    rs6 = c6.s.executeQuery(query7);
                    
                    while (true) {
                        query4 = "SELECT * FROM tickets WHERE index_no = '" + index + "'";
                        c3 = new Conn();
                        rs3 = c3.s.executeQuery(query4);
                        if (rs3.next()) {
                            PNR = rs3.getString("PNR");
                            query5 = "SELECT * FROM passenger WHERE PNR = '" + PNR + "'";
                            c4 = new Conn();
                            rs4 = c4.s.executeQuery(query5);
                            rs4.next();
                            bookingHistory = new BookingHistory();
                            bookingHistory.setTrain(trainno + " "+rs6.getString("train_name"));
                            PassengerHistory[] passengerHistory = new PassengerHistory[rs4.getInt("tickets")];
                            bookingHistory.setUserid(rs4.getInt("user_id"));
                            bookingHistory.setDate(rs4.getDate("date"));
                            query6 = "SELECT * FROM src_dest_table WHERE train_no ='" + rs1.getString("train") + "'";
                            c5 = new Conn();
                            rs5 = c5.s.executeQuery(query6);
                            while (rs5.next()) {
                                if (rs5.getInt("station_no") == rs3.getInt("src"))
                                    bookingHistory.setSrc(rs5.getString("station"));

                                if (rs5.getInt("station_no") == rs3.getInt("dest"))
                                    bookingHistory.setDest(rs5.getString("station"));
                            }
                            query6 = "SELECT * FROM tickets WHERE PNR = '" + PNR + "'";
                            c5 = new Conn();
                            rs5 = c5.s.executeQuery(query6);
                            int k = 0;
                            while (rs5.next()) {
                                if (rs5.getInt("type") == 1)
                                    passengerHistory[k] = new PassengerHistory(rs5.getString("name"),
                                            "S" + rs5.getInt("coach_no") + " " + rs5.getInt("seat_no"),
                                            rs5.getInt("age"), rs5.getString("gender").charAt(0));
                                if (rs5.getInt("type") == 2)
                                    passengerHistory[k] = new PassengerHistory(rs5.getString("name"),
                                            "B" + rs5.getInt("coach_no") + " " + rs5.getInt("seat_no"),
                                            rs5.getInt("age"), rs5.getString("gender").charAt(0));

                                k++;
                            }
                            bookingHistory.setPassengerHistory(passengerHistory);
                            query6 = "DELETE FROM tickets WHERE index_no = '" + index + "'";
                            c5 = new Conn();
                            c5.s.executeUpdate(query6);
                            query6 = "DELETE FROM passenger WHERE PNR = '" + PNR + "'";
                            c5 = new Conn();
                            c5.s.executeUpdate(query6);
                            for (int j = 0; j < bookingHistory.getPassengerHistory().length; j++) {
                                query6 = "INSERT INTO `booking_history` (`user_id`, `PNR`,`train`, `name`, `age`, `gender`, `source`, `destination`, `date`, `seat`) VALUES ("
                                        + bookingHistory.getUserid() + "', '" + bookingHistory.getPNR() + "','" + bookingHistory.getTrain() + "' ,'"
                                        + bookingHistory.getPassengerHistory()[i].getName() + "', '"
                                        + bookingHistory.getPassengerHistory()[i].getAge() + "', '"
                                        + bookingHistory.getPassengerHistory()[i].getGender() + "', '"
                                        + bookingHistory.getSrc() + "', '" + bookingHistory.getDest() + "', '"
                                        + bookingHistory.getDate() + "', '"
                                        + bookingHistory.getPassengerHistory()[i].getSeat() + "')";
                                c5 = new Conn();
                                c5.s.executeUpdate(query6);
                            }
                        } else
                            break;
                    }

                    query6 = "DELETE FROM `month` WHERE index_no = '" + index + "'";
                    c3 = new Conn();
                    c3.s.executeUpdate(query6);
                }

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
                            trainno = rs.getString("train_no");
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

            c1 = new Conn();
            query2 = "SELECT * FROM month WHERE date = '" + today + "'";
            rs1 = c1.s.executeQuery(query2);
            while (rs1.next()) {
                index = rs1.getInt("index_no");
                trainno = rs1.getString("train");
                c6 = new Conn();
                query7 = "SELECT * FROM trains_basic_details WHERE train_no = '" + trainno + "'";
                rs6 = c6.s.executeQuery(query7);
                
                c2 = new Conn();
                query3 = "SELECT * FROM src_dest_table where train_no = '" + rs1.getString("train")
                        + "'AND station_no = 1";
                rs2 = c2.s.executeQuery(query3);
                rs2.next();
                dept = rs2.getTimestamp("departure");
                if (totaltime(now) >= totaltime(dept)) {
                    BookingHistory bookingHistory;

                    while (true) {
                        query4 = "SELECT * FROM tickets WHERE index_no = '" + index + "'";
                        c3 = new Conn();
                        rs3 = c3.s.executeQuery(query4);
                        if (rs3.next()) {
                            PNR = rs3.getString("PNR");
                            query5 = "SELECT * FROM passenger WHERE PNR = '" + PNR + "'";
                            c4 = new Conn();
                            rs4 = c4.s.executeQuery(query5);
                            rs4.next();
                            bookingHistory = new BookingHistory();
                            bookingHistory.setTrain(trainno + " "+rs6.getString("train_name"));
                            PassengerHistory[] passengerHistory = new PassengerHistory[rs4.getInt("tickets")];
                            bookingHistory.setUserid(rs4.getInt("user_id"));
                            bookingHistory.setDate(rs4.getDate("date"));
                            query6 = "SELECT * FROM src_dest_table WHERE train_no ='" + rs1.getString("train") + "'";
                            c5 = new Conn();
                            rs5 = c5.s.executeQuery(query6);
                            while (rs5.next()) {
                                if (rs5.getInt("station_no") == rs3.getInt("src"))
                                    bookingHistory.setSrc(rs5.getString("station"));

                                if (rs5.getInt("station_no") == rs3.getInt("dest"))
                                    bookingHistory.setDest(rs5.getString("station"));
                            }
                            query6 = "SELECT * FROM tickets WHERE PNR = '" + PNR + "'";
                            c5 = new Conn();
                            rs5 = c5.s.executeQuery(query6);
                            int i = 0;
                            while (rs5.next()) {
                                if (rs5.getInt("type") == 1)
                                    passengerHistory[i] = new PassengerHistory(rs5.getString("name"),
                                            "S" + rs5.getInt("coach_no") + " " + rs5.getInt("seat_no"),
                                            rs5.getInt("age"), rs5.getString("gender").charAt(0));
                                if (rs5.getInt("type") == 2)
                                    passengerHistory[i] = new PassengerHistory(rs5.getString("name"),
                                            "B" + rs5.getInt("coach_no") + " " + rs5.getInt("seat_no"),
                                            rs5.getInt("age"), rs5.getString("gender").charAt(0));

                                i++;
                            }
                            bookingHistory.setPassengerHistory(passengerHistory);
                            query6 = "DELETE FROM tickets WHERE index_no = '" + index + "'";
                            c5 = new Conn();
                            c5.s.executeUpdate(query6);
                            query6 = "DELETE FROM passenger WHERE PNR = '" + PNR + "'";
                            c5 = new Conn();
                            c5.s.executeUpdate(query6);
                            for (int j = 0; j < bookingHistory.getPassengerHistory().length; j++) {
                                query6 = "INSERT INTO `booking_history` (`user_id`, `PNR`,`train`, `name`, `age`, `gender`, `source`, `destination`, `date`, `seat`) VALUES ("
                                        + bookingHistory.getUserid() + "', '" + bookingHistory.getPNR() + "','" + bookingHistory.getTrain() + "' ,'"
                                        + bookingHistory.getPassengerHistory()[i].getName() + "', '"
                                        + bookingHistory.getPassengerHistory()[i].getAge() + "', '"
                                        + bookingHistory.getPassengerHistory()[i].getGender() + "', '"
                                        + bookingHistory.getSrc() + "', '" + bookingHistory.getDest() + "', '"
                                        + bookingHistory.getDate() + "', '"
                                        + bookingHistory.getPassengerHistory()[i].getSeat() + "')";
                                c5 = new Conn();
                                c5.s.executeUpdate(query6);
                            }
                        } else
                            break;
                    }

                    query6 = "DELETE FROM `month` WHERE index_no = '" + index + "'";
                    c3 = new Conn();
                    c3.s.executeUpdate(query6);

                }
            }
            c1 = new Conn();

            rs1 = c1.s.executeQuery(query2);
            if (rs1.next() == false) {
                end = end.plusDays(1);
                weekday = DayOfWeek.from(end);
                val = weekday.getValue() - 1;
                rs = c1.s.executeQuery(query);
                while (rs.next()) {
                    runningdays = (String) rs.getString("runningDays");
                    if (runningdays.charAt(val) == '1') {
                        trainno = rs.getString("train_no");
                        int sl = rs.getInt("ts_slr");
                        int ac = rs.getInt("ts_ac");
                        query2 = "INSERT INTO `month` ( `date`, `train`, `reroute`, `Total_S`, `Total_AC`, `Avail_S`, `Avail_AC`) VALUES ('"
                                + end + "','" + trainno + "' , '0', '" + sl + "', '" + ac + "', '" + sl + "', '" + ac
                                + "')";
                        c2 = new Conn();
                        c2.s.executeUpdate(query2);
                        System.out.println(end + " inserted");
                    }
                }
            }

            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    long totaltime(Timestamp t) {
        return t.getMinutes() + 60 * t.getHours();
    }

    public void NewTrain(String train,LocalDate starttrain) {
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
            while (starttrain.compareTo(end) <= 0) {
                weekday = DayOfWeek.from(starttrain);
                val = weekday.getValue() - 1;
                rs = c1.s.executeQuery(query);

                runningdays = (String) rs.getString("runningDays");
                if (runningdays.charAt(val) == '1') {
                    String trainno = rs.getString("train_no");
                    int sl = rs.getInt("ts_slr");
                    int ac = rs.getInt("ts_ac");
                    query2 = "INSERT INTO `month` ( `date`, `train`, `reroute`, `Total_S`, `Total_AC`, `Avail_S`, `Avail_AC`) VALUES ('"
                            + starttrain + "','" + trainno + "' , '0', '" + sl + "', '" + ac + "', '" + sl + "', '" + ac
                            + "')";
                    Conn c2 = new Conn();
                    c2.s.executeUpdate(query2);
                    System.out.println(starttrain + " inserted");
                }

                starttrain = starttrain.plusDays(1);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
