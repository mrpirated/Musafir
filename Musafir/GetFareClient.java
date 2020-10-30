package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;

public class GetFareClient extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, classLabel;
    private JPanel p1, p2, panel;
    private int userid;
    private JButton back, getFare;
    private JTextField pnrText, Fare;
    private String name, Username, trainNo;
    private Connect connection;
    private float fare;
    private JComboBox classType;
    private JLabel[] category;
    private JTextField[] noOfPeople;

    public GetFareClient(Connect connection, String name, float fare, int userid, String trainNo) {
        this.name = name;
        this.userid = userid;
        this.connection = connection;
        this.fare = fare;
        this.trainNo = trainNo;

        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "Get Fare");

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

        headLabel = new JLabel("Get Fare for Train No. " + trainNo);
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(200, 10, 400, 30);
        p1.add(headLabel);

        classLabel = new JLabel("Select Class:");
        classLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        classLabel.setForeground(Color.BLACK);
        classLabel.setBounds(220, 70, 400, 30);
        add(classLabel);

        String classes[] = { "Sleeper", "AC" };
        classType = new JComboBox(classes);
        classType.setBounds(370, 70, 100, 25);
        classType.setFont(new Font("Times new roman", Font.BOLD, 20));
        add(classType);

        Integer y1 = 150;
        category = new JLabel[4];
        noOfPeople = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            String str;
            if (i == 0)
                str = "Adult Passengers:";
            else if (i == 1)
                str = "Male Senior Citizens:";
            else if (i == 2)
                str = "Female Senior Citizens:";
            else
                str = "Child Passengers:";

            category[i] = new JLabel(str);
            category[i].setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
            category[i].setForeground(Color.BLACK);
            category[i].setBounds(180, y1, 400, 30);
            add(category[i]);

            noOfPeople[i] = new JTextField(15);
            noOfPeople[i].setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 20));
            noOfPeople[i].setForeground(Color.BLACK);
            noOfPeople[i].setBounds(450, y1, 100, 30);
            add(noOfPeople[i]);

            y1 = y1 + 60;
        }

        Fare = new JTextField(15);
        Fare.setEditable(false);
        Fare.setHorizontalAlignment(JTextField.CENTER);
        Fare.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 20));
        Fare.setForeground(Color.BLACK);
        Fare.setText("0");
        Fare.setBounds(320, 450, 100, 30);
        add(Fare);

        getFare = new JButton("Get Fare");
        getFare.setBackground(Color.BLACK);
        getFare.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        getFare.setForeground(Color.WHITE);
        getFare.setBorder(emptyBorder);
        getFare.setBounds(320, 500, 100, 30);
        add(getFare);

        back.addActionListener(this);
        getFare.addActionListener(this);

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
                setVisible(false);
            } else if (ae.getSource() == getFare) {
                float totalfare = 0;
                for (int i = 0; i < 4; i++) {
                    Integer n = 0;
                    if (i == 0) {
                        if (noOfPeople[i].getText().equals(""))
                            n = 0;
                        else
                            n = Integer.parseInt(noOfPeople[i].getText());

                        totalfare += n * (float) fare;
                    } else if (i == 1) {
                        if (noOfPeople[i].getText().equals(""))
                            n = 0;
                        else
                            n = Integer.parseInt(noOfPeople[i].getText());
                        totalfare += n * (float) fare * 0.6;

                    } else if (i == 2) {
                        if (noOfPeople[i].getText().equals(""))
                            n = 0;
                        else
                            n = Integer.parseInt(noOfPeople[i].getText());
                        totalfare += n * (float) fare / 2;

                    } else {
                        if (noOfPeople[i].getText().equals(""))
                            n = 0;
                        else
                            n = Integer.parseInt(noOfPeople[i].getText());
                        totalfare += n * (float) fare / 2;
                    }

                }
                String selectedClass = (String) classType.getSelectedItem();
                if (selectedClass == "AC") {
                    totalfare *= 3;
                }
                Fare.setText(String.valueOf(totalfare));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
