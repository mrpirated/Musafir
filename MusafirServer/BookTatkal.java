package MusafirServer;

import java.sql.ResultSet;

import Classes.*;

public class BookTatkal {
    private PassengersDetailForm passengersDetailForm;
    private BookedTicket bookedTicket;
    private int noofpassengers, index, total, available, x;
    private String PNR, query;
    private int[][] seatinfo;
    private Conn c, c1, c2, c3, c4, c5, c6;
    private ResultSet rs1, rs2, rs3, rs5;

    public BookTatkal(PassengersDetailForm passengersDetailForm) {
        this.passengersDetailForm = passengersDetailForm;
        noofpassengers = passengersDetailForm.getNoOfPassenger();
        bookedTicket = new BookedTicket(noofpassengers);
        PNR = PNR();
        bookedTicket.setPNR(PNR);
        seatinfo = new int[noofpassengers][3];
        c = new Conn();
        query = "INSERT INTO `passenger` (`PNR`,`train`, `user_id`, `date`, `tickets`) VALUES ('" + PNR + "','"
                + passengersDetailForm.getTrainNo() + " " + passengersDetailForm.getTrainName() + "', '"
                + passengersDetailForm.getUserid() + "', '"
                + (passengersDetailForm.getDate().plusDays(passengersDetailForm.getDay() - 1)) + "', '"
                + passengersDetailForm.getNoOfPassenger() + "')";
        try {
            c.s.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        query = "SELECT * FROM `month` WHERE `date` = '" + passengersDetailForm.getDate() + "' AND `train` = '"
                + passengersDetailForm.getTrainNo() + "' ORDER BY `date` ASC";
        try {
            c1 = new Conn();
            rs1 = c1.s.executeQuery(query);
            rs1.next();
            index = rs1.getInt("index_no");
            rs1.close();
            for (x = 0; x < noofpassengers; x++) {
                c2 = new Conn();
                rs2 = c2.s.executeQuery(query);
                rs2.next();
                if (passengersDetailForm.getType() == 1) {
                    total = rs2.getInt("Total_S") / 3;
                    available = rs2.getInt("Tatkal_S");

                } else if (passengersDetailForm.getType() == 2) {
                    total = rs2.getInt("Total_AC") / 3;
                    available = rs2.getInt("Tatkal_AC");
                }
                rs2.close();
                if (available > 0) {

                    bookedTicket = NormalBooking(bookedTicket);

                } else {
                    bookedTicket = Waiting(bookedTicket);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private BookedTicket Waiting(BookedTicket bookedTicket) {
        String queryc = "";
        if (passengersDetailForm.getType() == 1) {
            queryc = "UPDATE month SET Tatkal_S = '" + (available - 1) + "' WHERE index_no = '" + index + "'";
        } else if (passengersDetailForm.getType() == 2) {
            queryc = "UPDATE month SET Tatkal_AC = '" + (available - 1) + "' WHERE index_no = '" + index + "'";
        }
        String query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                + passengersDetailForm.getPassengerInfo()[x].getName() + "', NULL, NULL,'" + (available - 1) + "' , '"
                + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                + passengersDetailForm.getPassengerInfo()[x].getGender() + "')";
        c4 = new Conn();
        try {
            c4.s.executeUpdate(query3);
            bookedTicket.setGotseat(true);
            c5 = new Conn();
            c5.s.executeUpdate(queryc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookedTicket;
    }

    private BookedTicket NormalBooking(BookedTicket bookedTicket) {
        int tempseat;
        String query2, query3, query4, queryc = "";

        try {
            if (passengersDetailForm.getType() == 1) {
                queryc = "UPDATE month SET Tatkal_S = '" + (available - 1) + "' WHERE index_no = '" + index + "'";
            } else if (passengersDetailForm.getType() == 2) {
                queryc = "UPDATE month SET Tatkal_AC = '" + (available - 1) + "' WHERE index_no = '" + index + "'";
            }
            query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                    + passengersDetailForm.getType() + "'";

            c3 = new Conn();
            rs3 = c3.s.executeQuery(query2);
            boolean flag;
            while (rs3.next()) {
                if ((rs3.getInt("coach_no") - 1) * 72 + rs3.getInt("seat_no") > 2 * available) {
                    flag = true;
                    query4 = "SELECT * FROM `tickets` WHERE index_no = '" + index + "'AND type = '"
                            + passengersDetailForm.getType() + "' AND coach_no = '" + rs3.getInt("coach_no")
                            + "' AND seat_no = '" + rs3.getInt("seat_no") + "'";
                    c5 = new Conn();
                    rs5 = c5.s.executeQuery(query4);
                    while (rs5.next()) {
                        if (((rs5.getInt("dest") > passengersDetailForm.getSrc())
                                && (rs5.getInt("src") < passengersDetailForm.getSrc()))
                                || ((rs5.getInt("src") < passengersDetailForm.getDest())
                                        && ((rs5.getInt("dest") > passengersDetailForm.getDest())))
                                || (rs5.getInt("dest") == passengersDetailForm.getDest())
                                || (rs5.getInt("src") == passengersDetailForm.getSrc())) {
                            flag = false;
                        }
                    }
                    System.out.println("Came here " + flag);

                    tempseat = (rs3.getInt("coach_no") - 1) * 72 + rs3.getInt("seat_no");
                    if ((tempseat > 72 && tempseat < 145)) {
                        flag = false;
                    }
                    System.out.println("Came here " + flag);
                    if (flag) {
                        c4 = new Conn();
                        query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                                + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + rs3.getInt("coach_no")
                                + "', '" + rs3.getInt("seat_no") + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                + passengersDetailForm.getDest() + "', '"
                                + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                + passengersDetailForm.getPassengerInfo()[x].getGender() + "')";
                        c4.s.executeUpdate(query3);
                        seatinfo[0][0] = passengersDetailForm.getType();
                        seatinfo[0][1] = rs3.getInt("coach_no");
                        seatinfo[0][2] = rs3.getInt("seat_no");
                        bookedTicket.setGotseat(true);
                        bookedTicket.setSeats(seatinfo);

                        return bookedTicket;
                    }
                }
            }
            for (int i = total * 2 / 3; i < total * 3; i++) {

                query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                        + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1) + " AND `seat_no` = "
                        + (i % 72 + 1) + "";
                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);
                if (rs3.next() == false) {
                    query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                            + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                            + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1) + "', '"
                            + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                            + passengersDetailForm.getDest() + "', '"
                            + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                            + passengersDetailForm.getPassengerInfo()[x].getGender() + "')";
                    c4 = new Conn();
                    c4.s.executeUpdate(query3);
                    seatinfo[0][0] = passengersDetailForm.getType();
                    seatinfo[0][1] = i / 72 + 1;
                    seatinfo[0][2] = i % 72 + 1;
                    bookedTicket.setSeats(seatinfo);
                    bookedTicket.setGotseat(true);
                    c6 = new Conn();
                    c6.s.executeUpdate(queryc);
                    return bookedTicket;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookedTicket;
    }

    private String PNR() {
        String s = "";
        for (int i = 0; i < 10; i++) {
            s = s + (int) (Math.random() * 10);
        }
        return s;
    }

    public BookedTicket getBookedTicket() {
        return bookedTicket;
    }
}
