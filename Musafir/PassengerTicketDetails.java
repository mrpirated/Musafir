package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class PassengerTicketDetails extends JFrame implements ActionListener {
    private JLabel headLabel, pnrLabel, l1, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14;
    private JPanel p1, p2, panel;
    private JButton back, submit;
    private JTextField pnrText;
    private String Username, trainNo, type, name, src, dest;
    private Integer noOfPassenger;
    private Date date;
    private JComboBox cb;
    private JTextField[] nameOfPassenger, age;
    private JComboBox[] gender, preference;
    private JLabel[] number;
    private Connect connection;
    public PassengerTicketDetails(Connect connection,String name, String Username, String trainNo, String type, String src, String dest,
            Date date, Integer noOfPassenger) {
        this.name = name;
        this.Username = Username;
        this.trainNo = trainNo;
        this.type = type;
        this.src = src;
        this.dest = dest;
        this.date = date;
        this.noOfPassenger = noOfPassenger;
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
        setTitle(pad + "Passenger Details");

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
        add(back);

        headLabel = new JLabel("Add " + noOfPassenger + " Passengers Details");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 28));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(220, 10, 400, 30);
        p1.add(headLabel);

        l7 = new JLabel("FROM: ");
        l7.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        l7.setForeground(Color.BLACK);
        l7.setBounds(80, 70, 400, 30);
        add(l7);

        l8 = new JLabel("TO: ");
        l8.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        l8.setForeground(Color.BLACK);
        l8.setBounds(500, 70, 400, 30);
        add(l8);

        l9 = new JLabel("DATE OF JOURNEY: ");
        l9.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        l9.setForeground(Color.BLACK);
        l9.setBounds(80, 100, 400, 30);
        add(l9);

        l10 = new JLabel("TRAIN NO: ");
        l10.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        l10.setForeground(Color.BLACK);
        l10.setBounds(500, 100, 400, 30);
        add(l10);

        l11 = new JLabel(src);
        l11.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 20));
        l11.setForeground(Color.BLACK);
        l11.setBounds(180, 70, 400, 30);
        add(l11);

        l12 = new JLabel(dest);
        l12.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 20));
        l12.setForeground(Color.BLACK);
        l12.setBounds(560, 70, 400, 30);
        add(l12);

        l13 = new JLabel(date.toString());
        l13.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 20));
        l13.setForeground(Color.BLACK);
        l13.setBounds(310, 100, 400, 30);
        add(l13);

        l14 = new JLabel(trainNo);
        l14.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 20));
        l14.setForeground(Color.BLACK);
        l14.setBounds(620, 100, 400, 30);
        add(l14);

        l3 = new JLabel("Name Of Passenger");
        l3.setFont(new Font("Times new roman", Font.BOLD, 20));
        l3.setBounds(55, 150, 400, 32);
        add(l3);

        l4 = new JLabel("Age");
        l4.setFont(new Font("Times new roman", Font.BOLD, 20));
        l4.setBounds(295, 150, 400, 32);
        add(l4);

        l5 = new JLabel("Gender");
        l5.setFont(new Font("Times new roman", Font.BOLD, 20));
        l5.setBounds(410, 150, 400, 32);
        add(l5);

        l6 = new JLabel("Preference");
        l6.setFont(new Font("Times new roman", Font.BOLD, 20));
        l6.setBounds(560, 150, 400, 32);
        add(l6);

        nameOfPassenger = new JTextField[noOfPassenger];
        age = new JTextField[noOfPassenger];
        gender = new JComboBox[noOfPassenger];
        preference = new JComboBox[noOfPassenger];

        number = new JLabel[noOfPassenger];

        Integer i = 0, y1 = 200;

        for (i = 0; i < noOfPassenger; i++) {
            String str = String.valueOf(i + 1);
            number[i] = new JLabel(str + ".");
            number[i].setFont(new Font("Times new roman", Font.BOLD, 25));
            number[i].setBounds(20, y1 - 8, 400, 32);
            add(number[i]);

            Integer x1 = 55;

            nameOfPassenger[i] = new JTextField(50);
            nameOfPassenger[i].setBounds(x1, y1, 200, 25);
            nameOfPassenger[i].setFont(new Font("Times new roman", Font.BOLD, 20));
            add(nameOfPassenger[i]);

            x1 = 295;
            age[i] = new JTextField(51);
            age[i].setBounds(x1, y1, 80, 25);
            age[i].setFont(new Font("Times new roman", Font.BOLD, 20));
            add(age[i]);

            x1 = 410;
            String genderx[] = { "F", "M", "O" };
            gender[i] = new JComboBox(genderx);
            gender[i].setBounds(x1, y1, 80, 25);
            gender[i].setFont(new Font("Times new roman", Font.BOLD, 20));
            add(gender[i]);

            x1 = 560;
            String preferencex[] = { "LB", "MB", "UB", "SLB", "SUB" };
            preference[i] = new JComboBox(preferencex);
            preference[i].setBounds(x1, y1, 80, 25);
            preference[i].setFont(new Font("Times new roman", Font.BOLD, 20));
            add(preference[i]);

            y1 = y1 + 50;
            System.out.println(i + "\n");
        }

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        submit.setForeground(Color.WHITE);
        submit.setBorder(emptyBorder);
        submit.setBounds(300, 650, 100, 30);
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
                new BasicTicketDetails(connection,name, Username, trainNo, type, src, dest, date).setVisible(true);
                setVisible(false);
            }
            if (ae.getSource() == submit) {
                PassengersDetailForm passengerDetailForm = new PassengersDetailForm(name, Username, trainNo, type, src,
                        dest, date, noOfPassenger);
                PassengerInfo[] passengerInfo = new PassengerInfo[noOfPassenger];

                for (int i = 0; i < noOfPassenger; i++) {
                    String passengerNameSend = nameOfPassenger[i].getText();
                    String agex = age[i].getText();
                    Integer ageSend = Integer.parseInt(agex);
                    String genderx = (String) gender[i].getSelectedItem();
                    Character genderSend = genderx.charAt(0);
                    String berthPrefSend = (String) preference[i].getSelectedItem();
                    passengerInfo[i] = new PassengerInfo(passengerNameSend, ageSend, genderSend, berthPrefSend);
                }

                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(8);
                    os.writeObject(passengerDetailForm);
                    for (int i = 0; i < noOfPassenger; i++) {
                        os.writeObject(passengerInfo[i]);
                    }
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                String s = (String) oi.readUTF();
                if (s.equals("ok")) {

                } else {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
