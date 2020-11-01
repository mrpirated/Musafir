package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;

public class DisplayTicket extends JFrame {
    private JLabel PNR,seat,berth,name,trainno,trainname,quota,date,fare,musafir,dest,src;
    public DisplayTicket(/*BookedTicket bookedTicket*/){
        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "Passenger Details");

        musafir = new JLabel("MUSAFIR e-Ticket");
        musafir.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 40));
        musafir.setForeground(Color.BLACK);
        musafir.setBounds(200, 20, 500, 30);
        add(musafir);

        trainno = new JLabel("Train No: ");
        trainno.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        trainno.setForeground(Color.BLACK);
        trainno.setBounds(70, 100, 150, 30);
        add(trainno);

        trainno = new JLabel("12487");
        trainno.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        trainno.setForeground(Color.BLACK);
        trainno.setBounds(190, 100, 120, 30);
        add(trainno);

        trainname = new JLabel("Train Name: ");
        trainname.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        trainname.setForeground(Color.BLACK);
        trainname.setBounds(370, 100, 200, 30);
        add(trainname);

        trainname = new JLabel("Mumbai Rajdhani");
        trainname.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        trainname.setForeground(Color.BLACK);
        trainname.setBounds(540, 100, 200, 30);
        add(trainname);

        PNR = new JLabel("PNR :");
        PNR.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        PNR.setForeground(Color.BLACK);
        PNR.setBounds(70, 140, 200, 30);
        add(PNR);

        PNR = new JLabel("1547896324");
        PNR.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        PNR.setForeground(Color.BLACK);
        PNR.setBounds(140, 140, 200, 30);
        add(PNR);

        quota = new JLabel("Quota :");
        quota.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        quota.setForeground(Color.BLACK);
        quota.setBounds(370, 140, 200, 30);
        add(quota);

        quota = new JLabel("General");
        quota.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        quota.setForeground(Color.BLACK);
        quota.setBounds(470, 140, 200, 30);
        add(quota);

        fare = new JLabel("Fare :");
        fare.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        fare.setForeground(Color.BLACK);
        fare.setBounds(70, 180, 200, 30);
        add(fare);

        fare = new JLabel("1248.21");
        fare.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        fare.setForeground(Color.BLACK);
        fare.setBounds(150, 180, 200, 30);
        add(fare);

        date = new JLabel("Date Of Journey:");
        date.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        date.setForeground(Color.BLACK);
        date.setBounds(370, 180, 200, 30);
        add(date);

        date = new JLabel("23/12/2001");
        date.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN,22));
        date.setForeground(Color.BLACK);
        date.setBounds(570, 180, 200, 30);
        add(date);

        src = new JLabel("Source: ");
        src.setFont(new Font("TIMES NEW ROMAN", Font.BOLD,22));
        src.setForeground(Color.BLACK);
        src.setBounds(70, 220, 200, 30);
        add(src);

        src = new JLabel("LokmanyaTilak T ");
        src.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN,22));
        src.setForeground(Color.BLACK);
        src.setBounds(150, 220, 200, 30);
        add(src);

        src = new JLabel("Departure: ");
        src.setFont(new Font("TIMES NEW ROMAN", Font.BOLD,22));
        src.setForeground(Color.BLACK);
        src.setBounds(70, 260, 200, 30);
        add(src);

        src = new JLabel("08:56 on 24/12/2001");
        src.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN,22));
        src.setForeground(Color.BLACK);
        src.setBounds(180, 260, 200, 30);
        add(src);

        dest = new JLabel("Destination: ");
        dest.setFont(new Font("TIMES NEW ROMAN", Font.BOLD,22));
        dest.setForeground(Color.BLACK);
        dest.setBounds(370, 220, 200, 30);
        add(dest);

        dest = new JLabel("Chennai Central ");
        dest.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN,22));
        dest.setForeground(Color.BLACK);
        dest.setBounds(500, 220, 200, 30);
        add(dest);

        dest = new JLabel("Arrival: ");
        dest.setFont(new Font("TIMES NEW ROMAN", Font.BOLD,22));
        dest.setForeground(Color.BLACK);
        dest.setBounds(370, 260, 200, 30);
        add(dest);

        dest = new JLabel("08:56 on 24/12/2001");
        dest.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN,22));
        dest.setForeground(Color.BLACK);
        dest.setBounds(460, 260, 200, 30);
        add(dest);

        


        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }
    public static void main(String[] args) {
        new DisplayTicket().setVisible(true);
    }
}
