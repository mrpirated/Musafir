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

public class AddTrainAdminNext extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, l2, l3, l4, l5, l6, l7, l8, l9;
    private JPanel p1, p2, panel;
    private JButton back, next;
    // JTextField pnrText, noOfHalts;
    private JTextField[] stationFields, arrival, departure, distance, day, platform, fare;
    private JLabel[] number;
    private String trainNo;
    private Integer noOfHalts;
    private Connect connection;
    private Panel details;
    private JScrollPane scroll;
    public AddTrainAdminNext(Connect connection,String trainNo, Integer noOfHalts) {
        this.trainNo = trainNo;
        this.noOfHalts = noOfHalts;
        this.connection = connection;
        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "ADD TRAIN DETAILS");

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(Color.BLACK);
        p1.setBounds(0, 0, 750, 45);
        add(p1);

        headLabel = new JLabel("Add " + noOfHalts + " Station Details for Train no. " + trainNo);
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(200, 10, 500, 30);
        p1.add(headLabel);

        l3 = new JLabel("Station Name   |");
        l3.setFont(new Font("Times new roman", Font.BOLD, 15));
        l3.setBounds(55, 50, 400, 32);
        add(l3);

        l4 = new JLabel("Arrival    |");
        l4.setFont(new Font("Times new roman", Font.BOLD, 15));
        l4.setBounds(175, 50, 400, 32);
        add(l4);

        l5 = new JLabel("Departure    |");
        l5.setFont(new Font("Times new roman", Font.BOLD, 15));
        l5.setBounds(260, 50, 400, 32);
        add(l5);

        l6 = new JLabel("Distance    |");
        l6.setFont(new Font("Times new roman", Font.BOLD, 15));
        l6.setBounds(370, 50, 400, 32);
        add(l6);

        l7 = new JLabel("Day    |");
        l7.setFont(new Font("Times new roman", Font.BOLD, 15));
        l7.setBounds(470, 50, 400, 32);
        add(l7);

        l8 = new JLabel("Platform    |");
        l8.setFont(new Font("Times new roman", Font.BOLD, 15));
        l8.setBounds(535, 50, 400, 32);
        add(l8);

        l9 = new JLabel("Fare");
        l9.setFont(new Font("Times new roman", Font.BOLD, 15));
        l9.setBounds(630, 50, 400, 32);
        add(l9);

        stationFields = new JTextField[noOfHalts];
        arrival = new JTextField[noOfHalts];
        departure = new JTextField[noOfHalts];
        distance = new JTextField[noOfHalts];
        platform = new JTextField[noOfHalts];
        fare = new JTextField[noOfHalts];
        day = new JTextField[noOfHalts];

        number = new JLabel[noOfHalts];

        details = new Panel();
        

        Integer i = 0, y1 = 10;

        for (i = 0; i < noOfHalts; i++) {
            String str = String.valueOf(i + 1);
            number[i] = new JLabel("S" + str + ".");
            number[i].setFont(new Font("Times new roman", Font.BOLD, 15));
            number[i].setBounds(10, y1 - 8, 400, 32);
            details.add(number[i]);

            Integer x1 = 45;

            stationFields[i] = new JTextField(50);
            stationFields[i].setBounds(x1, y1, 80, 20);
            stationFields[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(stationFields[i]);

            x1 = 145;
            arrival[i] = new JTextField(51);
            arrival[i].setBounds(x1, y1, 70, 20);
            arrival[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(arrival[i]);

            x1 = 245;
            departure[i] = new JTextField(50);
            departure[i].setBounds(x1, y1, 70, 20);
            departure[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(departure[i]);

            x1 = 340;
            distance[i] = new JTextField(50);
            distance[i].setBounds(x1, y1, 80, 20);
            distance[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(distance[i]);

            x1 = 440;
            day[i] = new JTextField(50);
            day[i].setBounds(x1, y1, 50, 20);
            day[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(day[i]);

            x1 = 505;
            platform[i] = new JTextField(50);
            platform[i].setBounds(x1, y1, 80, 20);
            platform[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(platform[i]);

            x1 = 600;
            fare[i] = new JTextField(50);
            fare[i].setBounds(x1, y1, 60, 20);
            fare[i].setFont(new Font("Times new roman", Font.BOLD, 14));
            details.add(fare[i]);
            y1 = y1 + 30;
            System.out.println(i + "\n");
        }
        details.setLayout(null);
        scroll = new JScrollPane(details);
        scroll.setBounds(20,90, 700, 600);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
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
    class Panel extends JPanel{
        @Override
        public Dimension getPreferredSize() {
            if(noOfHalts>20)
            return new Dimension(650,610+30*(noOfHalts-20));
            else return new Dimension(700,610);
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            AddTrainAdminNextInfo[] stationInfo = new AddTrainAdminNextInfo[noOfHalts];

            for (int i = 0; i < noOfHalts; i++) {
                String station = stationFields[i].getText();
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

                stationInfo[i] = new AddTrainAdminNextInfo(i + 1, station, arrival, departure, distance, day, platform,
                        fare);
            }

            if (ae.getSource() == next) {
                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(7);
                    os.writeUTF(trainNo);
                    int noOfHaltsx = noOfHalts.intValue();
                    os.writeInt(noOfHaltsx);
                    for (int i = 0; i < noOfHalts; i++) {
                        os.writeObject(stationInfo[i]);
                    }
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                String s = (String) oi.readUTF();
                if (s.equals("ok")) {
                    JOptionPane.showMessageDialog(null, "Train Basic Details Added. Complete the next form.");
                    new AdminHome(connection).setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Request Not Processed");
                    new AdminHome(connection).setVisible(true);
                    setVisible(false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
