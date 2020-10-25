package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;

public class CancelTicket extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, l2, l3, l4;
    private JPanel p1, p2, panel;
    private JButton back, submit, b1;
    private JTextField pnrText, tf1, tf2;
    private JPasswordField pf2;
    private String name, Username;
    private Connect connection;
    public CancelTicket(Connect connection,String name, String Username) {
        this.name = name;
        this.Username = Username;
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
        setTitle(pad + "CANCEL TICKET");

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

        headLabel = new JLabel("CANCEL TICKET");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(250, 10, 400, 30);
        p1.add(headLabel);

        l2 = new JLabel("USER ID:");
        l2.setFont(new Font("Times new roman", Font.BOLD, 28));
        l2.setBounds(100, 100, 150, 32);
        add(l2);

        l3 = new JLabel("PASSWORD:");
        l3.setFont(new Font("Times new roman", Font.BOLD, 28));
        l3.setBounds(100, 250, 200, 32);
        add(l3);

        l4 = new JLabel("PNR NO.:");
        l4.setFont(new Font("Times new roman", Font.BOLD, 28));
        l4.setBounds(100, 400, 150, 32);
        add(l4);

        tf1 = new JTextField(15);
        tf1.setFont(new Font("Times new roman", Font.BOLD, 14));
        tf1.setBounds(300, 100, 230, 30);
        add(tf1);

        pf2 = new JPasswordField(15);
        pf2.setFont(new Font("Times new roman", Font.BOLD, 14));
        pf2.setBounds(300, 250, 230, 30);
        add(pf2);

        tf2 = new JTextField(15);
        tf2.setFont(new Font("Times new roman", Font.BOLD, 14));
        tf2.setBounds(300, 400, 230, 30);
        add(tf2);

        b1 = new JButton("SUBMIT");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Times new roman", Font.BOLD, 14));
        b1.setBounds(300, 500, 200, 30);
        add(b1);

        back.addActionListener(this);

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
                new HomePage(connection,name, Username).setVisible(true);
                setVisible(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
