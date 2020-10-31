package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.util.*;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class BotClient extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel;
    private JPanel p1, p2, panel, p3, p4, p5;
    private JButton back, submit, send, bot, bot0, bot1;
    private JTextField pnrText;
    private String name, Username;
    private Connect connection;
    private JScrollPane scroll;
    private int userid;
    private JComboBox tf1;
    private Vector<String> reply1, reply2;
    private Integer x1Final, yFinal, x2Final;
    private Integer noOfChats = 0;

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
        p1.setBackground((new Color(136, 217, 212)));
        p1.setBounds(0, 0, 750, 45);
        add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/backArrow.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        back = new JButton(i3);
        back.setBackground(new Color(136, 217, 212));
        Border emptyBorder = BorderFactory.createEmptyBorder();
        back.setBorder(emptyBorder);
        back.setBounds(5, 8, 30, 30);
        p1.add(back);

        headLabel = new JLabel("Have A Query?");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.black);
        headLabel.setBounds(250, 10, 400, 30);
        p1.add(headLabel);

        p2 = new Panel();
        p2.setLayout(null);

        scroll = new JScrollPane(p2);
        scroll.setBounds(15, 60, 710, 550);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scroll);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(Color.white);

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/chat.png"));
        i2 = i1.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        bot = new JButton(i3);
        bot.setBackground(new Color(136, 217, 212));
        bot.setBounds(15, 15, 50, 50);
        p2.add(bot);

        p3 = new JPanel();
        p3.setBackground(new Color(136, 217, 212));
        p3.setBounds(70, 15, 120, 40);
        p2.add(p3);

        JLabel reply1Label = new JLabel("Namaste");
        reply1Label.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        reply1Label.setForeground(Color.black);
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
        p4.setBackground((new Color(136, 217, 212)));
        p4.setBounds(70, 90, 410, 150);
        p2.add(p4);

        int x1 = 3, y1 = 5;
        for (int i = 0; i < reply1.size(); i++) {
            JLabel reply2Label = new JLabel(reply1.get(i), JLabel.LEFT);
            reply2Label.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
            reply2Label.setForeground(Color.black);
            reply2Label.setBounds(x1, y1, 400, 30);
            p4.add(reply2Label);
            y1 = y1 + 40;
        }

        i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/chat.png"));
        i2 = i1.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        bot1 = new JButton(i3);
        bot1.setBackground(Color.WHITE);
        bot1.setBounds(15, 265, 50, 50);
        p2.add(bot1);

        p5 = new JPanel();
        p5.setBackground(Color.BLACK);
        p5.setBounds(70, 265, 420, 600);
        p2.add(p5);

        int x2 = 3, y2 = 5;
        JLabel reply3Label[] = new JLabel[reply2.size()];
        for (int i = 0; i < reply2.size(); i++) {
            String str1 = reply2.get(i);

            reply3Label[i] = new JLabel(str1, JLabel.LEFT);
            reply3Label[i].setFont(new Font("Times new roman", Font.BOLD, 25));
            reply3Label[i].setBounds(x2, y2, 420, 30);
            reply3Label[i].setForeground(Color.WHITE);
            p5.add(reply3Label[i]);
            y2 = y2 + 40;
        }

        x1Final = 253;
        yFinal = 900;
        x2Final = x2;

        Vector<String> options = new Vector<String>();
        for (int i = 4; i < reply2.size(); i++) {
            options.add(reply2.get(i));
        }

        tf1 = new JComboBox(options);
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
        send.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    class Panel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            if (noOfChats > 0)
                return new Dimension(690, 1000 + 450 * (noOfChats));
            else
                return new Dimension(690, 1000);
        }
    }

    public void PrintSelectedItem(String selection) {
        JPanel p6 = new JPanel();
        p6.setBackground((new Color(136, 217, 212)));
        p6.setBounds(x1Final, yFinal, 380, 30);
        p2.add(p6);

        JLabel newLabel = new JLabel(selection);
        newLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        newLabel.setForeground(Color.black);
        newLabel.setBounds(5, 3, 380, 30);
        p6.add(newLabel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/user.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JButton user = new JButton(i3);
        user.setBackground(Color.WHITE);
        user.setBounds(640, yFinal, 35, 35);
        p2.add(user);

        yFinal += 50;
    }

    public void PrintBotResponse(Vector<String> selection) {
        JPanel p6 = new JPanel();
        p6.setBackground(new Color(136, 217, 212));
        p6.setBounds(70, yFinal, 428, 350);
        p2.add(p6);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/chat.png"));
        Image i2 = i1.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        bot1 = new JButton(i3);
        bot1.setBackground(Color.black);
        bot1.setBounds(15, yFinal, 50, 50);
        p2.add(bot1);

        int x2 = 3, y2 = 5;
        JLabel reply3Label[] = new JLabel[selection.size()];
        for (int i = 0; i < selection.size(); i++) {
            String str1 = selection.get(i);

            reply3Label[i] = new JLabel(str1, JLabel.LEFT);
            reply3Label[i].setFont(new Font("Times new roman", Font.BOLD, 25));
            reply3Label[i].setBounds(x2, y2, 428, 30);
            reply3Label[i].setForeground(Color.black);
            p6.add(reply3Label[i]);
            y2 = y2 + 40;
            yFinal += 40;
        }
        yFinal += 50;
        noOfChats++;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == back) {
                new HomePage(connection, name, userid).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == send) {
                String selection = (String) tf1.getSelectedItem();
                StringTokenizer st2 = new StringTokenizer(selection, ".");
                String selectString = st2.nextToken();
                Integer itemNo = Integer.parseInt(selectString);

                String parts[] = selection.split(" ", 2);
                PrintSelectedItem(parts[1]);
                if (itemNo != 13) {
                    try {

                        ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                        os.writeInt(18);
                        os.writeInt(itemNo);
                        os.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                    Vector<String> response = (Vector<String>) oi.readObject();
                    PrintBotResponse(response);
                }
                p2.repaint();
            }

        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }
}
