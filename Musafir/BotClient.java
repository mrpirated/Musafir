package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;

public class BotClient extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel;
    private JPanel p1, p2, panel;
    private JButton back, submit, send;
    private JTextField pnrText, tf1;
    private String name, Username;
    private Connect connection;
    private JScrollPane scroll;

    public BotClient(Connect connection, String name, String Username) {
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
            return new Dimension(690, 590);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == back) {
                new HomePage(connection, name, Username).setVisible(true);
                setVisible(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
