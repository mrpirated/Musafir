package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class BasicTicketDetails extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, l1;
    private JPanel p1, p2, panel;
    private JButton back, submit;
    private JTextField pnrText;
    private String Username, trainNo, type, name, src, dest;
    private Date date;
    private JComboBox cb;
    private Connect connection;
    public BasicTicketDetails(Connect connection,String name, String Username, String trainNo, String type, String src, String dest,
            Date date) {
        this.name = name;
        this.Username = Username;
        this.trainNo = trainNo;
        this.type = type;
        this.src = src;
        this.dest = dest;
        this.date = date;
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
        setTitle(pad + "Basic Ticket Details");

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

        headLabel = new JLabel("Basic Ticket Details");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(250, 10, 400, 30);
        p1.add(headLabel);

        l1 = new JLabel("No. of Passenger:");
        l1.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        l1.setForeground(Color.BLACK);
        l1.setBounds(80, 100, 400, 30);
        add(l1);

        String number[] = { "1", "2", "3", "4", "5", "6" };
        cb = new JComboBox(number);
        cb.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 25));
        cb.setBounds(280, 100, 90, 30);
        add(cb);

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        submit.setForeground(Color.WHITE);
        submit.setBorder(emptyBorder);
        submit.setBounds(300, 300, 100, 30);
        add(submit);

        back.addActionListener(this);
        submit.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 400);
        setLocation(400, 50);
        setVisible(true);
    }

    
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == back) {
                new HomePage(connection,name, Username).setVisible(true);
                setVisible(false);
            }
            if (ae.getSource() == submit) {
                String str = (String) cb.getSelectedItem();
                Integer noOfPassenger = Integer.parseInt(str);
                new PassengerTicketDetails(connection,name, Username, trainNo, type, src, dest, date, noOfPassenger)
                        .setVisible(true);
                setVisible(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
