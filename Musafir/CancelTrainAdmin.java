package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.jdesktop.swingx.JXDatePicker;
import java.text.ParseException;
import java.sql.*;
import java.util.*;

public class CancelTrainAdmin extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, infoLabel, startDate, endDate, trainNo;
    private JPanel p1, p2, panel, startDatePanel, endDatePanel;
    private JButton back, submit;
    private JTextField pnrText;
    private JComboBox tf1;
    private JXDatePicker picker, picker1;
    private Connect connection;
    private Vector<String> trainList;

    public CancelTrainAdmin(Connect connection, Vector<String> trainList) {
        this.connection = connection;
        this.trainList = trainList;
        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "CANCEL TRAIN");

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

        headLabel = new JLabel("CANCEL TRAIN");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(250, 10, 400, 30);
        p1.add(headLabel);

        infoLabel = new JLabel("Fill Following Information");
        infoLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 27));
        infoLabel.setForeground(Color.BLACK);
        infoLabel.setBounds(220, 90, 400, 30);
        add(infoLabel);

        trainNo = new JLabel("Train No:");
        trainNo.setFont(new Font("Times new roman", Font.BOLD, 20));
        trainNo.setBounds(200, 180, 150, 32);
        add(trainNo);

        tf1 = new JComboBox(trainList);
        tf1.setFont(new Font("Times new roman", Font.BOLD, 14));
        tf1.setBounds(300, 180, 300, 30);
        add(tf1);

        startDate = new JLabel("From:");
        startDate.setFont(new Font("Times new roman", Font.BOLD, 20));
        startDate.setBounds(70, 250, 200, 32);
        add(startDate);

        startDatePanel = new JPanel();
        picker = new JXDatePicker();
        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        startDatePanel.setBackground(Color.white);
        startDatePanel.setBounds(40, 290, 150, 30);
        startDatePanel.add(picker);
        add(startDatePanel);

        endDate = new JLabel("To:");
        endDate.setFont(new Font("Times new roman", Font.BOLD, 20));
        endDate.setBounds(500, 250, 200, 32);
        add(endDate);

        endDatePanel = new JPanel();
        picker1 = new JXDatePicker();
        picker1.setDate(Calendar.getInstance().getTime());
        picker1.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        endDatePanel.setBackground(Color.white);
        endDatePanel.setBounds(470, 290, 150, 30);
        endDatePanel.add(picker1);
        add(endDatePanel);

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        submit.setForeground(Color.WHITE);
        submit.setBorder(emptyBorder);
        submit.setBounds(300, 500, 100, 30);
        add(submit);

        back.addActionListener(this);
        submit.addActionListener(this);

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
            } else if (ae.getSource() == submit) {
                String trainNo = (String) tf1.getSelectedItem();
                java.util.Date dateFrom = picker.getDate();
                java.util.Date dateTo = picker1.getDate();
                String dFrom = new SimpleDateFormat("yyyy-MM-dd").format(dateFrom);
                String dTo = new SimpleDateFormat("yyyy-MM-dd").format(dateTo);
                java.sql.Date dateFromSend = java.sql.Date.valueOf(dFrom);
                java.sql.Date dateToSend = java.sql.Date.valueOf(dTo);
                System.out.println(dateFromSend);
                System.out.println(dateToSend);
                CancelTrainAdminInfo cancelRequest = new CancelTrainAdminInfo(trainNo, dateFromSend, dateToSend);

                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(8);
                    os.writeObject(cancelRequest);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                String s = (String) oi.readUTF();
                if (s.equals("ok")) {
                    JOptionPane.showMessageDialog(null, "Train Cancelled Successfully.");
                    new AdminHome(connection).setVisible(true);
                    setVisible(false);
                } else {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
