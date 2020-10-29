package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.util.*;

public class BotClient extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel;
    private JPanel p1, p2, panel, p3, p4, p5;
    private JButton back, submit, send, bot, bot0, bot1;
    private JTextField pnrText, tf1;
    private String name;
    private Connect connection;
    private JScrollPane scroll;
    private int userid;
    private Vector<String> reply1, reply2;

    public BotClient(Connect connection, String name, int userid, Vector<String> reply1, Vector<String> reply2) {
        this.name = name;
        this.userid = userid;
        this.connection = connection;
        this.reply2 = reply2;
        this.reply1 = reply1;

        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "Have A Query?");

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

        headLabel = new JLabel("Have A Query?");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(250, 10, 400, 30);
        p1.add(headLabel);

        p2 = new Panel();
        p2.setLayout(null);

        scroll = new JScrollPane(p2);
        scroll.setBounds(15, 60, 710, 550);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scroll);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/chat.png"));
        i2 = i1.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        bot = new JButton(i3);
        bot.setBackground(Color.WHITE);
        bot.setBounds(15, 15, 50, 50);
        p2.add(bot);

        p3 = new JPanel();
        p3.setBackground(Color.BLACK);
        p3.setBounds(70, 15, 120, 40);
        p2.add(p3);

        JLabel reply1Label = new JLabel("Namaste");
        reply1Label.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        reply1Label.setForeground(Color.WHITE);
        reply1Label.setBounds(3, 5, 120, 35);
        p3.add(reply1Label);
        p2.add(p3);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/chat.png"));
        i2 = i1.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        bot0 = new JButton(i3);
        bot0.setBackground(Color.WHITE);
        bot0.setBounds(15, 90, 50, 50);
        p2.add(bot0);

        p4 = new JPanel();
        p4.setBackground(Color.BLACK);
        p4.setBounds(70, 90, 410, 410);
        p2.add(p4);

        int x1 = 3, y1 = 5;
        for (int i = 0; i < reply1.size(); i++) {
            JLabel reply2Label = new JLabel(reply1.get(i), SwingConstants.LEFT);
            reply2Label.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
            reply2Label.setForeground(Color.WHITE);
            reply2Label.setBounds(x1, y1, 400, 30);
            p4.add(reply2Label);
            y1 = y1 + 40;
        }
        p2.add(p4);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/chat.png"));
        i2 = i1.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        bot1 = new JButton(i3);
        bot1.setBackground(Color.WHITE);
        bot1.setBounds(15, 525, 50, 50);
        p2.add(bot1);

        p5 = new JPanel();
        p5.setBackground(Color.BLACK);
        p5.setBounds(70, 525, 550, 720);
        p2.add(p5);

        int x2 = 3, y2 = 5;
        for (int i = 0; i < reply2.size(); i++) {
            JLabel reply2Label = new JLabel(reply2.get(i), SwingConstants.LEFT);
            reply2Label.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
            reply2Label.setForeground(Color.WHITE);
            reply2Label.setBounds(x2, y2, 540, 30);
            p5.add(reply2Label);
            y2 = y2 + 40;
        }
        p2.add(p5);

        tf1 = new JTextField(15);
        tf1.setFont(new Font("Times new roman", Font.BOLD, 14));
        tf1.setBounds(15, 620, 550, 30);
        add(tf1);

        send = new JButton("SEND");
        send.setBackground(Color.BLACK);
        send.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        send.setForeground(Color.WHITE);
        send.setBorder(emptyBorder);
        send.setBounds(570, 620, 90, 30);
        add(send);

        back.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    class Panel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(690, 1500);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == back) {
                new HomePage(connection, name, userid).setVisible(true);
                setVisible(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
