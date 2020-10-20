package Musafir;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class HomePage extends JFrame implements ActionListener {
    
    JLabel home,plan,booking,pnr,cancel,refund,traincancel,reroute,meal,query;
    JButton planbt, bookingbt, pnrbt, cancelbt, refundbt, traincancelbt, reroutebt, mealbt, querybt;
    
    ImageIcon i1,i3;
    Image i2;

    HomePage(String name) {

        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w *2.5 + "s", pad);
        setTitle(pad + "HOME PAGE");


        home = new JLabel("Welcome "+name);
        home.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        home.setBackground(Color.WHITE);
        home.setBounds(220, 10, 400, 30);
        add(home);
       
        

       
        
        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/planMyJourney.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        planbt = new JButton(i3);
        planbt.setBounds(75, 75, 100, 100);
        add(planbt);

        plan = new JLabel("Plan My Journey");
        plan.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        plan.setForeground(Color.BLACK);
        plan.setBounds(50, 180, 200, 24);
        add(plan);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/myBookings.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        bookingbt = new JButton(i3);
        bookingbt.setBounds(315, 75, 100, 100);
        add(bookingbt);


        booking = new JLabel("My Bookings");
        booking.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        booking.setForeground(Color.BLACK);
        booking.setBounds(340, 180, 200, 24);
        add(booking);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/pnrEnquiry.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        pnrbt = new JButton(i3);
        pnrbt.setBounds(555, 75, 100, 100);
        add(pnrbt);

        pnr = new JLabel("PNR Enquiry");
        pnr.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        pnr.setForeground(Color.BLACK);
        pnr.setBounds(550, 180, 200, 24);
        add(pnr);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/cancelTicket.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        cancelbt = new JButton(i3);
        cancelbt.setBounds(75, 250, 100, 100);
        add(cancelbt);

        cancel = new JLabel("Cancel Ticket");
        cancel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        cancel.setForeground(Color.BLACK);
        cancel.setBounds(75, 355, 200, 24);
        add(cancel);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/refund.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        refundbt = new JButton(i3);
        refundbt.setBounds(315, 250, 100, 100);
        add(refundbt);


        refund = new JLabel("Refund");
        refund.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        refund.setForeground(Color.BLACK);
        refund.setBounds(315, 355, 200, 24);
        add(refund);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/cancel.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        traincancelbt=new JButton(i3);
        traincancelbt.setBounds(555, 250, 100, 100);
        add(traincancelbt);

        traincancel = new JLabel("Cancelled Trains");
        traincancel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        traincancel.setForeground(Color.BLACK);
        traincancel.setBounds(545, 355, 200, 24);
        add(traincancel);

        

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/reroute.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        reroutebt=new JButton(i3);
        reroutebt.setBounds(75, 425, 100, 100);
        add(reroutebt);

        reroute = new JLabel("Rerouted Trains");
        reroute.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        reroute.setForeground(Color.BLACK);
        reroute.setBounds(75, 530, 200, 24);
        add(reroute);


        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/meal.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        mealbt = new JButton(i3);
        mealbt.setBounds(315, 425, 100, 100);
        add(mealbt);


        meal = new JLabel("Book a Meal");
        meal.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        meal.setForeground(Color.BLACK);
        meal.setBounds(315, 530, 200, 24);
        add(meal);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/bot.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        querybt=new JButton(i3);
        querybt.setBounds(555, 425, 100, 100);
        add(querybt);

        query = new JLabel("Have a Query?");
        query.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        query.setForeground(Color.BLACK);
        query.setBounds(545, 530, 200, 24);
        add(query);
        
        planbt.addActionListener(this);
        bookingbt.addActionListener(this);
        pnrbt.addActionListener(this);
        refundbt.addActionListener(this);
        cancelbt.addActionListener(this);
        traincancelbt.addActionListener(this);
        reroutebt.addActionListener(this);
        mealbt.addActionListener(this);
        querybt.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == planbt) {
                new PlanMyJourney().setVisible(true);
                setVisible(false);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HomePage("Deepesh").setVisible(true);
    }

    
}
