package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class HomePage extends JFrame implements ActionListener {
    JPanel p1, p2;
    JButton l1, b1, b2, b3, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20;
    JTextField tf1;
    JPasswordField pf2;

    HomePage() {

        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        // for (int i=0; i!=w; i++) pad +=" ";
        pad = String.format("%" + w * 4 + "s", pad);
        setTitle(pad + "HOME PAGE");

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(Color.BLACK);
        p1.setBounds(0, 0, 750, 45);
        add(p1);

        // ImageIcon i1 = new
        // ImageIcon(ClassLoader.getSystemResource("Musafir/icons/backArrow.png"));
        // Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        // ImageIcon i3 = new ImageIcon(i2);
        // l1 = new JButton(i3);
        // l1.setBackground(Color.BLACK);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        // l1.setBorder(emptyBorder);
        // l1.setBounds(5, 8, 30, 30);
        // p1.add(l1);

        JLabel l2 = new JLabel("HOME PAGE");
        l2.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        l2.setForeground(Color.WHITE);
        l2.setBounds(280, 10, 200, 30);
        p1.add(l2);

        l3 = new JButton("Log Out");
        l3.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l3.setForeground(Color.WHITE);
        l3.setBackground(Color.BLACK);
        l3.setBorder(emptyBorder);
        l3.setBounds(640, 12, 100, 24);
        p1.add(l3);

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/planMyJourney.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        l4 = new JButton(i6);
        l4.setBounds(80, 100, 50, 50);
        add(l4);

        JLabel l5 = new JLabel("Plan My Journey");
        l5.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l5.setForeground(Color.BLACK);
        l5.setBounds(40, 160, 200, 24);
        add(l5);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/myBookings.png"));
        Image i8 = i7.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        l6 = new JButton(i9);
        l6.setBounds(345, 100, 50, 50);
        add(l6);

        JLabel l7 = new JLabel("My Bookings");
        l7.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l7.setForeground(Color.BLACK);
        l7.setBounds(310, 160, 200, 24);
        add(l7);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/pnrEnquiry.png"));
        Image i11 = i10.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        l8 = new JButton(i12);
        l8.setBounds(550, 100, 50, 50);
        add(l8);

        JLabel l9 = new JLabel("PNR Enquiry");
        l9.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l9.setForeground(Color.BLACK);
        l9.setBounds(530, 160, 200, 24);
        add(l9);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/cancelTicket.png"));
        Image i14 = i13.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        l10 = new JButton(i15);
        l10.setBounds(80, 230, 50, 50);
        add(l10);

        JLabel l11 = new JLabel("Cancel Ticket");
        l11.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l11.setForeground(Color.BLACK);
        l11.setBounds(50, 290, 200, 24);
        add(l11);

        ImageIcon i16 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/refundHistory.png"));
        Image i17 = i16.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i18 = new ImageIcon(i17);
        l12 = new JButton(i18);
        l12.setBounds(345, 230, 50, 50);
        add(l12);

        JLabel l13 = new JLabel("Refund History");
        l13.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l13.setForeground(Color.BLACK);
        l13.setBounds(310, 290, 200, 24);
        add(l13);

        ImageIcon i19 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/cancelled.png"));
        Image i20 = i19.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i21 = new ImageIcon(i20);
        l14 = new JButton(i21);
        l14.setBounds(550, 230, 50, 50);
        add(l14);

        JLabel l15 = new JLabel("Cancelled Trains");
        l15.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l15.setForeground(Color.BLACK);
        l15.setBounds(520, 290, 200, 24);
        add(l15);

        ImageIcon i22 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/reroute.png"));
        Image i23 = i22.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i24 = new ImageIcon(i23);
        l16 = new JButton(i24);
        l16.setBounds(80, 360, 50, 50);
        add(l16);

        JLabel l17 = new JLabel("Rerouted Trains");
        l17.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l17.setForeground(Color.BLACK);
        l17.setBounds(50, 420, 200, 24);
        add(l17);

        p2 = new JPanel();
        p2.setLayout(null);
        p2.setBackground(Color.BLACK);
        p2.setBounds(0, 520, 750, 10);
        add(p2);

        ImageIcon i25 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/meal.png"));
        Image i26 = i25.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i27 = new ImageIcon(i26);
        l18 = new JButton(i27);
        l18.setBounds(205, 580, 50, 50);
        add(l18);

        JLabel l19 = new JLabel("Book A Meal!!!");
        l19.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l19.setForeground(Color.BLACK);
        l19.setBounds(175, 640, 210, 24);
        add(l19);

        ImageIcon i28 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/bot.png"));
        Image i29 = i28.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i30 = new ImageIcon(i29);
        l20 = new JButton(i30);
        l20.setBounds(470, 580, 50, 50);
        add(l20);

        JLabel l21 = new JLabel("Have A Query???");
        l21.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l21.setForeground(Color.BLACK);
        l21.setBounds(440, 640, 210, 24);
        add(l21);

        // l1.addActionListener(this);
        l3.addActionListener(this);
        l4.addActionListener(this);
        l6.addActionListener(this);
        l8.addActionListener(this);
        l10.addActionListener(this);
        l12.addActionListener(this);
        l14.addActionListener(this);
        l16.addActionListener(this);
        l18.addActionListener(this);
        l20.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(760, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == l4) {
                new PlanMyJourney().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == l3) {
                new Login().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == l6) {
                new PlanMyJourney().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == l8) {
                new PlanMyJourney().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == l10) {
                new PlanMyJourney().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == l12) {
                new PlanMyJourney().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == l14) {
                new PlanMyJourney().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == l16) {
                new PlanMyJourney().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == l18) {
                new PlanMyJourney().setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == l20) {
                new PlanMyJourney().setVisible(true);
                setVisible(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HomePage().setVisible(true);
    }
}
