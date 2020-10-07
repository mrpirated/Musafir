package LockDownTraveller;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class PlanMyJourney extends JFrame {
    JPanel p1, p2;
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14;
    JTextField tf1;
    JPasswordField pf2;
    JButton b1, b2, b3;

    PlanMyJourney() {

        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("PLAN MY JOURNEY");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        // for (int i=0; i!=w; i++) pad +=" ";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "PLAN MY JOURNEY");

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(Color.BLACK);
        p1.setBounds(0, 0, 750, 45);
        add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("LockDownTraveller/icons/backArrow.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(5, 8, 30, 30);
        p1.add(l1);

        JLabel l2 = new JLabel("PLAN MY JOURNEY");
        l2.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        l2.setForeground(Color.WHITE);
        l2.setBounds(220, 10, 400, 30);
        p1.add(l2);

        JLabel l3 = new JLabel("From");
        l3.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l3.setForeground(Color.BLACK);
        l3.setBounds(150, 70, 100, 24);
        add(l3);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PlanMyJourney().setVisible(true);
    }
}
