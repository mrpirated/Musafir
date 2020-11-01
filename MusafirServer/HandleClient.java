package MusafirServer;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
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
                userid = new Userid(rs.getInt("user_id"), rs.getString("name"));
                return userid;
            }
            query = "SELECT * FROM `user_info` WHERE phone='" + loginInfo.getUsername() + "' AND password='"
                    + String.valueOf(loginInfo.getPassword()) + "'";
            rs = c1.s.executeQuery(query);
            if (rs.next()) {
                userid = new Userid(rs.getInt("user_id"), rs.getString("name"));
                return userid;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return userid;
    }

    private Userid LoginEncrypted(LoginInfo loginInfo) {
        Userid userid = new Userid(0, "");
        try {
            String query = "SELECT * FROM `user_info` WHERE email='" + loginInfo.getUsername() + "'";

            ResultSet rs = c1.s.executeQuery(query);
            if (rs.next()) {
                char[] providedPassword = loginInfo.getPassword();
                String securePassword = rs.getString("secure_password");
                String salt = rs.getString("salt");

                boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword, salt);
                if (passwordMatch) {
                    userid = new Userid(rs.getInt("user_id"), rs.getString("name"));
                    return userid;
                }
            }
            query = "SELECT * FROM `user_info` WHERE phone='" + loginInfo.getUsername() + "'";
            rs = c1.s.executeQuery(query);
            rs.next();
            char[] providedPassword = loginInfo.getPassword();
            String securePassword = rs.getString("secure_password");
            String salt = rs.getString("salt");

            boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, securePassword, salt);
            if (passwordMatch) {
                userid = new Userid(rs.getInt("user_id"), rs.getString("name"));
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

    private Boolean SignupEncrypted(UserInfo userInfo) {

        char[] myPassword = userInfo.getPassword();

        // Generate Salt. The generated value can be stored in DB.
        String salt = PasswordUtils.getSalt(30);

        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = PasswordUtils.generateSecurePassword(myPassword, salt);
        System.out.println(salt);
        System.out.println(mySecurePassword);
        System.out.println(userInfo.getGender());
        System.out.println(userInfo.getEmail());
        System.out.println(userInfo.getPhone());

        String d = userInfo.getMm() + "-" + userInfo.getDd() + "-" + userInfo.getYy();
        String query = "INSERT INTO `user_info` ( `name`, `dob`, `gender`, `email`, `phone`, `secure_password`, `salt`) VALUES ( '"
                + userInfo.getName() + "', STR_TO_DATE('" + d + "','%m-%d-%Y'), '" + userInfo.getGender() + "', '"
                + userInfo.getEmail() + "', '" + userInfo.getPhone() + "', '" + mySecurePassword + "', '" + salt + "')";
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
        boolean tatkal = scheduleEnq.getTatkal();
        String train;
        String query = "SELECT * FROM `trains_basic_details`", query2, query3, query4;
        String st1, st2;
        String source = scheduleEnq.getSource();
        String dest = scheduleEnq.getDest();
        String trainName;
        Timestamp dep;
        boolean dynamic;
        int day1, fare1, station_no, availsl, availac;
        try {
            ResultSet rs1 = c1.s.executeQuery(query), rs2, rs3, rs4, rs5;
            while (rs1.next()) {
                train = (String) rs1.getString("train_no");
                dynamic = rs1.getBoolean("dynamic");
                query2 = "SELECT * FROM `src_dest_table` WHERE `train_no` = '" + train + "' ORDER BY `station_no` ASC";
                trainName = rs1.getString("train_name");
                Conn c2 = new Conn(), c3, c5;
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
                                query3 = "SELECT * FROM month  where date = '"
                                        + scheduleEnq.getDate().toLocalDate().minusDays(day1 - 1) + "'AND train = '"
                                        + train + "'";
                                c3 = new Conn();
                                rs4 = c3.s.executeQuery(query3);
                                while (rs4.next()) {
                                    availsl = rs4.getInt("Avail_S");
                                    availac = rs4.getInt("Avail_AC");
                                    query4 = "SELECT * FROM `tickets` WHERE index_no = '" + rs4.getInt("index_no")
                                            + "'AND type = 1";
                                    c5 = new Conn();
                                    rs5 = c5.s.executeQuery(query4);
                                    while (rs5.next()) {
                                        if (!(((rs5.getInt("dest") > station_no) && (rs5.getInt("src") < station_no))
                                                || ((rs5.getInt("src") < rs3.getInt("station_no"))
                                                        && ((rs5.getInt("dest") > rs3.getInt("station_no"))))
                                                || (rs5.getInt("dest") == rs3.getInt("station_no"))
                                                || (rs5.getInt("src") == station_no)))
                                            availsl++;
                                    }
                                    query4 = "SELECT * FROM `tickets` WHERE index_no = '" + rs4.getInt("index_no")
                                            + "'AND type = 2";
                                    c5 = new Conn();
                                    rs5 = c5.s.executeQuery(query4);
                                    while (rs5.next()) {
                                        if (!(((rs5.getInt("dest") > station_no) && (rs5.getInt("src") < station_no))
                                                || ((rs5.getInt("src") < rs3.getInt("station_no"))
                                                        && ((rs5.getInt("dest") > rs3.getInt("station_no"))))
                                                || (rs5.getInt("dest") == rs3.getInt("station_no"))
                                                || (rs5.getInt("src") == station_no)))
                                            availac++;
                                    }
                                    temp = new AvailabilityInfo(true, train, trainName, availsl, availac,
                                            rs3.getTimestamp("arrival"), dep,
                                            scheduleEnq.getDate().toLocalDate().minusDays(day1 - 1), day1,
                                            rs3.getInt("day"), rs3.getInt("fare") - fare1, station_no,
                                            rs3.getInt("station_no"), false, dynamic);

                                    availabilityInfo.add(temp);
                                    System.out.println("added");
                                }
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

    private Vector<AvailabilityInfo> ScheduleEnqtatkal(ScheduleEnq scheduleEnq) {
        Vector<AvailabilityInfo> availabilityInfo = new Vector<AvailabilityInfo>();
        AvailabilityInfo temp;
        boolean tatkal = scheduleEnq.getTatkal();
        String train;
        String query = "SELECT * FROM `trains_basic_details`", query2, query3, query4;
        String st1, st2;
        String source = scheduleEnq.getSource();
        String dest = scheduleEnq.getDest();
        String trainName;
        Timestamp dep;
        boolean dynamic;
        int day1, fare1, station_no, availsl, availac;
        try {
            ResultSet rs1 = c1.s.executeQuery(query), rs2, rs3, rs4, rs5;
            while (rs1.next()) {
                train = (String) rs1.getString("train_no");
                dynamic = rs1.getBoolean("dynamic");
                query2 = "SELECT * FROM `src_dest_table` WHERE `train_no` = '" + train + "' ORDER BY `station_no` ASC";
                trainName = rs1.getString("train_name");
                Conn c2 = new Conn(), c3, c5;
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
                                query3 = "SELECT * FROM month  where date = '"
                                        + scheduleEnq.getDate().toLocalDate().minusDays(day1 - 1) + "'AND train = '"
                                        + train + "'";
                                c3 = new Conn();
                                rs4 = c3.s.executeQuery(query3);
                                while (rs4.next()) {
                                    availsl = rs4.getInt("Tatkal_S");
                                    availac = rs4.getInt("Tatkal_AC");
                                    query4 = "SELECT * FROM `tickets` WHERE index_no = '" + rs4.getInt("index_no")
                                            + "'AND type = 1";
                                    c5 = new Conn();
                                    rs5 = c5.s.executeQuery(query4);
                                    while (rs5.next() && ((rs5.getInt("coach_no") - 1) * 72 + rs5.getInt("seat_no") > 2
                                            * availsl)) {
                                        if (!(((rs5.getInt("dest") > station_no) && (rs5.getInt("src") < station_no))
                                                || ((rs5.getInt("src") < rs3.getInt("station_no"))
                                                        && ((rs5.getInt("dest") > rs3.getInt("station_no"))))
                                                || (rs5.getInt("dest") == rs3.getInt("station_no"))
                                                || (rs5.getInt("src") == station_no)))
                                            availsl++;
                                    }
                                    query4 = "SELECT * FROM `tickets` WHERE index_no = '" + rs4.getInt("index_no")
                                            + "'AND type = 2";
                                    c5 = new Conn();
                                    rs5 = c5.s.executeQuery(query4);
                                    while (rs5.next() && ((rs5.getInt("coach_no") - 1) * 72 + rs5.getInt("seat_no") > 2
                                            * availac)) {
                                        if (!(((rs5.getInt("dest") > station_no) && (rs5.getInt("src") < station_no))
                                                || ((rs5.getInt("src") < rs3.getInt("station_no"))
                                                        && ((rs5.getInt("dest") > rs3.getInt("station_no"))))
                                                || (rs5.getInt("dest") == rs3.getInt("station_no"))
                                                || (rs5.getInt("src") == station_no)))
                                            availac++;
                                    }
                                    temp = new AvailabilityInfo(true, train, trainName, availsl, availac,
                                            rs3.getTimestamp("arrival"), dep,
                                            scheduleEnq.getDate().toLocalDate().minusDays(day1 - 1), day1,
                                            rs3.getInt("day"), rs3.getInt("fare") - fare1, station_no,
                                            rs3.getInt("station_no"), false, dynamic);

                                    availabilityInfo.add(temp);
                                    System.out.println("added");
                                }
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
            new HandleDatabase().NewTrain(trainInfo.getTrainNo(), trainInfo.getDate().toLocalDate());
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
                return " ";
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

    public Vector<Vector<String>> BotReplyFirst() {
        Vector<Vector<String>> str = new Vector<Vector<String>>();
        Vector<String> temp1 = new Vector<String>(), temp2 = new Vector<String>();
        temp1.add("Welcome To Musafir App !!!");
        temp1.add("I'm RailBOT.");
        temp1.add("I will be happy to sort you out.");
        str.add(temp1);

        temp2.add("Check these options and choose an   ");
        temp2.add("appropriate option from the below   ");
        temp2.add("menu and click on send to process.  ");
        temp2.add("                                    ");
        temp2.add("1. How To See the right Trains?     ");
        temp2.add("2. How to Check the Availability?   ");
        temp2.add("3. How to Check Fare for the train? ");
        temp2.add("4. How to Book a Ticket for a train?");
        temp2.add("5. How to get my Booking History?   ");
        temp2.add("6. How to get the Status of a PNR?  ");
        temp2.add("7. How to Cancel a Ticket manually? ");
        temp2.add("8. How to Check the Refund History? ");
        temp2.add("9. How to see the Cancelled Trains? ");
        temp2.add("10. How to see the Rerouted Trains? ");
        temp2.add("11. How to book meal for a ticket?  ");
        temp2.add("12. How to get Infos about a Train? ");
        temp2.add("13. Have Other Query? Let Us Know.  ");
        str.add(temp2);

        return str;
    }

    public Vector<String> BotResponses(Integer a) {
        Vector<String> temp = new Vector<String>();

        switch (a) {
            case 1:
                temp.add("Way To See the right Trains:");
                temp.add("1. Open HomePage. After Logging in.");
                temp.add("2. Now Click on Plan My Journey.");
                temp.add("3. Fill in the infos asked to you.");
                temp.add("4. Tap on submit button to get Trains.");
                temp.add("5. Information will get displayed below.");
                temp.add("Hola!!! You got the right trains.");
                temp.add("Anything else? Select from the menu.");
                break;
            case 2:
                temp.add("Way to Check Availability for a train:");
                temp.add("1. Open HomePage. After Logging in.");
                temp.add("2. Now Click on Plan My Journey.");
                temp.add("3. Fill in the infos asked to you.");
                temp.add("4. Tap on submit button to get Trains.");
                temp.add("5. Now Check the availability column.");
                temp.add("You Did it!! My Job is Done. Go, Book.");
                temp.add("Anything else? Select from the menu.");
                break;

            case 3:
                temp.add("Way to Check Fare based on requirements:");
                temp.add("1. Open HomePage. After Logging in.");
                temp.add("2. Now Click on Plan My Journey.");
                temp.add("3. Fill in the infos asked to you.");
                temp.add("4. Tap on submit button to get Trains.");
                temp.add("5. Now Check the fare column in the list.");
                temp.add("Gosh!! Did the Right Job didn't I?");
                temp.add("Anything else? Select from the menu.");
                break;

            case 4:
                temp.add("Way to Book a Ticket for a Train:");
                temp.add("1. Open HomePage. After Logging in.");
                temp.add("2. Now Click on Plan My Journey.");
                temp.add("3. Fill in the infos asked to you.");
                temp.add("4. Tap on submit button to get Trains.");
                temp.add("5. Now select the right train from list.");
                temp.add("6. Fill the Passenger Details and Submit.");
                temp.add("Anything else? Select from the menu.");
                break;

            case 5:
                temp.add("Way to get my Booking History is:");
                temp.add("1. Open HomePage. After Logging in.");
                temp.add("2. Now Click on Booking History.");
                temp.add("3. Now find the righteous PNR.");
                temp.add("4. Found the Correct PNR ???!");
                temp.add("5. Check the Journey Details then.");
                temp.add("Helping Others Is my Passion!!");
                temp.add("Anything else? Select from the menu.");
                break;

            case 6:
                temp.add("Way to check the PNR Status is:");
                temp.add("1. Open HomePage. After Logging in.");
                temp.add("2. Now Click on PNR Enquiry Tab.");
                temp.add("3. Now enter the 10 Digits PNR.");
                temp.add("4. Click on submit button to process.");
                temp.add("5. The Information will be displayed.");
                temp.add("Gosh!! Did the Right Job didn't I?");
                temp.add("Anything else? Select from the menu.");
                break;

            case 7:
                temp.add("Way to Cancel a Ticket manually is:");
                temp.add("1. Open HomePage. After Logging in.");
                temp.add("2. Now Click on Cancel Ticket tab.");
                temp.add("3. Now fill the informations asked.");
                temp.add("4. Now click on the Submit button.");
                temp.add("5. Verify the Details of the ticket.");
                temp.add("Helping Others Is my Passion!!");
                temp.add("Anything else? Select from the menu.");
                break;

            case 8:
                temp.add("How to Check the Refund History?");
                temp.add("1. Open HomePage. After Logging in.");
                temp.add("2. Now Click on PNR Enquiry Tab.");
                temp.add("3. Now enter the 10 Digits PNR.");
                temp.add("4. Click on submit button to process.");
                temp.add("5. The Information will be displayed.");
                temp.add("Gosh!! Did the Right Job didn't I?");
                temp.add("Anything else? Select from the menu.");
                break;

            case 9:
                temp.add("Way to see the Cancelled Trains:   ");
                temp.add("1. Open HomePage. After Logging in.");
                temp.add("2. Now Click on Cancelled Trains.  ");
                temp.add("3. Now enter the details asked.    ");
                temp.add("4. Click on submit button to process.");
                temp.add("5. The Information will be displayed.");
                temp.add("Helping Others Is my Passion!!     ");
                temp.add("Anything else? Select from the menu.");
                break;

            case 10:
                temp.add("Way to see the Rerouted Trains:   ");
                temp.add("1. Open HomePage. After Logging in.");
                temp.add("2. Now Click on Rerouted Trains tab.");
                temp.add("3. Now enter the details asked.   ");
                temp.add("4. Click on submit button to process.");
                temp.add("5. The Information will be displayed.");
                temp.add("Gosh!! Did the Right Job didn't I?");
                temp.add("Anything else? Select from the menu.");
                break;

            case 11:
                temp.add("Way to book the meal for a ticket:");
                temp.add("1. Open HomePage. After Logging in.");
                temp.add("2. Now Click on Book A Meal tab.");
                temp.add("3. Now choose the PNR to select it.");
                temp.add("4. Click on submit button to process.");
                temp.add("5. Meal will be booked accordingly.");
                temp.add("I hope you enjoy the meal on Rails!");
                temp.add("Anything else? Select from the menu.");
                break;

            case 12:
                temp.add("Way to get Info about a Train:");
                temp.add("1. Open HomePage. After Logging in.");
                temp.add("2. Now Click on Train Info tab.");
                temp.add("3. Now choose the train number.");
                temp.add("4. Click on 'Get Details' button.");
                temp.add("5. Info will be displayed accordingly.");
                temp.add("Gosh!! Did the Right Job didn't I?");
                temp.add("Anything else? Select from the menu.");
                break;

        }

        return temp;
    }

    private String CancelTrains(CancelTrainAdminInfo cancelTrainAdminInfo) {
        LocalDate start = cancelTrainAdminInfo.getFrom().toLocalDate();
        LocalDate end = cancelTrainAdminInfo.getTo().toLocalDate();
        int index;
        String PNR, query2, query4, query5, query6, query7;
        Conn c1, c3, c4, c5, c6;
        ResultSet rs1, rs3, rs4, rs5, rs6;
        while (start.compareTo(end) <= 0) {
            BookingHistory bookingHistory;
            query2 = "SELECT * FROM month WHERE date = '" + start + "' AND train = '"
                    + cancelTrainAdminInfo.getTrainNo() + "'";
            try {
                c1 = new Conn();
                rs1 = c1.s.executeQuery(query2);
                rs1.next();
                index = rs1.getInt("index_no");
                while (true) {
                    query4 = "SELECT * FROM tickets WHERE index_no = '" + index + "'";
                    c3 = new Conn();
                    rs3 = c3.s.executeQuery(query4);
                    query7 = "SELECT * FROM trains_basic_details WHERE train_no = '" + cancelTrainAdminInfo.getTrainNo()
                            + "'";
                    c6 = new Conn();
                    rs6 = c6.s.executeQuery(query7);
                    rs6.next();
                    if (rs3.next()) {
                        PNR = rs3.getString("PNR");
                        query5 = "SELECT * FROM passenger WHERE PNR = '" + PNR + "'";
                        c4 = new Conn();
                        rs4 = c4.s.executeQuery(query5);
                        rs4.next();
                        bookingHistory = new BookingHistory();
                        bookingHistory.setPNR(PNR);
                        bookingHistory.setTrain(cancelTrainAdminInfo.getTrainNo() + " " + rs6.getString("train_name"));
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
                                        "S" + rs5.getInt("coach_no") + " " + rs5.getInt("seat_no"), rs5.getInt("age"),
                                        rs5.getString("gender").charAt(0),rs5.getFloat("fare"));
                            if (rs5.getInt("type") == 2)
                                passengerHistory[k] = new PassengerHistory(rs5.getString("name"),
                                        "B" + rs5.getInt("coach_no") + " " + rs5.getInt("seat_no"), rs5.getInt("age"),
                                        rs5.getString("gender").charAt(0),rs5.getFloat("fare"));

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
                            query6 = "INSERT INTO `booking_history` (`user_id`, `PNR`,`train`, `name`, `age`, `gender`, `source`, `destination`, `date`, `seat`,`cancelled`) VALUES ('"
                                    + bookingHistory.getUserid() + "', '" + bookingHistory.getPNR() + "','"
                                    + bookingHistory.getTrain() + "' ,'"
                                    + bookingHistory.getPassengerHistory()[j].getName() + "', '"
                                    + bookingHistory.getPassengerHistory()[j].getAge() + "', '"
                                    + bookingHistory.getPassengerHistory()[j].getGender() + "', '"
                                    + bookingHistory.getSrc() + "', '" + bookingHistory.getDest() + "', '"
                                    + bookingHistory.getDate() + "', '"
                                    + bookingHistory.getPassengerHistory()[j].getSeat() + "',2)";
                            c5 = new Conn();
                            c5.s.executeUpdate(query6);
                        }
                    } else
                        break;
                }
                query6 = "DELETE FROM `month` WHERE index_no = '" + index + "'";
                c3 = new Conn();
                c3.s.executeUpdate(query6);
            } catch (Exception e) {
                e.printStackTrace();
            }
            start = start.plusDays(1);
        }
        return "ok";
    }

    public String GetTrainPassenger(String pnr) {
        String train = " ";
        String query = "SELECT * FROM `passenger` WHERE `PNR` = '" + pnr + "'";

        try {
            ResultSet rs = c1.s.executeQuery(query);
            if (rs.next()) {
                train = rs.getString("train");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return train;
    }

    public PnrEnquiryFinalInfo GetPassengerDetails(String pnr, String train) {
        String name, coach_no, gender, src = "", dest = "";
        char meal = ' ';
        java.sql.Date doj;
        Integer type, seat_no, waiting, age, index_no, srcIndex, destIndex;
        String query = "SELECT * FROM `tickets` WHERE `PNR` = '" + pnr + "'", query2, query3;
        PnrEnquiryFinalInfo pnrDetails;
        Vector<PnrEnquiryInfo> passengersDetails = new Vector<PnrEnquiryInfo>();
        PnrEnquiryInfo temp;

        String parts[] = train.split(" ", 2);
        String trainNo = parts[0], trainName = parts[1];
        long d = System.currentTimeMillis();
        doj = new java.sql.Date(d);

        try {
            c1 = new Conn();
            ResultSet rs = c1.s.executeQuery(query), rs2, rs3;
            if (rs.next()) {
                index_no = rs.getInt("index_no");
                query2 = "SELECT * FROM `month` WHERE `index_no` = " + index_no + "";
                Conn c3 = new Conn();
                rs2 = c3.s.executeQuery(query2);
                rs2.next();
                doj = rs2.getDate("date");
                meal = rs.getString("meal").charAt(0);

                srcIndex = rs.getInt("src");
                destIndex = rs.getInt("dest");

                query3 = "SELECT * FROM `src_dest_table` WHERE `train_no` = '" + trainNo + "'";
                Conn c4 = new Conn();
                rs3 = c4.s.executeQuery(query3);
                while (rs3.next()) {
                    if (srcIndex == rs3.getInt("station_no"))
                        src = rs3.getString("station");
                    if (destIndex == rs3.getInt("station_no"))
                        dest = rs3.getString("station");
                }
                rs3.close();
                name = rs.getString("name");
                type = rs.getInt("type");
                coach_no = rs.getString("coach_no");
                seat_no = rs.getInt("seat_no");
                waiting = rs.getInt("waiting");
                age = rs.getInt("age");
                gender = rs.getString("gender");

                temp = new PnrEnquiryInfo(name, type, coach_no, seat_no, waiting, age, gender, meal);
                passengersDetails.add(temp);
            }
            while (rs.next()) {
                name = rs.getString("name");
                type = rs.getInt("type");
                coach_no = rs.getString("coach_no");
                seat_no = rs.getInt("seat_no");
                waiting = rs.getInt("waiting");
                age = rs.getInt("age");
                gender = rs.getString("gender");
                meal = rs.getString("meal").charAt(0);

                temp = new PnrEnquiryInfo(name, type, coach_no, seat_no, waiting, age, gender, meal);
                passengersDetails.add(temp);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        pnrDetails = new PnrEnquiryFinalInfo(trainNo, trainName, src, dest, passengersDetails, doj, pnr);

        return pnrDetails;
    }

    public String BookMeal(BookAMealInfo bookMeal) {
        String s = " ";
        String query = "UPDATE `tickets` SET `meal`='1' WHERE PNR='" + bookMeal.getPnr() + "' AND name='"
                + bookMeal.getName() + "' AND age=" + bookMeal.getAge() + "";
        try {
            c1.s.executeUpdate(query);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public Vector<PnrEnquiryFinalInfo> BookingHistory1(Integer userId) {
        Vector<PnrEnquiryFinalInfo> bookingHistory1 = new Vector<PnrEnquiryFinalInfo>();
        PnrEnquiryFinalInfo temp;
        String query1 = "SELECT * FROM `passenger` WHERE `user_id` = " + userId + "";

        try {
            int i = 1;
            Conn c5 = new Conn();
            ResultSet rs5 = c5.s.executeQuery(query1);
            while (rs5.next()) {
                String train = rs5.getString("train");
                String pnr = rs5.getString("PNR");
                temp = GetPassengerDetails(pnr, train);
                bookingHistory1.add(temp);
                System.out.println(i++);
            }
            rs5.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookingHistory1;
    }

    public BookingHistory2FinalInfo SingleHistory(String pnr, String train) {
        String trainNo, trainName, src = " ", dest = " ";
        java.sql.Date doj;
        String name, gender, seat;
        Integer age, cancelled;
        String query = "SELECT * FROM `booking_history` WHERE `PNR` = '" + pnr + "'", query2, query3;
        BookingHistory2FinalInfo pnrDetails;
        Vector<BookingHistory2TicketInfo> passengersDetails = new Vector<BookingHistory2TicketInfo>();
        BookingHistory2TicketInfo temp;

        String parts[] = train.split(" ", 2);
        trainNo = parts[0];
        trainName = parts[1];
        long d = System.currentTimeMillis();
        doj = new java.sql.Date(d);

        try {
            Conn c2 = new Conn();
            ResultSet rs2 = c2.s.executeQuery(query), rs3;
            if (rs2.next()) {
                name = rs2.getString("name");
                age = rs2.getInt("age");
                gender = rs2.getString("gender");
                src = rs2.getString("source");
                dest = rs2.getString("destination");
                doj = rs2.getDate("date");
                seat = rs2.getString("seat");
                cancelled = rs2.getInt("cancelled");

                temp = new BookingHistory2TicketInfo(name, age, gender, seat, cancelled);
                passengersDetails.add(temp);
            }
            while (rs2.next()) {
                name = rs2.getString("name");
                age = rs2.getInt("age");
                gender = rs2.getString("gender");
                seat = rs2.getString("seat");
                cancelled = rs2.getInt("cancelled");

                temp = new BookingHistory2TicketInfo(name, age, gender, seat, cancelled);
                passengersDetails.add(temp);
            }
            rs2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        pnrDetails = new BookingHistory2FinalInfo(trainNo, trainName, src, dest, passengersDetails, doj, pnr);

        return pnrDetails;
    }

    public Vector<PnrEnquiryFinalInfo> PassengerDetails(PassengerInfoAdminInfo passengerInfo) {
        Vector<PnrEnquiryFinalInfo> bookingHistory1 = new Vector<PnrEnquiryFinalInfo>();
        PnrEnquiryFinalInfo temp;
        String train1 = passengerInfo.getTrain();
        String parts[] = train1.split(" ", 2);
        String trainNo = parts[0];
        String trainsz = parts[1];
        String parts1[] = trainsz.split(" ", 2);
        String trainName = parts1[1];
        String traind = trainNo + " " + trainName;
        System.out.println(traind);
        String query1 = "SELECT * FROM `passenger` WHERE `train` = '" + traind + "' AND `date`= '"
                + passengerInfo.getDate() + "'";
        System.out.println(query1);
        try {
            int i = 1;
            Conn c5 = new Conn();
            ResultSet rs5 = c5.s.executeQuery(query1);
            while (rs5.next()) {
                String train = rs5.getString("train");
                String pnr = rs5.getString("PNR");
                System.out.println(pnr);
                temp = GetPassengerDetails(pnr, train);
                bookingHistory1.add(temp);
                System.out.println(i++);
            }
            rs5.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookingHistory1;

    }

    private Vector<BookingHistory2FinalInfo> BookingHistory2(Integer userId) {
        Vector<BookingHistory2FinalInfo> bookingHistory2 = new Vector<BookingHistory2FinalInfo>();
        BookingHistory2FinalInfo temp;
        String query1 = "SELECT * FROM `booking_history` WHERE `user_id` = " + userId + "";

        try {
            Conn c5 = new Conn();
            ResultSet rs5 = c5.s.executeQuery(query1);
            while (rs5.next()) {
                String train = rs5.getString("train");
                String pnr = rs5.getString("PNR");
                temp = SingleHistory(pnr, train);
                bookingHistory2.add(temp);
            }
            rs5.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingHistory2;
    }

    private String Canceluserticket(CancelTicket cancelTicket) {
        String PNR = cancelTicket.getPNR();
        Conn c1, c2, c3, c4;
        String query1, query2, query3, query4;
        ResultSet rs1, rs2, rs3, rs4;
        int index;
        BookingHistory bookingHistory = new BookingHistory();
        bookingHistory.setUserid(cancelTicket.getUserid());
        bookingHistory.setPNR(PNR);
        bookingHistory.setDate(cancelTicket.getDate());
        bookingHistory.setDest(cancelTicket.getDest());
        bookingHistory.setSrc(cancelTicket.getSrc());
        bookingHistory.setTrain(cancelTicket.getTrain());
        if (cancelTicket.getName() != null) {
            PassengerHistory[] passengerHistory = new PassengerHistory[1];

            try {
                query1 = "SELECT * FROM tickets WHERE PNR ='" + PNR + "' AND name = '" + cancelTicket.getName() + "' ";
                c1 = new Conn();
                rs1 = c1.s.executeQuery(query1);
                rs1.next();
                index = rs1.getInt("index_no");
                passengerHistory[0] = new PassengerHistory(rs1.getString("name"),
                        "S" + rs1.getInt("coach_no") + " " + rs1.getInt("seat_no"), rs1.getInt("age"),
                        rs1.getString("gender").charAt(0),rs1.getFloat("fare"));
                bookingHistory.setPassengerHistory(passengerHistory);
                query2 = "SELECT * FROM month WHERE index_no = '" + index + "'";
                c2 = new Conn();
                rs2 = c2.s.executeQuery(query2);
                rs2.next();
                if (rs1.getInt("type") == 1) {
                    if (rs2.getInt("Avail_S") < 0) {
                        query3 = "SELECT * FROM tickets WHERE index_no = '" + index
                                + "' AND type = 1AND seat_no = NULL ORDER BY `tickets`.`waiting` ASC";
                        c3 = new Conn();
                        rs3 = c3.s.executeQuery(query3);
                        rs3.next();
                        c4 = new Conn();
                        query4 = "UPDATE tickets SET seat_no = '" + rs1.getInt("seat_no") + "',coach_no = '"
                                + rs1.getInt("coach_no") + "' WHERE waiting = 1";
                        c4.s.executeUpdate(query4);
                        rs3.next();
                        while (rs3.next()) {
                            c4 = new Conn();
                            query4 = "UPDATE tickets SET waiting = '" + (rs3.getInt("waiting") - 1)
                                    + "' WHERE waiting = '" + rs3.getInt("waiting") + "'";
                            c4.s.executeUpdate(query4);
                        }

                    } else {
                        query3 = "UPDATE `month` SET `Avail_S` = '" + (rs2.getInt("Avail_S") + 1)
                                + "' WHERE `month`.`index_no` = '" + index + "'";
                        c3 = new Conn();
                        c3.s.executeUpdate(query3);
                    }
                }
                if (rs1.getInt("type") == 2) {
                    if (rs2.getInt("Avail_AC") < 0) {
                        query3 = "SELECT * FROM tickets WHERE index_no = '" + index
                                + "' AND type = 2 AND seat_no = NULL ORDER BY `tickets`.`waiting` ASC";
                        c3 = new Conn();
                        rs3 = c3.s.executeQuery(query3);
                        rs3.next();
                        c4 = new Conn();
                        query4 = "UPDATE tickets SET seat_no = '" + rs1.getInt("seat_no") + "',coach_no = '"
                                + rs1.getInt("coach_no") + "' WHERE waiting = 1";
                        c4.s.executeUpdate(query4);
                        rs3.next();
                        while (rs3.next()) {
                            c4 = new Conn();
                            query4 = "UPDATE tickets SET waiting = '" + (rs3.getInt("waiting") - 1)
                                    + "' WHERE waiting = '" + rs3.getInt("waiting") + "'";
                            c4.s.executeUpdate(query4);
                        }

                    } else {
                        query3 = "UPDATE `month` SET `Avail_S` = '" + (rs2.getInt("Avail_AC") + 1)
                                + "' WHERE `month`.`index_no` = '" + index + "'";
                        c3 = new Conn();
                        c3.s.executeUpdate(query3);
                    }
                }
                for (int j = 0; j < 1; j++) {
                    query4 = "INSERT INTO `booking_history` (`user_id`, `PNR`,`train`, `name`, `age`, `gender`, `source`, `destination`, `date`, `seat`,`cancelled`) VALUES ('"
                            + bookingHistory.getUserid() + "', '" + bookingHistory.getPNR() + "','"
                            + bookingHistory.getTrain() + "' ,'" + bookingHistory.getPassengerHistory()[j].getName()
                            + "', '" + bookingHistory.getPassengerHistory()[j].getAge() + "', '"
                            + bookingHistory.getPassengerHistory()[j].getGender() + "', '" + bookingHistory.getSrc()
                            + "', '" + bookingHistory.getDest() + "', '" + bookingHistory.getDate() + "', '"
                            + bookingHistory.getPassengerHistory()[j].getSeat() + "',1)";
                    c4 = new Conn();
                    c4.s.executeUpdate(query4);
                }
                query4 = "DELETE FROM tickets WHERE PNR ='" + PNR + "' AND name = '" + cancelTicket.getName() + "' ";
                c4 = new Conn();
                c4.s.executeUpdate(query4);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                query1 = "SELECT * FROM passenger WHERE PNR ='" + PNR + "'";
                c1 = new Conn();
                rs1 = c1.s.executeQuery(query1);
                rs1.next();
                PassengerHistory[] passengerHistory = new PassengerHistory[rs1.getInt("tickets")];
                query1 = "SELECT * FROM tickets WHERE PNR ='" + PNR + "' ";
                c1 = new Conn();
                rs1 = c1.s.executeQuery(query1);
                int i = 0;
                while (rs1.next()) {
                    index = rs1.getInt("index_no");
                    passengerHistory[i] = new PassengerHistory(rs1.getString("name"),
                            "S" + rs1.getInt("coach_no") + " " + rs1.getInt("seat_no"), rs1.getInt("age"),
                            rs1.getString("gender").charAt(0),rs1.getFloat("fare"));

                    i++;
                    query2 = "SELECT * FROM month WHERE index_no = '" + index + "'";
                    c2 = new Conn();
                    rs2 = c2.s.executeQuery(query2);
                    rs2.next();
                    if (rs1.getInt("type") == 1) {
                        if (rs2.getInt("Avail_S") < 0) {
                            query3 = "SELECT * FROM tickets WHERE index_no = '" + index
                                    + "' AND type = 1AND seat_no = NULL ORDER BY `tickets`.`waiting` ASC";
                            c3 = new Conn();
                            rs3 = c3.s.executeQuery(query3);
                            rs3.next();
                            c4 = new Conn();
                            query4 = "UPDATE tickets SET seat_no = '" + rs1.getInt("seat_no") + "',coach_no = '"
                                    + rs1.getInt("coach_no") + "' WHERE waiting = 1";
                            c4.s.executeUpdate(query4);
                            rs3.next();
                            while (rs3.next()) {
                                c4 = new Conn();
                                query4 = "UPDATE tickets SET waiting = '" + (rs3.getInt("waiting") - 1)
                                        + "' WHERE waiting = '" + rs3.getInt("waiting") + "'";
                                c4.s.executeUpdate(query4);
                            }

                        } else {
                            query3 = "UPDATE `month` SET `Avail_S` = '" + (rs2.getInt("Avail_S") + 1)
                                    + "' WHERE `month`.`index_no` = '" + index + "'";
                            c3 = new Conn();
                            c3.s.executeUpdate(query3);
                        }
                    }
                    if (rs1.getInt("type") == 2) {
                        if (rs2.getInt("Avail_AC") < 0) {
                            query3 = "SELECT * FROM tickets WHERE index_no = '" + index
                                    + "' AND type = 2 AND seat_no = NULL ORDER BY `tickets`.`waiting` ASC";
                            c3 = new Conn();
                            rs3 = c3.s.executeQuery(query3);
                            rs3.next();
                            c4 = new Conn();
                            query4 = "UPDATE tickets SET seat_no = '" + rs1.getInt("seat_no") + "',coach_no = '"
                                    + rs1.getInt("coach_no") + "' WHERE waiting = 1";
                            c4.s.executeUpdate(query4);
                            rs3.next();
                            while (rs3.next()) {
                                c4 = new Conn();
                                query4 = "UPDATE tickets SET waiting = '" + (rs3.getInt("waiting") - 1)
                                        + "' WHERE waiting = '" + rs3.getInt("waiting") + "'";
                                c4.s.executeUpdate(query4);
                            }

                        } else {
                            query3 = "UPDATE `month` SET `Avail_S` = '" + (rs2.getInt("Avail_AC") + 1)
                                    + "' WHERE `month`.`index_no` = '" + index + "'";
                            c3 = new Conn();
                            c3.s.executeUpdate(query3);
                        }
                    }
                    for (int j = 0; j < passengerHistory.length; j++) {
                        query4 = "INSERT INTO `booking_history` (`user_id`, `PNR`,`train`, `name`, `age`, `gender`, `source`, `destination`, `date`, `seat`,`cancelled`) VALUES ('"
                                + bookingHistory.getUserid() + "', '" + bookingHistory.getPNR() + "','"
                                + bookingHistory.getTrain() + "' ,'" + bookingHistory.getPassengerHistory()[j].getName()
                                + "', '" + bookingHistory.getPassengerHistory()[j].getAge() + "', '"
                                + bookingHistory.getPassengerHistory()[j].getGender() + "', '" + bookingHistory.getSrc()
                                + "', '" + bookingHistory.getDest() + "', '" + bookingHistory.getDate() + "', '"
                                + bookingHistory.getPassengerHistory()[j].getSeat() + "',1)";
                        c4 = new Conn();
                        c4.s.executeUpdate(query4);
                    }
                    query4 = "DELETE FROM tickets WHERE PNR ='" + PNR + "' ";
                    c4 = new Conn();
                    c4.s.executeUpdate(query4);
                    query4 = "DELETE FROM passengers WHERE PNR ='" + PNR + "' ";
                    c4 = new Conn();
                    c4.s.executeUpdate(query4);

                }
                bookingHistory.setPassengerHistory(passengerHistory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "ok";
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
                        Userid userid = LoginEncrypted(loginInfo);
                        os.writeObject(userid);
                        os.flush();
                        break;

                    case 2:
                        UserInfo userInfo = (UserInfo) oi.readObject();
                        Boolean b = SignupEncrypted(userInfo);
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
                    case 13:
                        BookAMealInfo bookMeal = (BookAMealInfo) oi.readObject();
                        String reply13 = BookMeal(bookMeal);
                        os.writeUTF(reply13);
                        os.flush();
                        break;
                    case 14:
                        CancelTrainAdminInfo cancelTrainAdminInfo = (CancelTrainAdminInfo) oi.readObject();
                        String res = CancelTrains(cancelTrainAdminInfo);
                        os.writeUTF(res);
                        os.flush();
                        break;
                    case 15:
                        ScheduleEnq scheduleEnqtatkal = (ScheduleEnq) oi.readObject();
                        Vector<AvailabilityInfo> availabilityInfotatkal = ScheduleEnqtatkal(scheduleEnqtatkal);
                        os.writeObject(availabilityInfotatkal);
                        os.flush();
                        break;
                    case 16:
                        PassengersDetailForm passengersDetailFormtatkal = (PassengersDetailForm) oi.readObject();
                        System.out.println(passengersDetailFormtatkal.getDate());
                        BookedTicket bookedTickettatkal = new BookTatkal(passengersDetailFormtatkal).getBookedTicket();
                        os.writeObject(bookedTickettatkal);
                        os.flush();
                        break;

                    case 17:
                        Vector<Vector<String>> reply17 = BotReplyFirst();
                        os.writeObject(reply17);
                        os.flush();
                        break;
                    case 18:
                        Integer selectedItem = (Integer) oi.readInt();
                        Vector<String> reply18 = BotResponses(selectedItem);
                        os.writeObject(reply18);
                        os.flush();
                        break;
                    case 19:
                        String pnr19 = (String) oi.readUTF();
                        String train19 = GetTrainPassenger(pnr19);
                        os.writeUTF(train19);
                        Integer result19 = train19.compareTo(" ");
                        if (result19 != -1) {
                            PnrEnquiryFinalInfo pnrDetails = GetPassengerDetails(pnr19, train19);
                            os.writeObject(pnrDetails);
                        }
                        os.flush();
                        break;
                    case 20:
                        Integer userId20 = (Integer) oi.readInt();
                        Vector<PnrEnquiryFinalInfo> bookingHistory1 = BookingHistory1(userId20);
                        Vector<BookingHistory2FinalInfo> bookingHistory2 = BookingHistory2(userId20);
                        os.writeObject(bookingHistory1);
                        os.writeObject(bookingHistory2);
                        os.flush();
                        break;
                    case 21:
                        CancelTicket cancelTicket = (CancelTicket) oi.readObject();
                        os.writeUTF(Canceluserticket(cancelTicket));
                        os.flush();
                        break;
                    case 22:
                        PassengerInfoAdminInfo passengerInfo = (PassengerInfoAdminInfo) oi.readObject();
                        Vector<PnrEnquiryFinalInfo> bookingDetails = PassengerDetails(passengerInfo);
                        os.writeObject(bookingDetails);
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
