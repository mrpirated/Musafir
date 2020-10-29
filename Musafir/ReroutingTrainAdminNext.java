package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.sql.*;
import java.lang.*;
import java.util.*;

public class ReroutingTrainAdminNext extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, l2, l3, l4, l5, l6, l7, l8, l9;
    private JPanel p1, p2, panel;
    private JButton back, next;
    // JTextField pnrText, noOfHalts;
    private JTextField[] arrival, departure, distance, day, platform, fare;
    private JLabel[] number;
    private String trainNo;
    private String[][] cities;
    private Connect connection;
    private Panel details;
    private JScrollPane scroll;
    private JComboBox[] stationFields;
    private java.sql.Date dateFrom, dateTo;
    Vector<AddTrainAdminNextInfo> stationsInfo, reroutedStations = new Vector<AddTrainAdminNextInfo>();
    private Integer noOfReroutes, startStation, endStation;

    public ReroutingTrainAdminNext(Connect connection, String trainNo, Integer startStation, Integer endStation,
            Integer noOfReroutes, String[][] cities, Vector<AddTrainAdminNextInfo> stationsInfo, java.sql.Date dateFrom,
            java.sql.Date dateTo) {
        this.connection = connection;
        this.trainNo = trainNo;
        this.noOfReroutes = noOfReroutes;
        this.startStation = startStation;
        this.endStation = endStation;
        this.cities = cities;
        this.stationsInfo = stationsInfo;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

        System.out.println(dateFrom);
        System.out.println(dateTo);

        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "ADD REROUTE DETAILS");

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(Color.BLACK);
        p1.setBounds(0, 0, 750, 45);
        add(p1);

        headLabel = new JLabel("Fill Reroute Details for Train No: " + trainNo);
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(150, 10, 600, 30);
        p1.add(headLabel);

        l3 = new JLabel("Station Name");
        l3.setFont(new Font("Times new roman", Font.BOLD, 15));
        l3.setBounds(55, 50, 400, 32);
        add(l3);

        l4 = new JLabel("Arrival");
        l4.setFont(new Font("Times new roman", Font.BOLD, 15));
        l4.setBounds(210, 50, 400, 32);
        add(l4);

        l5 = new JLabel("Departure");
        l5.setFont(new Font("Times new roman", Font.BOLD, 15));
        l5.setBounds(290, 50, 400, 32);
        add(l5);

        l6 = new JLabel("Distance");
        l6.setFont(new Font("Times new roman", Font.BOLD, 15));
        l6.setBounds(400, 50, 400, 32);
        add(l6);

        l7 = new JLabel("Day");
        l7.setFont(new Font("Times new roman", Font.BOLD, 15));
        l7.setBounds(500, 50, 400, 32);
        add(l7);

        l8 = new JLabel("Platform");
        l8.setFont(new Font("Times new roman", Font.BOLD, 15));
        l8.setBounds(565, 50, 400, 32);
        add(l8);

        l9 = new JLabel("Fare");
        l9.setFont(new Font("Times new roman", Font.BOLD, 15));
        l9.setBounds(670, 50, 400, 32);
        add(l9);

        stationFields = new JComboBox[noOfReroutes];
        arrival = new JTextField[noOfReroutes];
        departure = new JTextField[noOfReroutes];
        distance = new JTextField[noOfReroutes];
        platform = new JTextField[noOfReroutes];
        fare = new JTextField[noOfReroutes];
        day = new JTextField[noOfReroutes];

        number = new JLabel[noOfReroutes];

        details = new Panel();

        Integer i = 0, y1 = 10;

        for (i = 0; i < startStation - 1; i++) {
            reroutedStations.add(stationsInfo.get(i));
        }

        for (i = 0; i < noOfReroutes; i++) {
            String str = String.valueOf(i + 1);
            number[i] = new JLabel(str + ".");
            number[i].setFont(new Font("Times new roman", Font.BOLD, 15));
            number[i].setBounds(2, y1 - 8, 400, 32);
            details.add(number[i]);

            Integer x1 = 20;

            stationFields[i] = new JComboBox(cities[0]);
            stationFields[i].setBackground(Color.WHITE);
            stationFields[i].setBounds(x1, y1, 160, 20);
            stationFields[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(stationFields[i]);

            x1 = 195;
            arrival[i] = new JTextField(51);
            arrival[i].setBounds(x1, y1, 70, 20);
            arrival[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(arrival[i]);

            x1 = 280;
            departure[i] = new JTextField(50);
            departure[i].setBounds(x1, y1, 70, 20);
            departure[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(departure[i]);

            x1 = 370;
            distance[i] = new JTextField(50);
            distance[i].setBounds(x1, y1, 80, 20);
            distance[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(distance[i]);

            x1 = 470;
            day[i] = new JTextField(50);
            day[i].setBounds(x1, y1, 50, 20);
            day[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(day[i]);

            x1 = 545;
            platform[i] = new JTextField(50);
            platform[i].setBounds(x1, y1, 80, 20);
            platform[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(platform[i]);

            x1 = 630;
            fare[i] = new JTextField(50);
            fare[i].setBounds(x1, y1, 40, 20);
            fare[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(fare[i]);
            y1 = y1 + 30;
            System.out.println(i + "\n");
        }
        details.setLayout(null);
        scroll = new JScrollPane(details);
        scroll.setBounds(20, 90, 700, 600);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        getContentPane().add(scroll);

        next = new JButton("Submit");
        next.setBackground(Color.BLACK);
        next.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        next.setForeground(Color.WHITE);
        next.setBounds(300, 700, 100, 25);
        add(next);

        next.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 770);
        setLocation(400, 50);
        setVisible(true);
    }

    class Panel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            if (noOfReroutes > 20)
                return new Dimension(650, 610 + 30 * (noOfReroutes - 20));
            else
                return new Dimension(700, 610);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            AddTrainAdminNextInfo temp;

            for (int i = 0; i < noOfReroutes; i++) {
                String station = (String) stationFields[i].getSelectedItem();
                String arrivalx = arrival[i].getText();
                java.sql.Time arrival = java.sql.Time.valueOf(arrivalx);
                String departurex = departure[i].getText();
                java.sql.Time departure = java.sql.Time.valueOf(departurex);
                String distancex = distance[i].getText();
                Integer distance = Integer.parseInt(distancex);
                String dayx = day[i].getText();
                Integer day = Integer.parseInt(dayx);
                String platformx = platform[i].getText();
                Integer platform = Integer.parseInt(platformx);
                String farex = fare[i].getText();
                Integer fare = Integer.parseInt(farex);

                temp = new AddTrainAdminNextInfo(noOfReroutes + i, station, arrival, departure, distance, day, platform,
                        fare);

                reroutedStations.add(temp);
            }

            int j = 0;

            for (int i = endStation; i < stationsInfo.size(); i++) {
                temp = stationsInfo.get(i);
                temp.setNumber(startStation + noOfReroutes + j++);
                reroutedStations.add(temp);
            }

            for (int i = 0; i < reroutedStations.size(); i++) {
                System.out.println(reroutedStations.get(i).getNumber() + " - " + reroutedStations.get(i).getStation());
            }

            RerouteTrainAdminInfo reroutedDetails = new RerouteTrainAdminInfo(trainNo, dateFrom, dateTo,
                    reroutedStations);

            if (ae.getSource() == next) {
                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(13);
                    os.writeObject(reroutedDetails);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                String s = (String) oi.readUTF();
                if (s.equals("ok")) {
                    JOptionPane.showMessageDialog(null, "Train Rerouted Successfully.");
                    new AdminHome(connection).setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Request Not Processed.");
                    new AdminHome(connection).setVisible(true);
                    setVisible(false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
