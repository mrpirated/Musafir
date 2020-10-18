package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class AdminHome extends JFrame {

    JLabel passenger, home, train, book, cancel, add, reroute, l10, l11, l12, l13, l14;
    JButton passengerbt, trainbt, bookbt,cancelbt,addbt,reroutebt;
    ImageIcon i1,i3;
    Image i2;

    AdminHome() {

        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w *2.5 + "s", pad);
        setTitle(pad + "ADMIN HOME PAGE");

        home = new JLabel("ADMIN HOME PAGE");
        home.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        home.setBackground(Color.WHITE);
        home.setBounds(220, 10, 400, 30);
        add(home);
        
        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/passengers.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        passengerbt = new JButton(i3);
        passengerbt.setBounds(75, 75, 100, 100);
        add(passengerbt);
        

        passenger = new JLabel("Passengers Information");
        passenger.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        passenger.setForeground(Color.BLACK);
        passenger.setBounds(35, 180, 200, 24);
        add(passenger);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/trainicon1.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        trainbt = new JButton(i3);
        trainbt.setBounds(315, 75, 100, 100);
        add(trainbt);

        train = new JLabel("Trains");
        train.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        train.setForeground(Color.BLACK);
        train.setBounds(340, 180, 200, 24);
        add(train);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/ticket.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        bookbt = new JButton(i3);
        bookbt.setBounds(555, 75, 100, 100);
        add(bookbt);

        book = new JLabel("Book Tickets");
        book.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        book.setForeground(Color.BLACK);
        book.setBounds(550, 180, 200, 24);
        add(book);

        

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/cancel.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        cancelbt = new JButton(i3);
        cancelbt.setBounds(75, 250, 100, 100);
        add(cancelbt);

        cancel = new JLabel("Cancel Trains");
        cancel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        cancel.setForeground(Color.BLACK);
        cancel.setBounds(75, 355, 200, 24);
        add(cancel);


        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/add.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        addbt = new JButton(i3);
        addbt.setBounds(315, 250, 100, 100);
        add(addbt);

        add = new JLabel("Add Trains");
        add.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        add.setForeground(Color.BLACK);
        add.setBounds(315, 355, 200, 24);
        add(add);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/reroute.png"));
        i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        reroutebt = new JButton(i3);
        reroutebt.setBounds(555, 250, 100, 100);
        add(reroutebt);

        reroute = new JLabel("Reroute Trains");
        reroute.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        reroute.setForeground(Color.BLACK);
        reroute.setBounds(545, 355, 200, 24);
        add(reroute);



        

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AdminHome().setVisible(true);
    }
}