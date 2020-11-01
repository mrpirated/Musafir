package MusafirServer;

import java.sql.ResultSet;

import Classes.*;

public class BookTicket {
    private PassengersDetailForm passengersDetailForm;
    private BookedTicket bookedTicket;
    private int noofpassengers, index, total, available, x;
    private String PNR, query;
    private int[] seats, coach;
    private String[] name;
    private Conn c, c1, c2, c3, c4, c5, c6;
    private ResultSet rs1, rs2, rs3, rs5;

    public BookTicket(PassengersDetailForm passengersDetailForm) {
        this.passengersDetailForm = passengersDetailForm;
        noofpassengers = passengersDetailForm.getNoOfPassenger();
        bookedTicket = new BookedTicket(noofpassengers);

        boolean check = true;
        c = new Conn();
        query = "SELECT * FROM passenger";
        try {
            rs1 = c.s.executeQuery(query);
            while (check) {
                PNR = PNR();
                check = false;
                while (rs1.next()) {
                    if (rs1.getString("PNR").equals(PNR)) {
                        check = true;
                        break;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        bookedTicket.setPNR(PNR);
        seats = new int[noofpassengers];
        coach = new int[noofpassengers];
        name = new String[noofpassengers];
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
                    available = rs2.getInt("Avail_S");
                    bookedTicket.setType(1);

                } else if (passengersDetailForm.getType() == 2) {
                    total = rs2.getInt("Total_AC") * 2 / 3;
                    available = rs2.getInt("Avail_AC");
                    bookedTicket.setType(2);
                }
                rs2.close();
                if (available > 0) {
                    if (passengersDetailForm.getPassengerInfo()[x].getBerthPreference().equals("NONE")) {
                        if (passengersDetailForm.getPassengerInfo()[x].isSenior()) {
                            bookedTicket = SeniorBooking(bookedTicket);
                        } else {
                            bookedTicket = NormalBooking(bookedTicket);
                        }
                    } else {
                        if (passengersDetailForm.getPassengerInfo()[x].getBerthPreference().equals("LB"))
                            bookedTicket = PreferenceBooking(bookedTicket, 1);
                        if (passengersDetailForm.getPassengerInfo()[x].getBerthPreference().equals("MB"))
                            bookedTicket = PreferenceBooking(bookedTicket, 2);
                        if (passengersDetailForm.getPassengerInfo()[x].getBerthPreference().equals("UB"))
                            bookedTicket = PreferenceBooking(bookedTicket, 3);
                        if (passengersDetailForm.getPassengerInfo()[x].getBerthPreference().equals("SLB"))
                            bookedTicket = PreferenceBooking(bookedTicket, 7);
                        if (passengersDetailForm.getPassengerInfo()[x].getBerthPreference().equals("SUB"))
                            bookedTicket = PreferenceBooking(bookedTicket, 0);
                    }

                } else {
                    bookedTicket = Waiting(bookedTicket);
                }
                bookedTicket.setName(name);
                bookedTicket.setCoach(coach);
                bookedTicket.setSeats(seats);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private BookedTicket Waiting(BookedTicket bookedTicket) {
        String queryc = "";
        if (passengersDetailForm.getType() == 1) {
            queryc = "UPDATE month SET Avail_S = '" + (available - 1) + "' WHERE index_no = '" + index + "'";
        } else if (passengersDetailForm.getType() == 2) {
            queryc = "UPDATE month SET Avail_AC = '" + (available - 1) + "' WHERE index_no = '" + index + "'";
        }
        String query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                + passengersDetailForm.getPassengerInfo()[x].getName() + "', NULL, NULL,'" + (Math.abs(available) + 1)
                + "' , '" + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
        c4 = new Conn();
        try {
            c4.s.executeUpdate(query3);

            seats[x] = Math.abs(available) + 1;
            coach[x] = 0;
            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();
            bookedTicket.setName(name);
            bookedTicket.setCoach(coach);
            bookedTicket.setSeats(seats);
            bookedTicket.setGotseat(true);
            c5 = new Conn();
            c5.s.executeUpdate(queryc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookedTicket;
    }

    private BookedTicket PreferenceBooking(BookedTicket bookedTicket, int n) {
        int tempseat;
        String query2, query3, query4, queryc = "";

        try {
            if (passengersDetailForm.getType() == 1) {
                queryc = "UPDATE month SET Avail_S = '" + (available - 1) + "' WHERE index_no = '" + index + "'";
            } else if (passengersDetailForm.getType() == 2) {
                queryc = "UPDATE month SET Avail_AC = '" + (available - 1) + "' WHERE index_no = '" + index + "'";
            }
            if ((passengersDetailForm.getPassengerInfo()[x].getGender() == 'F')
                    && passengersDetailForm.getNoOfPassenger() == 1) {
                query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                        + passengersDetailForm.getType() + "'";
                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);
                boolean flag;
                while (rs3.next()) {
                    if (rs3.getInt("seat_no") % 8 == n || rs3.getInt("seat_no") % 8 == n + 3) {
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
                        if ((tempseat <= 72 && tempseat >= 145)) {
                            flag = false;
                        }
                        System.out.println("Came here " + flag);
                        if (flag) {
                            c4 = new Conn();
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '"
                                    + rs3.getInt("coach_no") + "', '" + rs3.getInt("seat_no") + "', NULL, '"
                                    + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4.s.executeUpdate(query3);

                            coach[x] = rs3.getInt("coach_no");
                            seats[x] = rs3.getInt("seat_no");
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);

                            return bookedTicket;
                        }
                    }
                }

                for (int i = 72; i < 144; i++) {
                    if ((i + 1) % 8 == n || (i + 1) % 8 == n + 3) {

                        query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                                + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1)
                                + " AND `seat_no` = " + (i % 72 + 1) + "";
                        c3 = new Conn();
                        rs3 = c3.s.executeQuery(query2);
                        if (rs3.next() == false) {
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1)
                                    + "', '" + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                    + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4 = new Conn();
                            c4.s.executeUpdate(query3);

                            coach[x] = i / 72 + 1;
                            seats[x] = i % 72 + 1;
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);
                            c6 = new Conn();
                            c6.s.executeUpdate(queryc);
                            return bookedTicket;
                        }
                    }

                }
                query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                        + passengersDetailForm.getType() + "'";

                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);

                while (rs3.next()) {
                    if (rs3.getInt("seat_no") % 8 == n || rs3.getInt("seat_no") % 8 == n + 3) {
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
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '"
                                    + rs3.getInt("coach_no") + "', '" + rs3.getInt("seat_no") + "', NULL, '"
                                    + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4.s.executeUpdate(query3);
                            coach[x] = rs3.getInt("coach_no");
                            seats[x] = rs3.getInt("seat_no");
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);

                            return bookedTicket;
                        }

                    }

                }
                for (int i = 0; i < total; i++) {
                    if (i == 72)
                        i = 144;
                    if ((i + 1) % 8 == n || (i + 1) % 8 == n + 3) {
                        query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                                + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1)
                                + " AND `seat_no` = " + (i % 72 + 1) + "";
                        c3 = new Conn();
                        rs3 = c3.s.executeQuery(query2);
                        if (rs3.next() == false) {
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1)
                                    + "', '" + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                    + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4 = new Conn();
                            c4.s.executeUpdate(query3);

                            coach[x] = i / 72 + 1;
                            seats[x] = i % 72 + 1;
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);
                            c6 = new Conn();
                            c6.s.executeUpdate(queryc);
                            return bookedTicket;
                        }
                    }

                }
            } else {
                query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                        + passengersDetailForm.getType() + "'";

                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);
                boolean flag;
                while (rs3.next()) {
                    if (rs3.getInt("seat_no") % 8 == n || rs3.getInt("seat_no") % 8 == n + 3) {
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
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '"
                                    + rs3.getInt("coach_no") + "', '" + rs3.getInt("seat_no") + "', NULL, '"
                                    + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4.s.executeUpdate(query3);
                            coach[x] = rs3.getInt("coach_no");
                            seats[x] = rs3.getInt("seat_no");
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);

                            return bookedTicket;
                        }
                    }

                }
                for (int i = 0; i < total; i++) {
                    if (i == 72)
                        i = 144;
                    if ((i + 1) % 8 == n || (i + 1) % 8 == n + 3) {

                        query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                                + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1)
                                + " AND `seat_no` = " + (i % 72 + 1) + "";
                        c3 = new Conn();
                        rs3 = c3.s.executeQuery(query2);
                        if (rs3.next() == false) {
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1)
                                    + "', '" + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                    + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4 = new Conn();
                            c4.s.executeUpdate(query3);

                            coach[x] = i / 72 + 1;
                            seats[x] = i % 72 + 1;
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);
                            c6 = new Conn();
                            c6.s.executeUpdate(queryc);
                            return bookedTicket;
                        }
                    }
                }

                query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                        + passengersDetailForm.getType() + "'";
                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);
                while (rs3.next()) {
                    if (rs3.getInt("seat_no") % 8 == n || rs3.getInt("seat_no") % 8 == n + 3) {
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
                        if ((tempseat <= 72 && tempseat >= 145)) {
                            flag = false;
                        }
                        System.out.println("Came here " + flag);
                        if (flag) {
                            c4 = new Conn();
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '"
                                    + rs3.getInt("coach_no") + "', '" + rs3.getInt("seat_no") + "', NULL, '"
                                    + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4.s.executeUpdate(query3);
                            coach[x] = rs3.getInt("coach_no");
                            seats[x] = rs3.getInt("seat_no");
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);

                            return bookedTicket;
                        }
                    }

                }

                for (int i = 72; i < 144; i++) {
                    if ((i + 1) % 8 == n || (i + 1) % 8 == n + 3) {
                        query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                                + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1)
                                + " AND `seat_no` = " + (i % 72 + 1) + "";
                        c3 = new Conn();
                        rs3 = c3.s.executeQuery(query2);
                        if (rs3.next() == false) {
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1)
                                    + "', '" + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                    + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4 = new Conn();
                            c4.s.executeUpdate(query3);

                            coach[x] = i / 72 + 1;
                            seats[x] = i % 72 + 1;
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);
                            c6 = new Conn();
                            c6.s.executeUpdate(queryc);
                            return bookedTicket;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bookedTicket.getGotseat() == false)
            bookedTicket = NormalBooking(bookedTicket);
        return bookedTicket;
    }

    private BookedTicket SeniorBooking(BookedTicket bookedTicket) {
        int tempseat;
        String query2, query3, query4, queryc = "";

        try {
            if (passengersDetailForm.getType() == 1) {
                queryc = "UPDATE month SET Avail_S = '" + (available - 1) + "' WHERE index_no = '" + index + "'";
            } else if (passengersDetailForm.getType() == 2) {
                queryc = "UPDATE month SET Avail_AC = '" + (available - 1) + "' WHERE index_no = '" + index + "'";
            }
            if ((passengersDetailForm.getPassengerInfo()[x].getGender() == 'F')
                    && passengersDetailForm.getNoOfPassenger() == 1) {
                query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                        + passengersDetailForm.getType() + "'";
                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);
                boolean flag;
                while (rs3.next()) {
                    if (rs3.getInt("seat_no") % 8 == 1 || rs3.getInt("seat_no") % 8 == 4
                            || rs3.getInt("seat_no") % 8 == 7) {
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
                        if ((tempseat <= 72 && tempseat >= 145)) {
                            flag = false;
                        }
                        System.out.println("Came here " + flag);
                        if (flag) {
                            c4 = new Conn();
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '"
                                    + rs3.getInt("coach_no") + "', '" + rs3.getInt("seat_no") + "', NULL, '"
                                    + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4.s.executeUpdate(query3);
                            coach[x] = rs3.getInt("coach_no");
                            seats[x] = rs3.getInt("seat_no");
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);

                            return bookedTicket;
                        }
                    }
                }

                for (int i = 72; i < 144; i++) {
                    if ((i + 1) % 8 == 0 || (i + 1) % 8 == 3 || (i + 1) % 8 == 6) {

                        query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                                + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1)
                                + " AND `seat_no` = " + (i % 72 + 1) + "";
                        c3 = new Conn();
                        rs3 = c3.s.executeQuery(query2);
                        if (rs3.next() == false) {
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1)
                                    + "', '" + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                    + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4 = new Conn();
                            c4.s.executeUpdate(query3);

                            coach[x] = i / 72 + 1;
                            seats[x] = i % 72 + 1;
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);
                            c6 = new Conn();
                            c6.s.executeUpdate(queryc);
                            return bookedTicket;
                        }
                    }

                }
                query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                        + passengersDetailForm.getType() + "'";

                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);

                while (rs3.next()) {
                    if (rs3.getInt("seat_no") % 8 == 1 || rs3.getInt("seat_no") % 8 == 4
                            || rs3.getInt("seat_no") % 8 == 7) {
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
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '"
                                    + rs3.getInt("coach_no") + "', '" + rs3.getInt("seat_no") + "', NULL, '"
                                    + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4.s.executeUpdate(query3);
                            coach[x] = rs3.getInt("coach_no");
                            seats[x] = rs3.getInt("seat_no");
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);

                            return bookedTicket;
                        }

                    }

                }
                for (int i = 0; i < total; i++) {
                    if (i == 72)
                        i = 144;
                    if ((i + 1) % 8 == 0 || (i + 1) % 8 == 3 || (i + 1) % 8 == 6) {
                        query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                                + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1)
                                + " AND `seat_no` = " + (i % 72 + 1) + "";
                        c3 = new Conn();
                        rs3 = c3.s.executeQuery(query2);
                        if (rs3.next() == false) {
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1)
                                    + "', '" + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                    + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4 = new Conn();
                            c4.s.executeUpdate(query3);

                            coach[x] = i / 72 + 1;
                            seats[x] = i % 72 + 1;
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);
                            c6 = new Conn();
                            c6.s.executeUpdate(queryc);
                            return bookedTicket;
                        }
                    }

                }
            } else {
                query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                        + passengersDetailForm.getType() + "'";

                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);
                boolean flag;
                while (rs3.next()) {
                    if (rs3.getInt("seat_no") % 8 == 1 || rs3.getInt("seat_no") % 8 == 4
                            || rs3.getInt("seat_no") % 8 == 7) {
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
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '"
                                    + rs3.getInt("coach_no") + "', '" + rs3.getInt("seat_no") + "', NULL, '"
                                    + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4.s.executeUpdate(query3);
                            coach[x] = rs3.getInt("coach_no");
                            seats[x] = rs3.getInt("seat_no");
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);

                            return bookedTicket;
                        }
                    }

                }
                for (int i = 0; i < total; i++) {
                    if (i == 72)
                        i = 144;
                    if ((i + 1) % 8 == 0 || (i + 1) % 8 == 3 || (i + 1) % 8 == 6) {

                        query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                                + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1)
                                + " AND `seat_no` = " + (i % 72 + 1) + "";
                        c3 = new Conn();
                        rs3 = c3.s.executeQuery(query2);
                        if (rs3.next() == false) {
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1)
                                    + "', '" + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                    + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4 = new Conn();
                            c4.s.executeUpdate(query3);

                            coach[x] = i / 72 + 1;
                            seats[x] = i % 72 + 1;
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);
                            c6 = new Conn();
                            c6.s.executeUpdate(queryc);
                            return bookedTicket;
                        }
                    }
                }

                query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                        + passengersDetailForm.getType() + "'";
                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);
                while (rs3.next()) {
                    if (rs3.getInt("seat_no") % 8 == 1 || rs3.getInt("seat_no") % 8 == 4
                            || rs3.getInt("seat_no") % 8 == 7) {
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
                        if ((tempseat <= 72 && tempseat >= 145)) {
                            flag = false;
                        }
                        System.out.println("Came here " + flag);
                        if (flag) {
                            c4 = new Conn();
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '"
                                    + rs3.getInt("coach_no") + "', '" + rs3.getInt("seat_no") + "', NULL, '"
                                    + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4.s.executeUpdate(query3);
                            coach[x] = rs3.getInt("coach_no");
                            seats[x] = rs3.getInt("seat_no");
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);

                            return bookedTicket;
                        }
                    }

                }

                for (int i = 72; i < 144; i++) {
                    if ((i + 1) % 8 == 0 || (i + 1) % 8 == 3 || (i + 1) % 8 == 6) {
                        query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                                + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1)
                                + " AND `seat_no` = " + (i % 72 + 1) + "";
                        c3 = new Conn();
                        rs3 = c3.s.executeQuery(query2);
                        if (rs3.next() == false) {
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1)
                                    + "', '" + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                    + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4 = new Conn();
                            c4.s.executeUpdate(query3);

                            coach[x] = i / 72 + 1;
                            seats[x] = i % 72 + 1;
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);
                            c6 = new Conn();
                            c6.s.executeUpdate(queryc);
                            return bookedTicket;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bookedTicket.getGotseat() == false)
            bookedTicket = NormalBooking(bookedTicket);
        return bookedTicket;
    }

    private BookedTicket NormalBooking(BookedTicket bookedTicket) {
        int tempseat;
        String query2, query3, query4, queryc = "";

        try {
            if (passengersDetailForm.getType() == 1) {
                queryc = "UPDATE month SET Avail_S = '" + (available - 1) + "' WHERE index_no = '" + index + "'";
            } else if (passengersDetailForm.getType() == 2) {
                queryc = "UPDATE month SET Avail_AC = '" + (available - 1) + "' WHERE index_no = '" + index + "'";
            }
            if ((passengersDetailForm.getPassengerInfo()[x].getGender() == 'F')
                    && passengersDetailForm.getNoOfPassenger() == 1) {
                query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                        + passengersDetailForm.getType() + "'";
                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);
                boolean flag;
                while (rs3.next()) {
                    if (rs3.getInt("coach_no") == 2) {
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
                        if ((tempseat <= 72 && tempseat >= 145)) {
                            flag = false;
                        }
                        System.out.println("Came here " + flag);
                        if (flag) {
                            c4 = new Conn();
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '"
                                    + rs3.getInt("coach_no") + "', '" + rs3.getInt("seat_no") + "', NULL, '"
                                    + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4.s.executeUpdate(query3);
                            coach[x] = rs3.getInt("coach_no");
                            seats[x] = rs3.getInt("seat_no");
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);

                            return bookedTicket;
                        }
                    }
                }

                for (int i = 72; i < 144; i++) {

                    query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                            + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1) + " AND `seat_no` = "
                            + (i % 72 + 1) + "";
                    c3 = new Conn();
                    rs3 = c3.s.executeQuery(query2);
                    if (rs3.next() == false) {
                        query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1) + "', '"
                                + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                + passengersDetailForm.getDest() + "', '"
                                + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                        c4 = new Conn();
                        c4.s.executeUpdate(query3);

                        coach[x] = i / 72 + 1;
                        seats[x] = i % 72 + 1;
                        name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                        bookedTicket.setGotseat(true);
                        c6 = new Conn();
                        c6.s.executeUpdate(queryc);
                        return bookedTicket;
                    }

                }
                query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                        + passengersDetailForm.getType() + "'";

                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);

                while (rs3.next()) {
                    if (rs3.getInt("coach_no") != 2) {
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
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '"
                                    + rs3.getInt("coach_no") + "', '" + rs3.getInt("seat_no") + "', NULL, '"
                                    + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4.s.executeUpdate(query3);
                            coach[x] = rs3.getInt("coach_no");
                            seats[x] = rs3.getInt("seat_no");
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);

                            return bookedTicket;
                        }
                    }
                }
                for (int i = 0; i < total; i++) {
                    if (i == 72)
                        i = 144;

                    query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                            + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1) + " AND `seat_no` = "
                            + (i % 72 + 1) + "";
                    c3 = new Conn();
                    rs3 = c3.s.executeQuery(query2);
                    if (rs3.next() == false) {
                        query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1) + "', '"
                                + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                + passengersDetailForm.getDest() + "', '"
                                + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                        c4 = new Conn();
                        c4.s.executeUpdate(query3);

                        coach[x] = i / 72 + 1;
                        seats[x] = i % 72 + 1;
                        name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                        bookedTicket.setGotseat(true);
                        c6 = new Conn();
                        c6.s.executeUpdate(queryc);
                        return bookedTicket;
                    }

                }
            } else {
                query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                        + passengersDetailForm.getType() + "'";

                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);
                boolean flag;
                while (rs3.next()) {
                    if (rs3.getInt("coach_no") != 2) {
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
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '"
                                    + rs3.getInt("coach_no") + "', '" + rs3.getInt("seat_no") + "', NULL, '"
                                    + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4.s.executeUpdate(query3);
                            coach[x] = rs3.getInt("coach_no");
                            seats[x] = rs3.getInt("seat_no");
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);

                            return bookedTicket;
                        }
                    }
                }
                for (int i = 0; i < total; i++) {
                    if (i == 72)
                        i = 144;

                    query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                            + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1) + " AND `seat_no` = "
                            + (i % 72 + 1) + "";
                    c3 = new Conn();
                    rs3 = c3.s.executeQuery(query2);
                    if (rs3.next() == false) {
                        query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1) + "', '"
                                + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                + passengersDetailForm.getDest() + "', '"
                                + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                        c4 = new Conn();
                        c4.s.executeUpdate(query3);

                        coach[x] = i / 72 + 1;
                        seats[x] = i % 72 + 1;
                        name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                        bookedTicket.setGotseat(true);
                        c6 = new Conn();
                        c6.s.executeUpdate(queryc);
                        return bookedTicket;
                    }

                }

                query2 = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                        + passengersDetailForm.getType() + "'";
                c3 = new Conn();
                rs3 = c3.s.executeQuery(query2);
                while (rs3.next()) {
                    if (rs3.getInt("coach_no") == 2) {
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
                        if ((tempseat <= 72 && tempseat >= 145)) {
                            flag = false;
                        }
                        System.out.println("Came here " + flag);
                        if (flag) {
                            c4 = new Conn();
                            query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                    + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                    + passengersDetailForm.getPassengerInfo()[x].getName() + "', '"
                                    + rs3.getInt("coach_no") + "', '" + rs3.getInt("seat_no") + "', NULL, '"
                                    + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                    + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                            c4.s.executeUpdate(query3);
                            coach[x] = rs3.getInt("coach_no");
                            seats[x] = rs3.getInt("seat_no");
                            name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                            bookedTicket.setGotseat(true);

                            return bookedTicket;
                        }
                    }
                }

                for (int i = 72; i < 144; i++) {

                    query2 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                            + passengersDetailForm.getType() + " AND `coach_no` = " + (i / 72 + 1) + " AND `seat_no` = "
                            + (i % 72 + 1) + "";
                    c3 = new Conn();
                    rs3 = c3.s.executeQuery(query2);
                    if (rs3.next() == false) {
                        query3 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`,`name`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`,`fare`) VALUES ('"
                                + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "','"
                                + passengersDetailForm.getPassengerInfo()[x].getName() + "', '" + (i / 72 + 1) + "', '"
                                + (i % 72 + 1) + "', NULL, '" + passengersDetailForm.getSrc() + "', '"
                                + passengersDetailForm.getDest() + "', '"
                                + passengersDetailForm.getPassengerInfo()[x].getAge() + "', '"
                                + passengersDetailForm.getPassengerInfo()[x].getGender() + "','"+passengersDetailForm.getPassengerInfo()[x].getFare()+"')";
                        c4 = new Conn();
                        c4.s.executeUpdate(query3);

                        coach[x] = i / 72 + 1;
                        seats[x] = i % 72 + 1;
                        name[x] = passengersDetailForm.getPassengerInfo()[x].getName();

                        bookedTicket.setGotseat(true);
                        c6 = new Conn();
                        c6.s.executeUpdate(queryc);
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
