package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class PnrEnquiry extends JFrame implements ActionListener {

    JLabel headLabel, pnrLabel;
    JPanel p1, p2, panel;
    JButton back, submit;
    JTextField pnrText;
    private String name;

    public PnrEnquiry(String name) {
        this.name = name;
        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "PNR ENQUIRY");

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

        headLabel = new JLabel("PNR ENQUIRY");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(250, 10, 400, 30);
        p1.add(headLabel);

        pnrLabel = new JLabel("Enter 10 Digit PNR");
        pnrLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        pnrLabel.setBackground(Color.WHITE);
        pnrLabel.setBounds(270, 200, 400, 30);
        add(pnrLabel);

        pnrText = new JTextField(10);
        pnrText.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        pnrText.setForeground(Color.BLACK);
        pnrText.setBounds(280, 250, 150, 30);
        add(pnrText);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        submit = new JButton("Get Details");
        submit.setBackground(Color.BLACK);
        submit.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        submit.setForeground(Color.WHITE);
        submit.setBorder(emptyBorder);
        submit.setBounds(300, 350, 100, 30);
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
                new HomePage(name).setVisible(true);
                setVisible(false);
            }

            else if (ae.getSource() == submit) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PnrEnquiry("deepesh").setVisible(true);
    }

    
    
}
