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
        int day1, fare1, station_no, availsl, availac;
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
                                availsl = rs1.getInt("Avail_S") - rs1.getInt("Total_S") / 3;
                                availac = rs1.getInt("Avail_AC") - rs1.getInt("Total_AC") / 3;
                                temp = new AvailabilityInfo(true, train, trainName, availsl, availac,
                                        rs3.getTimestamp("arrival"), dep, (Date) scheduleEnq.getDate(), day1,
                                        rs3.getInt("day"), rs3.getInt("fare") - fare1, station_no,
                                        rs3.getInt("station_no"));

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
            new HandleDatabase().NewTrain(trainInfo.getTrainNo());
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
                }

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

    }
}
