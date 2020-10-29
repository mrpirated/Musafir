package MusafirServer;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import Classes.*;

public class HandleClient implements Runnable {
    private Conn c1 = new Conn();
    private Socket socket;

    public HandleClient(Socket socket) {
        this.socket = socket;
    }

    private Userid Login(LoginInfo loginInfo) {
        Userid userid = new Userid(0, "");
        try {
            String query = "SELECT * FROM `user_info` WHERE email='" + loginInfo.getUsername() + "' AND password='"
                    + String.valueOf(loginInfo.getPassword()) + "'";

            ResultSet rs = c1.s.executeQuery(query);
            if (rs.next()) {
                userid = new Userid(rs.getInt("user_id"),rs.getString("name"));
                return userid;
            }
            query = "SELECT * FROM `user_info` WHERE phone='" + loginInfo.getUsername() + "' AND password='"
                    + String.valueOf(loginInfo.getPassword()) + "'";
            rs = c1.s.executeQuery(query);
            if (rs.next()) {
                userid = new Userid(rs.getInt("user_id"),rs.getString("name"));
                return userid;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return userid;
    }

    private Boolean Signup(UserInfo userInfo) {

        String d = userInfo.getMm() + "-" + userInfo.getDd() + "-" + userInfo.getYy();
        String query = "INSERT INTO `user_info` ( `name`, `dob`, `gender`, `email`, `phone`, `password`) VALUES ( '"
                + userInfo.getName() + "', STR_TO_DATE('" + d + "','%m-%d-%Y'), '" + userInfo.getGender() + "', '"
                + userInfo.getEmail() + "', '" + userInfo.getPhone() + "', '" + String.valueOf(userInfo.getPassword())
                + "')";
        try {
            c1.s.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return true;
    }

    private String[][] GetCities() {
        String[][] cities = new String[2][];
        String query = "SELECT COUNT(*) FROM Cities";

        try {
            ResultSet rs = c1.s.executeQuery(query);
            rs.next();
            int n = rs.getInt(1);
            cities[0] = new String[n];
            cities[1] = new String[n];
            query = "SELECT * FROM Cities";
            rs = c1.s.executeQuery(query);
            for (int i = 0; i < n; i++) {
                rs.next();
                cities[0][i] = (String) rs.getString("stations");
                cities[1][i] = (String) rs.getString("short");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }

    private String AdminLogin(LoginInfo loginInfo) {
        try {
            String query = "SELECT * FROM `admin_info` WHERE email='" + loginInfo.getUsername() + "' AND password='"
                    + String.valueOf(loginInfo.getPassword()) + "'";

            ResultSet rs = c1.s.executeQuery(query);
            if (rs.next()) {
                return rs.getString("name");
            }
            query = "SELECT * FROM `admin_info` WHERE phone='" + loginInfo.getUsername() + "' AND password='"
                    + String.valueOf(loginInfo.getPassword()) + "'";
            rs = c1.s.executeQuery(query);
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

    private Vector<AvailabilityInfo> ScheduleEnq(ScheduleEnq scheduleEnq) {
        Vector<AvailabilityInfo> availabilityInfo = new Vector<AvailabilityInfo>();
        AvailabilityInfo temp;
        String train;
        String query = "SELECT * FROM `month` WHERE `date` = '" + scheduleEnq.getDate() + "'", query2, query3;
        String st1, st2;
        String source = scheduleEnq.getSource();
        String dest = scheduleEnq.getDest();
        String trainName;
        Timestamp dep;
        int day1, fare1, station_no,availsl,availac;
        System.out.println("in the function");
        try {
            ResultSet rs1 = c1.s.executeQuery(query), rs2, rs3, rs4;
            while (rs1.next()) {
                train = (String) rs1.getString("train");
                query2 = "SELECT * FROM `src_dest_table` WHERE `train_no` = " + train + " ORDER BY `station_no` ASC";
                query3 = "SELECT * FROM `trains_basic_details` WHERE `train_no` = " + train + "";

                Conn c3 = new Conn();
                rs4 = c3.s.executeQuery(query3);
                rs4.next();
                trainName = rs4.getString("train_name");
                Conn c2 = new Conn();
                rs2 = c2.s.executeQuery(query2);

                while (rs2.next()) {

                    st1 = (String) rs2.getString("station");
                    if (source.equals(st1)) {
                        rs3 = rs2;
                        dep = rs2.getTimestamp("departure");
                        day1 = rs2.getInt("day");
                        fare1 = rs2.getInt("fare");
                        station_no = rs2.getInt("station_no");
                        System.out.println("equals");
                        while (rs3.next()) {
                            System.out.println("rs3");
                            st2 = (String) rs3.getString("station");
                            if (dest.equals(st2)) {
                                availsl = rs1.getInt("Avail_S") - rs1.getInt("Total_S")/3;
                                availac = rs1.getInt("Avail_S") - rs1.getInt("Total_S")/3;
                                temp = new AvailabilityInfo(true, train, trainName, availsl,
                                        availac, rs3.getTimestamp("arrival"), dep,
                                        (Date) scheduleEnq.getDate(), day1, rs3.getInt("day"),
                                        rs3.getInt("fare") - fare1, station_no, rs3.getInt("station_no"));

                                availabilityInfo.add(temp);
                                System.out.println("added");
                                break;
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availabilityInfo;
    }

    private String AddTrain(AddTrainAdminInfo trainInfo) {

        String query = "INSERT INTO `trains_basic_details`(`train_no`, `train_name`, `ts_slr`, `ts_ac`, `src`, `dest`, `runningDays`) VALUES ( '"
                + trainInfo.getTrainNo() + "', '" + trainInfo.getTrainName() + "', " + trainInfo.getTs_slr() + ", "
                + trainInfo.getTs_ac() + ", '" + trainInfo.getSrc() + "', '" + trainInfo.getDest() + "', '"
                + trainInfo.getRunningDays() + "' )";

        try {
            c1.s.executeUpdate(query);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return " ";
        }

    }

    private String AddStationInfo(String trainNo7, Integer noOfHalt7, AddTrainAdminNextInfo[] stationInfo) {
        for (int i = 0; i < noOfHalt7; i++) {

            String query = "INSERT INTO `src_dest_table`(`train_no`, `station_no`, `station`, `arrival`, `departure`, `distance`, `day`, `platform`, `fare`) VALUES ("
                    + trainNo7 + ", " + (i + 1) + ", '" + stationInfo[i].getStation() + "', '"
                    + stationInfo[i].getArrival() + "', '" + stationInfo[i].getDeparture() + "', "
                    + stationInfo[i].getDistance() + ", " + stationInfo[i].getDay() + ", "
                    + stationInfo[i].getDistance() + ", " + stationInfo[i].getFare() + ")";
            try {
                c1.s.executeUpdate(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "ok";
    }

    private String AddCity(AddCityAdminInfo[] addCity) {
        int n = addCity.length;

        for (int i = 0; i < n; i++) {

            String query = "INSERT INTO `cities`(`stations`, `short`) VALUES ( '" + addCity[i].getCityName() + "', '"
                    + addCity[i].getStationCode() + "' )";
            try {
                c1.s.executeUpdate(query);
            } catch (Exception e) {
                e.printStackTrace();
                return " ";
            }

        }
        return "ok";
    }

    private TrainBasicInfoAdminInfo TrainBasicInfo(String trainNo) {
        TrainBasicInfoAdminInfo trainBasicInfo;
        try {
            String train_name, src, dest, runningDays;
            Integer train_no, ts_slr, ts_ac;
            String query = "SELECT * FROM `trains_basic_details` WHERE train_no='" + trainNo + "'";
            ResultSet rs = c1.s.executeQuery(query);
            if (rs.next()) {
                train_no = Integer.parseInt(rs.getString("train_no"));
                train_name = rs.getString("train_name");
                ts_slr = rs.getInt("ts_slr");
                ts_ac = rs.getInt("ts_ac");
                src = rs.getString("src");
                dest = rs.getString("dest");
                runningDays = rs.getString("runningDays");
                trainBasicInfo = new TrainBasicInfoAdminInfo(train_no, train_name, ts_slr, ts_ac, src, dest,
                        runningDays);
            } else {
                trainBasicInfo = new TrainBasicInfoAdminInfo(0, "train_name", 0, 0, "src", "dest", "runningDays");
            }
        } catch (Exception e) {
            trainBasicInfo = new TrainBasicInfoAdminInfo(0, "train_name", 0, 0, "src", "dest", "runningDays");
            e.printStackTrace();
        }
        return trainBasicInfo;
    }

    private Vector<AddTrainAdminNextInfo> TrainStationsInfo(String trainNo8) {
        Vector<AddTrainAdminNextInfo> stationsInfo = new Vector<AddTrainAdminNextInfo>();
        AddTrainAdminNextInfo temp;
        String query = "SELECT * FROM `src_dest_table` WHERE `train_no` = '" + trainNo8 + "'";
        String station;
        java.sql.Time arrival, departure;
        Integer distance, day, platform, fare, number;
        try {
            ResultSet rs1 = c1.s.executeQuery(query);
            while (rs1.next()) {
                number = rs1.getInt("station_no");
                station = rs1.getString("station");
                arrival = rs1.getTime("arrival");
                departure = rs1.getTime("departure");
                distance = rs1.getInt("distance");
                day = rs1.getInt("day");
                platform = rs1.getInt("platform");
                fare = rs1.getInt("fare");

                temp = new AddTrainAdminNextInfo(number, station, arrival, departure, distance, day, platform, fare);

                stationsInfo.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stationsInfo;

    }

    private Vector<TrainBasicInfoAdminInfo> AllTrainInfo() {
        Vector<TrainBasicInfoAdminInfo> stationsInfo = new Vector<TrainBasicInfoAdminInfo>();
        TrainBasicInfoAdminInfo temp;
        String query = "SELECT * FROM `trains_basic_details`";
        String train_name, src, dest, runningDays;
        Integer train_no, ts_slr, ts_ac;
        try {
            ResultSet rs = c1.s.executeQuery(query);
            while (rs.next()) {
                train_no = Integer.parseInt(rs.getString("train_no"));
                train_name = rs.getString("train_name");
                ts_slr = rs.getInt("ts_slr");
                ts_ac = rs.getInt("ts_ac");
                src = rs.getString("src");
                dest = rs.getString("dest");
                runningDays = rs.getString("runningDays");
                temp = new TrainBasicInfoAdminInfo(train_no, train_name, ts_slr, ts_ac, src, dest, runningDays);
                stationsInfo.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stationsInfo;

    }

    
    
    /*private BookedTicket Book(PassengersDetailForm passengersDetailForm) {

        int noofpassengers = passengersDetailForm.getNoOfPassenger();
        BookedTicket bookedTicket = new BookedTicket(noofpassengers);
        bookedTicket.setPNR(PNR);
        int[][] seatinfo = new int[noofpassengers][3];
        Boolean booked ;
        int index;
        Conn c1 = new Conn();
        String query = "SELECT * FROM `month` WHERE `date` = '" + passengersDetailForm.getDate() + "' AND `train` = '"
                + passengersDetailForm.getTrainNo() + "' ORDER BY `date` ASC";
        try {
            ResultSet rs = c1.s.executeQuery(query);
            rs.next();
            index = rs.getInt("index_no");

            int avail = 0, seats = 0 , tempseat,tempcoach;
            if (noofpassengers == 1) {
                
                if (passengersDetailForm.getType() == 1) {
                    seats = rs.getInt("Total_S")*2/3;
                    avail = rs.getInt("Avail_S") - seats/2;
                    
                } else if (passengersDetailForm.getType() == 2) {
                    seats = rs.getInt("Total_AC")*2/3;
                    avail = rs.getInt("Avail_AC") - seats/2;
                    
                }
                Conn c2, c3,c4;
                ResultSet rs2,rs3;
                String query2,query1;
                if (avail > 0) {
                    
                    if (passengersDetailForm.getPassengerInfo()[0].getBerthPreference().equals("NONE")
                            && !(passengersDetailForm.getPassengerInfo()[0].isSenior())) {

                        query = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                                + passengersDetailForm.getType() + "'";
                        c2 = new Conn();
                        rs2 = c2.s.executeQuery(query);
                        
                        
                        rs2 = c2.s.executeQuery(query);
                        while (rs2.next() && booked == false) {
                            if (rs2.getInt("dest") < passengersDetailForm.getSrc()
                                    || rs2.getInt("src") > passengersDetailForm.getDest()) {
                                tempseat = (rs2.getInt("coach_no") - 1) * 72 + rs2.getInt("seat_no");
                                if ((tempseat >72 && tempseat < 145)) {
                                    c3 = new Conn();
                                    query2 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                                            + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "', '"
                                            + rs2.getInt("coach_no") + "', '" + rs2.getInt("seat_no") + "', NULL, '"
                                            + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest()
                                            + "', '" + passengersDetailForm.getPassengerInfo()[0].getAge() + "', '"
                                            + passengersDetailForm.getPassengerInfo()[0].getGender() + "')";
                                    c3.s.executeUpdate(query2);
                                    seatinfo[0][0] = passengersDetailForm.getType();
                                    seatinfo[0][1] = rs2.getInt("coach_no");
                                    seatinfo[0][2] = rs2.getInt("seat_no");
                                    bookedTicket.setSeats(seatinfo);
                                    booked = true;
                                    return bookedTicket;
                                }
                            }

                        }

                        if(booked == false){
                            for(int i = 72;i<144;i++){
                                query1 ="SEARCH * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                                + passengersDetailForm.getType() + " AND coach_no = '" + i/72 + "' AND seat_no = '" + i%72 + "'";
                                c3 = new Conn();
                                rs3 = c3.s.executeQuery(query1);
                                if(rs3.next()==false){
                                    query2 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                                        + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "', '"
                                        + i/72 + "', '" + i%72+1 + "', NULL, '"
                                        + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest()
                                        + "', '" + passengersDetailForm.getPassengerInfo()[0].getAge() + "', '"
                                        + passengersDetailForm.getPassengerInfo()[0].getGender() + "')";
                                    c4 = new Conn();
                                    c4.s.executeUpdate(query2);
                                    seatinfo[0][0] = passengersDetailForm.getType();
                                    seatinfo[0][1] = i/72;
                                    seatinfo[0][2] = i%72+1;
                                    bookedTicket.setSeats(seatinfo);
                                    booked = true;
                                    return bookedTicket;
                                }
                                
                            }
                        }


                    }
                    else if (passengersDetailForm.getPassengerInfo()[0].getBerthPreference().equals("NONE")
                    && (passengersDetailForm.getPassengerInfo()[0].isSenior())) {
                        query = "SELECT * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                                + passengersDetailForm.getType() + "'";
                        c2 = new Conn();
                        rs2 = c2.s.executeQuery(query);
                        while (rs2.next() && booked == false) {
                            if (rs2.getInt("dest") < passengersDetailForm.getSrc()
                                    || rs2.getInt("src") > passengersDetailForm.getDest()) {
                                tempseat = (rs2.getInt("coach_no") - 1) * 72 + rs2.getInt("seat_no");
                                if ((tempseat < 73 || tempseat > 144)&&(tempseat%8 == 0||tempseat%8 == 3||tempseat%8 == 6)) {
                                    c3 = new Conn();
                                    query2 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                                            + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "', '"
                                            + rs2.getInt("coach_no") + "', '" + rs2.getInt("seat_no") + "', NULL, '"
                                            + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest()
                                            + "', '" + passengersDetailForm.getPassengerInfo()[0].getAge() + "', '"
                                            + passengersDetailForm.getPassengerInfo()[0].getGender() + "')";
                                    c3.s.executeUpdate(query2);
                                    booked = true;
                                    return bookedTicket;
                                }
                            }

                        }
                        if (booked == false) {
                            for (int i = 0; i < avail; i++) {
                                if (i == 72)
                                    i = 144;
                                if (i % 8 == 0 || i % 8 == 6 || i % 8 == 3) {
                                    query1 = "SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = "
                                            + passengersDetailForm.getType() + " AND `coach_no` = " + i / 72
                                            + " AND `seat_no` = " + i % 72 + "";
                                    c3 = new Conn();
                                    rs3 = c3.s.executeQuery(query1);
                                    if (rs3.next() == false) {
                                        query2 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                                                + index + "', '" + PNR + "', '" + passengersDetailForm.getType()
                                                + "', '" + i / 72 + "', '" + i % 72 + 1 + "', NULL, '"
                                                + passengersDetailForm.getSrc() + "', '"
                                                + passengersDetailForm.getDest() + "', '"
                                                + passengersDetailForm.getPassengerInfo()[0].getAge() + "', '"
                                                + passengersDetailForm.getPassengerInfo()[0].getGender() + "')";
                                        c4 = new Conn();
                                        c4.s.executeUpdate(query2);
                                        seatinfo[0][0] = passengersDetailForm.getType();
                                        seatinfo[0][1] = i / 72;
                                        seatinfo[0][2] = i % 72 + 1;
                                        bookedTicket.setSeats(seatinfo);
                                        booked = true;
                                        return bookedTicket;
                                    }
                                }

                            }

                        }
                        if(booked == false){
                            for(int i = 0;i<avail;i++){
                                if(i==72)
                                i=144;

                                query1 ="SELECT * FROM `tickets` WHERE `index_no` = " + index + " AND `type` = " + passengersDetailForm.getType() + " AND `coach_no` = " + i/72 + " AND `seat_no` = " + i%72 + "";
                                c3 = new Conn();
                                rs3 = c3.s.executeQuery(query1);
                                if(rs3.next()==false){
                                    query2 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                                        + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "', '"
                                        + i/72 + "', '" + i%72+1 + "', NULL, '"
                                        + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest()
                                        + "', '" + passengersDetailForm.getPassengerInfo()[0].getAge() + "', '"
                                        + passengersDetailForm.getPassengerInfo()[0].getGender() + "')";
                                    c4 = new Conn();
                                    c4.s.executeUpdate(query2);
                                    seatinfo[0][0] = passengersDetailForm.getType();
                                    seatinfo[0][1] = i/72;
                                    seatinfo[0][2] = i%72+1;
                                    bookedTicket.setSeats(seatinfo);
                                    booked = true;
                                    return bookedTicket;
                                }
                                
                            }
                        }
                        rs2 = c2.s.executeQuery(query);
                        while (rs2.next() && booked == false) {
                            if (rs2.getInt("dest") < passengersDetailForm.getSrc()
                                    || rs2.getInt("src") > passengersDetailForm.getDest()) {
                                tempseat = (rs2.getInt("coach_no") - 1) * 72 + rs2.getInt("seat_no");
                                if ((tempseat >72 && tempseat < 145)) {
                                    c3 = new Conn();
                                    query2 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                                            + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "', '"
                                            + rs2.getInt("coach_no") + "', '" + rs2.getInt("seat_no") + "', NULL, '"
                                            + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest()
                                            + "', '" + passengersDetailForm.getPassengerInfo()[0].getAge() + "', '"
                                            + passengersDetailForm.getPassengerInfo()[0].getGender() + "')";
                                    c3.s.executeUpdate(query2);
                                    seatinfo[0][0] = passengersDetailForm.getType();
                                    seatinfo[0][1] = rs2.getInt("coach_no");
                                    seatinfo[0][2] = rs2.getInt("seat_no");
                                    bookedTicket.setSeats(seatinfo);
                                    booked = true;
                                    return bookedTicket;
                                }
                            }

                        }

                        if(booked == false){
                            for(int i = 72;i<144;i++){
                                query1 ="SEARCH * FROM tickets WHERE index_no = '" + index + "' AND type = '"
                                + passengersDetailForm.getType() + " AND coach_no = '" + i/72 + "' AND seat_no = '" + i%72 + "'";
                                c3 = new Conn();
                                rs3 = c3.s.executeQuery(query1);
                                if(rs3.next()==false){
                                    query2 = "INSERT INTO `tickets` (`index_no`, `PNR`, `type`, `coach_no`, `seat_no`, `waiting`, `src`, `dest`, `age`, `gender`) VALUES ('"
                                        + index + "', '" + PNR + "', '" + passengersDetailForm.getType() + "', '"
                                        + i/72 + "', '" + i%72+1 + "', NULL, '"
                                        + passengersDetailForm.getSrc() + "', '" + passengersDetailForm.getDest()
                                        + "', '" + passengersDetailForm.getPassengerInfo()[0].getAge() + "', '"
                                        + passengersDetailForm.getPassengerInfo()[0].getGender() + "')";
                                    c4 = new Conn();
                                    c4.s.executeUpdate(query2);
                                    seatinfo[0][0] = passengersDetailForm.getType();
                                    seatinfo[0][1] = i/72;
                                    seatinfo[0][2] = i%72+1;
                                    bookedTicket.setSeats(seatinfo);
                                    booked = true;
                                    return bookedTicket;

                                }
                                
                            }
                        }
                        


                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookedTicket;

    }*/

    private Vector<String> GetTrain() {
        Vector<String> trainInfo = new Vector<String>();
        String query = "SELECT train_no, train_name FROM `trains_basic_details`";
        String temp;
        int i = 0;
        trainInfo.add("None");
        try {
            ResultSet rs = c1.s.executeQuery(query);
            while (rs.next()) {
                temp = rs.getString("train_no") + " - " + rs.getString("train_name");
                trainInfo.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainInfo;
    }

    @Override
    public void run() {
        while (true) {
            try {

                ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());

                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                int choice = (int) oi.readInt();
                switch (choice) {
                    case 1:
                        LoginInfo loginInfo = (LoginInfo) oi.readObject();
                        Userid userid = Login(loginInfo);
                        os.writeObject(userid);
                        os.flush();
                        break;

                    case 2:
                        UserInfo userInfo = (UserInfo) oi.readObject();
                        Boolean b = Signup(userInfo);
                        os.writeBoolean(b);
                        os.flush();
                        break;

                    case 3:
                        String[][] cities = GetCities();
                        os.writeObject(cities);
                        os.flush();
                        break;

                    case 4:
                        LoginInfo adminLogin = (LoginInfo) oi.readObject();
                        String s1 = AdminLogin(adminLogin);
                        os.writeUTF(s1);
                        os.flush();
                        break;
                    case 5:
                        ScheduleEnq scheduleEnq = (ScheduleEnq) oi.readObject();
                        Vector<AvailabilityInfo> availabilityInfo = ScheduleEnq(scheduleEnq);
                        os.writeObject(availabilityInfo);
                        os.flush();
                        break;
                    case 6:
                        AddTrainAdminInfo addTrain = (AddTrainAdminInfo) oi.readObject();
                        String reply = AddTrain(addTrain);
                        os.writeUTF(reply);
                        os.flush();
                        break;
                    case 7:
                        String trainNo7 = (String) oi.readUTF();
                        Integer noOfHalt7 = (Integer) oi.readInt();
                        AddTrainAdminNextInfo[] stationInfo = new AddTrainAdminNextInfo[noOfHalt7];
                        for (int i = 0; i < noOfHalt7; i++) {
                            stationInfo[i] = (AddTrainAdminNextInfo) oi.readObject();
                        }
                        String reply7 = AddStationInfo(trainNo7, noOfHalt7, stationInfo);
                        os.writeUTF(reply7);
                        os.flush();
                        break;
                    case 8:
                        String trainNo8 = (String) oi.readUTF();
                        TrainBasicInfoAdminInfo trainBasicInfo = TrainBasicInfo(trainNo8);
                        os.writeObject(trainBasicInfo);
                        if (trainBasicInfo.getTrain_no() != 0) {
                            Vector<AddTrainAdminNextInfo> stationsInfo = TrainStationsInfo(trainNo8);
                            os.writeObject(stationsInfo);
                        }
                        os.flush();
                        break;
                    case 9:
                        AddCityAdminInfo[] addCity = (AddCityAdminInfo[]) oi.readObject();
                        String reply9 = AddCity(addCity);
                        os.writeUTF(reply9);
                        os.flush();
                        break;
                    case 10:
                        PassengersDetailForm passengersDetailForm = (PassengersDetailForm) oi.readObject();
                        System.out.println(passengersDetailForm.getDate());
                        BookedTicket bookedTicket = new BookTicket(passengersDetailForm).getBookedTicket();
                        os.writeObject(bookedTicket);
                        os.flush();
                        break;
                    case 11:
                        Vector<TrainBasicInfoAdminInfo> allTrainInfo = AllTrainInfo();
                        os.writeObject(allTrainInfo);
                        os.flush();
                        break;
                    case 12:
                        Vector<String> train12 = GetTrain();
                        os.writeObject(train12);
                        os.flush();
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

    }
}
