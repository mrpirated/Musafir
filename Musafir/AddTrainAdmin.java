package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;

public class AddTrainAdmin extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, l2, l3, l4, l5, l6, l7, l8, l9;
    private JPanel p1, p2, panel;
    private JButton back, next;
    private JComboBox src, dest;
    private JTextField pnrText, trainNo, trainName, tsSlr, tsAc, runningDays, noOfStations;
    private Connect connection;
    private String[][] cities;

    public AddTrainAdmin(Connect connection, String[][] cities) {
        this.connection = connection;
        this.cities = cities;
        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "ADD TRAIN");

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(Color.BLACK);
        p1.setBounds(0, 0, 750, 45);
        add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/backArrow.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        back = new JButton(i3);
        back.setBackground(Color.BLACK);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        back.setBorder(emptyBorder);
        back.setBounds(5, 8, 30, 30);
        p1.add(back);

        headLabel = new JLabel("ADD TRAIN");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(270, 10, 600, 30);
        p1.add(headLabel);

        l2 = new JLabel("Train No:");
        l2.setFont(new Font("Times new roman", Font.BOLD, 28));
        l2.setBounds(100, 110, 400, 32);
        add(l2);

        trainNo = new JTextField(50);
        trainNo.setBounds(450, 110, 230, 30);
        trainNo.setFont(new Font("Times new roman", Font.BOLD, 14));
        add(trainNo);

        l3 = new JLabel("Train Name:");
        l3.setFont(new Font("Times new roman", Font.BOLD, 28));
        l3.setBounds(100, 170, 400, 32);
        add(l3);

        trainName = new JTextField(50);
        trainName.setBounds(450, 170, 230, 30);
        trainName.setFont(new Font("Times new roman", Font.BOLD, 14));
        add(trainName);

        l4 = new JLabel("No of Sleeper Coaches:");
        l4.setFont(new Font("Times new roman", Font.BOLD, 28));
        l4.setBounds(100, 470, 400, 32);
        add(l4);

        tsSlr = new JTextField(50);
        tsSlr.setBounds(450, 470, 230, 30);
        tsSlr.setFont(new Font("Times new roman", Font.BOLD, 14));
        add(tsSlr);

        l5 = new JLabel("No of AC Coaches:");
        l5.setFont(new Font("Times new roman", Font.BOLD, 28));
        l5.setBounds(100, 530, 400, 32);
        add(l5);

        tsAc = new JTextField(50);
        tsAc.setBounds(450, 530, 230, 30);
        tsAc.setFont(new Font("Times new roman", Font.BOLD, 14));
        add(tsAc);

        l6 = new JLabel("Source Station:");
        l6.setFont(new Font("Times new roman", Font.BOLD, 28));
        l6.setBounds(100, 230, 400, 32);
        add(l6);

        src = new JComboBox(cities[0]);
        src.setBounds(450, 230, 230, 30);
        src.setFont(new Font("Times new roman", Font.BOLD, 14));
        add(src);

        l7 = new JLabel("Destination Station:");
        l7.setFont(new Font("Times new roman", Font.BOLD, 28));
        l7.setBounds(100, 290, 400, 32);
        add(l7);

        dest = new JComboBox(cities[0]);
        dest.setBounds(450, 290, 230, 30);
        dest.setFont(new Font("Times new roman", Font.BOLD, 14));
        add(dest);

        l8 = new JLabel("Running Days [Mon-Sun]:");
        l8.setFont(new Font("Times new roman", Font.BOLD, 28));
        l8.setBounds(100, 350, 400, 32);
        add(l8);

        runningDays = new JTextField(50);
        runningDays.setBounds(450, 350, 230, 30);
        runningDays.setFont(new Font("Times new roman", Font.BOLD, 14));
        add(runningDays);

        l9 = new JLabel("Total Halt Stations:");
        l9.setFont(new Font("Times new roman", Font.BOLD, 28));
        l9.setBounds(100, 410, 400, 32);
        add(l9);

        noOfStations = new JTextField(50);
        noOfStations.setBounds(450, 410, 230, 30);
        noOfStations.setFont(new Font("Times new roman", Font.BOLD, 14));
        add(noOfStations);

        next = new JButton("Next");
        next.setBackground(Color.BLACK);
        next.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        next.setForeground(Color.WHITE);
        next.setBorder(emptyBorder);
        next.setBounds(300, 600, 100, 30);
        add(next);

        back.addActionListener(this);
        next.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == back) {
                new AdminHome(connection).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == next) {
                String trainNox = trainNo.getText();
                String trainNamex = trainName.getText();
                String srcx = (String) src.getSelectedItem();
                String destx = (String) dest.getSelectedItem();
                String runningDaysx = runningDays.getText();
                Integer noOfHaltx = Integer.parseInt(noOfStations.getText());
                Integer ts_slr = Integer.parseInt(tsSlr.getText()) * 72;
                Integer ts_ac = Integer.parseInt(tsAc.getText()) * 50;
                AddTrainAdminInfo addTrain = new AddTrainAdminInfo(trainNox, trainNamex, srcx, destx, runningDaysx,
                        noOfHaltx, ts_slr, ts_ac);

                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(6);
                    os.writeObject(addTrain);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                String s = (String) oi.readUTF();
                if (s.equals("ok")) {
                    JOptionPane.showMessageDialog(null, "Train Basic Details Added. Complete the next form.");
                    new AddTrainAdminNext(connection, trainNox, noOfHaltx, cities).setVisible(true);
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
