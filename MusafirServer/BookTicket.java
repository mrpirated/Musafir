package MusafirServer;

import java.sql.ResultSet;

import Classes.*;

public class BookTicket {
    private PassengersDetailForm passengersDetailForm;
    private BookedTicket bookedTicket;
    private int noofpassengers, index, total, available, x;
    private String PNR, query;
    private int[][] seatinfo;
    private Conn c, c1, c2, c3, c4, c5;
    private ResultSet rs1, rs2, rs3, rs4, rs5;

    public BookTicket(PassengersDetailForm passengersDetailForm) {
        this.passengersDetailForm = passengersDetailForm;
        noofpassengers = passengersDetailForm.getNoOfPassenger();
        bookedTicket = new BookedTicket(noofpassengers);
        PNR = PNR();
        bookedTicket.setPNR(PNR);
        seatinfo = new int[noofpassengers][3];
        c = new Conn();
        query = "INSERT INTO `passenger` (`PNR`, `user_id`, `date`, `tickets`) VALUES ('" + PNR + "', '"
                + passengersDetailForm.getUserid() + "', '" + passengersDetailForm.getDate() + "', '"
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
                    total = rs2.getInt("Total_S") * 2 / 3;
                    available = rs2.getInt("Avail_S") - total / 2;

                } else if (passengersDetailForm.getType() == 2) {
                    total = rs2.getInt("Total_AC") * 2 / 3;
                    available = rs2.getInt("Avail_AC") - total / 2;
                }
                rs2.close();
                if (available > 0) {
                    if (passengersDetailForm.getPassengerInfo()[0].getBerthPreference().equals("NONE")
                            && !(passengersDetailForm.getPassengerInfo()[x].isSenior())) {
                        bookedTicket = NormalBooking(bookedTicket);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private BookedTicket NormalBooking(BookedTicket bookedTicket) {
        int tempseat;
        String query2, query3;

        try {
            query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                    + passengersDetailForm.getType() + "'";
            c3 = new Conn();
            rs3 = c3.s.executeQuery(query2);
            while (rs3.next()) {
                if (rs3.getInt("dest") < passengersDetailForm.getSrc()
                        || rs3.getInt("src") > passengersDetailForm.getDest()) {
                    tempseat = (rs3.getInt("coach_no") - 1) * 72 + rs3.getInt("seat_no");
                    if ((tempseat < 73 || tempseat > 144)) {
                        c4 = new Conn();
                        query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                                + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "', '"
                                + rs2.getInt("coach_no") + "', '" + rs2.getInt("seat_no") + "', NULL, '"
                                + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                + passengersDetailForm.getPassengerInfo()[0].getAge() + "', '"
                                + passengersDetailForm.getPassengerInfo()[x].getGender() + "')";
                        c3.s.executeUpdate(query2);
                        seatinfo[0][0] = passengersDetailForm.getType();
                        seatinfo[0][1] = rs2.getInt("coach_no");
                        seatinfo[0][2] = rs2.getInt("seat_no");
                        bookedTicket.setGotseat(true);
                        bookedTicket.setSeats(seatinfo);

                        return bookedTicket;
                    }
                }
            }
            for (int i = 0; i < total; i++) {
                if (i == 72)
                    i = 144;

                query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                        + passengersDetailForm.getType() + " AND `coach_no` = " + i / 72 + 1 + " AND `seat_no` = "
                        + i % 72 + 1 + "";
                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);
                if (rs3.next() == false) {
                    query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                            + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "', '" + (i / 72 + 1)
                            + "', '" + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                            + passengersDetailForm.getDest() + "', '"
                            + passengersDetailForm.getPassengerInfo()[0].getAge() + "', '"
                            + passengersDetailForm.getPassengerInfo()[0].getGender() + "')";
                    c4 = new Conn();
                    c4.s.executeUpdate(query3);
                    seatinfo[0][0] = passengersDetailForm.getType();
                    seatinfo[0][1] = i / 72 + 1;
                    seatinfo[0][2] = i % 72 + 1;
                    bookedTicket.setSeats(seatinfo);
                    bookedTicket.setGotseat(true);
                    return bookedTicket;
                }

            }
            query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                    + passengersDetailForm.getType() + "'";
            c3 = new Conn();
            rs3 = c3.s.executeQuery(query2);
            while (rs3.next()) {
                if (rs2.getInt("dest") < passengersDetailForm.getSrc()
                        || rs2.getInt("src") > passengersDetailForm.getDest()) {
                    tempseat = (rs2.getInt("coach_no") - 1) * 72 + rs2.getInt("seat_no");
                    if ((tempseat > 72 && tempseat < 145)) {
                        c3 = new Conn();
                        query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                                + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "', '"
                                + rs2.getInt("coach_no") + "', '" + rs2.getInt("seat_no") + "', NULL, '"
                                + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                + passengersDetailForm.getPassengerInfo()[0].getAge() + "', '"
                                + passengersDetailForm.getPassengerInfo()[0].getGender() + "')";
                        c3.s.executeUpdate(query3);
                        seatinfo[0][0] = passengersDetailForm.getType();
                        seatinfo[0][1] = rs2.getInt("coach_no");
                        seatinfo[0][2] = rs2.getInt("seat_no");
                        bookedTicket.setSeats(seatinfo);
                        bookedTicket.setGotseat(true);
                        return bookedTicket;
                    }
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
