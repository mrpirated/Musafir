package Musafir;

import javax.swing.*;
import javax.swing.border.*;

import MusafirServer.Conn;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class HomePage extends JFrame implements ActionListener {

    private String name;
    private int userid;
    private JLabel home, plan, booking, pnr, cancel, refund, traincancel, reroute, meal, query, train;
    private JButton planbt, bookingbt, pnrbt, cancelbt, refundbt, traincancelbt, reroutebt, mealbt, querybt, logout,
            trainbt;
    private JPanel p2;
    private ImageIcon i1, i3;
    private Image i2;
    private Connect connection;
    private JScrollPane scroll;

    HomePage(Connect connection, String name,int userid) {
        this.connection = connection;
        this.name = name;
        this.userid = userid;
        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "HOME PAGE");

        home = new JLabel("Welcome " + name);
        home.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        home.setBackground(Color.WHITE);
        home.setBounds(240, 10, 400, 30);
        add(home);

        logout = new JButton("LogOut");
        Border emptyBorder = BorderFactory.createEmptyBorder();
        logout.setBorder(emptyBorder);
        logout.setBackground(Color.BLACK);
        logout.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        logout.setForeground(Color.WHITE);
        logout.setBounds(600, 10, 100, 30);
        add(logout);

        p2 = new Panel();
        p2.setLayout(null);

        scroll = new JScrollPane(p2);
        scroll.setBounds(15, 60, 720, 650);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scroll);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/planMyJourney.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        planbt = new JButton(i3);
        planbt.setBounds(75, 75, 100, 100);
        p2.add(planbt);

        plan = new JLabel("Plan My Journey");
        plan.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        plan.setForeground(Color.BLACK);
        plan.setBounds(50, 180, 200, 24);
        p2.add(plan);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/myBookings.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        bookingbt = new JButton(i3);
        bookingbt.setBounds(315, 75, 100, 100);
        p2.add(bookingbt);

        booking = new JLabel("My Bookings");
        booking.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        booking.setForeground(Color.BLACK);
        booking.setBounds(340, 180, 200, 24);
        p2.add(booking);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/pnrEnquiry.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        pnrbt = new JButton(i3);
        pnrbt.setBounds(555, 75, 100, 100);
        p2.add(pnrbt);

        pnr = new JLabel("PNR Enquiry");
        pnr.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        pnr.setForeground(Color.BLACK);
        pnr.setBounds(550, 180, 200, 24);
        p2.add(pnr);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/cancelTicket.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        cancelbt = new JButton(i3);
        cancelbt.setBounds(75, 250, 100, 100);
        p2.add(cancelbt);

        cancel = new JLabel("Cancel Ticket");
        cancel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        cancel.setForeground(Color.BLACK);
        cancel.setBounds(75, 355, 200, 24);
        p2.add(cancel);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/refund.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        refundbt = new JButton(i3);
        refundbt.setBounds(315, 250, 100, 100);
        p2.add(refundbt);

        refund = new JLabel("Refund");
        refund.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        refund.setForeground(Color.BLACK);
        refund.setBounds(315, 355, 200, 24);
        p2.add(refund);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/cancel.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        traincancelbt = new JButton(i3);
        traincancelbt.setBounds(555, 250, 100, 100);
        p2.add(traincancelbt);

        traincancel = new JLabel("Cancelled Trains");
        traincancel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        traincancel.setForeground(Color.BLACK);
        traincancel.setBounds(545, 355, 200, 24);
        p2.add(traincancel);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/reroute.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        reroutebt = new JButton(i3);
        reroutebt.setBounds(75, 425, 100, 100);
        p2.add(reroutebt);

        reroute = new JLabel("Rerouted Trains");
        reroute.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        reroute.setForeground(Color.BLACK);
        reroute.setBounds(75, 530, 200, 24);
        p2.add(reroute);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/meal.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        mealbt = new JButton(i3);
        mealbt.setBounds(315, 425, 100, 100);
        p2.add(mealbt);

        meal = new JLabel("Book a Meal");
        meal.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        meal.setForeground(Color.BLACK);
        meal.setBounds(315, 530, 200, 24);
        p2.add(meal);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/bot.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        querybt = new JButton(i3);
        querybt.setBounds(555, 425, 100, 100);
        p2.add(querybt);

        query = new JLabel("Have a Query?");
        query.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        query.setForeground(Color.BLACK);
        query.setBounds(545, 530, 200, 24);
        p2.add(query);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/trainicon1.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        trainbt = new JButton(i3);
        trainbt.setBounds(75, 600, 100, 100);
        p2.add(trainbt);

        train = new JLabel("Trains Info");
        train.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        train.setForeground(Color.BLACK);
        train.setBounds(75, 705, 200, 24);
        p2.add(train);

        planbt.addActionListener(this);
        bookingbt.addActionListener(this);
        pnrbt.addActionListener(this);
        refundbt.addActionListener(this);
        cancelbt.addActionListener(this);
        traincancelbt.addActionListener(this);
        reroutebt.addActionListener(this);
        mealbt.addActionListener(this);
        querybt.addActionListener(this);
        logout.addActionListener(this);
        trainbt.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    class Panel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(700, 1000);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == planbt) {
                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(3);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                String[][] cities = (String[][]) oi.readObject();
                new PlanMyJourney(connection, name, cities, userid).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == logout) {
                new Login(connection).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == pnrbt) {
                new PnrEnquiry(connection, name, userid).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == bookingbt) {
                new MyBookings(connection, name, userid).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == cancelbt) {
                new CancelTicket(connection, name, userid).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == refundbt) {
                new Refund(connection, name, userid).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == traincancelbt) {
                new CancelledTrains(connection, name, userid).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == reroutebt) {
                new ReroutedTrains(connection, name, userid).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == mealbt) {
                try {
                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(16);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                Vector<String> pnrList = (Vector<String>) oi.readObject();
                new BookAMeal(connection, name, userid, pnrList).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == trainbt) {
                try {
                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(12);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                Vector<String> trainList = (Vector<String>) oi.readObject();
                new TrainInfoClient(connection, name, userid, trainList).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == querybt) {
                new BotClient(connection, name, Username).setVisible(true);
                setVisible(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
