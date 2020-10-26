package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;

public class TrainInfoAdmin extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, trainNoLabel;
    private JPanel p1, p2, panel;
    private JButton back, submit, b1;
    private JTextField pnrText, tf1;
    private Connect connection;

    public TrainInfoAdmin(Connect connection) {
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
        setTitle(pad + "TRAIN INFORMATION");

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

        headLabel = new JLabel("TRAIN INFORMATION");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(210, 10, 600, 30);
        p1.add(headLabel);

        trainNoLabel = new JLabel("Enter Train No:");
        trainNoLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        trainNoLabel.setForeground(Color.BLACK);
        trainNoLabel.setBounds(100, 60, 600, 30);
        add(trainNoLabel);

        tf1 = new JTextField(15);
        tf1.setFont(new Font("Times new roman", Font.PLAIN, 20));
        tf1.setBounds(300, 60, 230, 30);
        add(tf1);

        b1 = new JButton("Get Details");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Times new roman", Font.BOLD, 14));
        b1.setBounds(300, 105, 150, 30);
        add(b1);

        back.addActionListener(this);
        b1.addActionListener(this);

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
            } else if (ae.getSource() == b1) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
