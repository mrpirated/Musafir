package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.util.*;

public class PnrEnquiry extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel;
    private int userid;
    private JPanel p1, p2, panel;
    private JButton back, submit;
    private JTextField pnrText;
    private String name, Username;
    private Connect connection;
    private JScrollPane scroll;
    private Integer noOfPassengers = 0;

    public PnrEnquiry(Connect connection, String name, int userid) {
        this.name = name;
        this.userid = userid;
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
        pnrLabel.setBounds(270, 70, 400, 30);
        add(pnrLabel);

        pnrText = new JTextField(10);
        pnrText.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 20));
        pnrText.setHorizontalAlignment(JTextField.CENTER);
        pnrText.setForeground(Color.BLACK);
        pnrText.setBounds(280, 120, 150, 30);
        add(pnrText);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        submit = new JButton("Get Details");
        submit.setBackground(Color.BLACK);
        submit.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        submit.setForeground(Color.WHITE);
        submit.setBorder(emptyBorder);
        submit.setBounds(300, 170, 100, 30);
        add(submit);

        p2 = new Panel();
        p2.setLayout(null);

        scroll = new JScrollPane(p2);
        scroll.setBounds(15, 210, 690, 470);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scroll);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        back.addActionListener(this);
        submit.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    class Panel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            if (noOfPassengers > 3)
                return new Dimension(670, 460 + 100 * (noOfPassengers - 3));
            else
                return new Dimension(670, 460);
        }
    }

    public void errorDisplay() {
        JLabel trainNoLabel = new JLabel("INVALID PNR NUMBER.");
        trainNoLabel.setFont(new Font("Times new roman", Font.BOLD, 25));
        trainNoLabel.setBounds(150, 200, 500, 30);
        p2.add(trainNoLabel);
        return;
    }

    public void showPnrDetails(PnrEnquiryFinalInfo passengerDetails) {
        noOfPassengers = passengerDetails.getPassengersInfo().size();

        JLabel trainNoLabel = new JLabel("TRAIN NO:");
        trainNoLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        trainNoLabel.setBounds(15, 5, 160, 30);
        p2.add(trainNoLabel);

        String trainNox = passengerDetails.getTrainNo();
        JLabel trainNo1Label = new JLabel(trainNox);
        trainNo1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
        trainNo1Label.setBounds(120, 5, 160, 30);
        p2.add(trainNo1Label);

        JLabel trainNameLabel = new JLabel("TRAIN NAME:");
        trainNameLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        trainNameLabel.setBounds(370, 5, 160, 30);
        p2.add(trainNameLabel);

        JLabel trainName1Label = new JLabel(passengerDetails.getTrainName());
        trainName1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
        trainName1Label.setBounds(515, 5, 160, 30);
        p2.add(trainName1Label);

        JLabel src = new JLabel("SOURCE:");
        src.setFont(new Font("Times new roman", Font.BOLD, 18));
        src.setBounds(15, 35, 160, 30);
        p2.add(src);

        JLabel src1 = new JLabel(passengerDetails.getSrc());
        src1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        src1.setBounds(110, 35, 160, 30);
        p2.add(src1);

        JLabel dest = new JLabel("DESTINATION:");
        dest.setFont(new Font("Times new roman", Font.BOLD, 18));
        dest.setBounds(370, 35, 160, 30);
        p2.add(dest);

        JLabel dest1 = new JLabel(passengerDetails.getDest());
        dest1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        dest1.setBounds(515, 35, 160, 30);
        p2.add(dest1);

        JLabel doj = new JLabel("DATE OF JOURNEY:");
        doj.setFont(new Font("Times new roman", Font.BOLD, 18));
        doj.setBounds(15, 65, 250, 30);
        p2.add(doj);

        JLabel doj1 = new JLabel(passengerDetails.getDoj().toString());
        doj1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        doj1.setBounds(240, 65, 160, 30);
        p2.add(doj1);

        JLabel noOfpass = new JLabel("NO OF PASSENGERS:");
        noOfpass.setFont(new Font("Times new roman", Font.BOLD, 18));
        noOfpass.setBounds(370, 65, 250, 30);
        p2.add(noOfpass);

        JLabel noOfpass1 = new JLabel(noOfPassengers.toString());
        noOfpass1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        noOfpass1.setBounds(595, 65, 160, 30);
        p2.add(noOfpass1);

        JLabel pnrLabel = new JLabel("PNR:");
        pnrLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        pnrLabel.setBounds(15, 95, 250, 30);
        p2.add(pnrLabel);

        JLabel pnrLabel1 = new JLabel(passengerDetails.getPnr());
        pnrLabel1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        pnrLabel1.setBounds(240, 95, 160, 30);
        p2.add(pnrLabel1);

        JLabel passengerLabel = new JLabel("PASSENGER DETAILS");
        passengerLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        passengerLabel.setBounds(240, 120, 300, 30);
        p2.add(passengerLabel);

        int x, y = 170;
        for (int i = 0; i < noOfPassengers; i++) {
            x = 2;
            Integer srNo = i + 1;
            JLabel srNo1 = new JLabel(srNo.toString() + ".");
            srNo1.setFont(new Font("Times new roman", Font.BOLD, 20));
            srNo1.setBounds(x, y + 20, 160, 30);
            p2.add(srNo1);

            x = 35;
            JLabel nameLabel = new JLabel("Name:");
            nameLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
            nameLabel.setBounds(x, y, 160, 30);
            p2.add(nameLabel);

            x = 160;
            String nameX = passengerDetails.getPassengersInfo().get(i).getName();
            JLabel name1Label = new JLabel(nameX);
            name1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
            name1Label.setBounds(x, y, 160, 30);
            p2.add(name1Label);

            x = 390;

            JLabel berthLabel = new JLabel("Berth No:");
            berthLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
            berthLabel.setBounds(x, y, 160, 30);
            p2.add(berthLabel);

            x = 535;
            Integer waitingNo = passengerDetails.getPassengersInfo().get(i).getWaiting();
            Integer typeNo = passengerDetails.getPassengersInfo().get(i).getType();
            Integer seatNo = passengerDetails.getPassengersInfo().get(i).getSeat_no();
            String coachNo = passengerDetails.getPassengersInfo().get(i).getCoach_no();

            String seatText = "";
            if (waitingNo == 0) {
                if (typeNo == 1) {
                    seatText = "S-" + coachNo + " / " + seatNo.toString();
                } else if (typeNo == 2) {
                    seatText = "B-" + coachNo + " / " + seatNo.toString();
                }
            } else {
                seatText = "WL / " + waitingNo.toString();
            }

            JLabel berth1Label = new JLabel(seatText);
            berth1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
            berth1Label.setBounds(x, y, 160, 30);
            p2.add(berth1Label);

            x = 35;
            JLabel gender = new JLabel("Gender:");
            gender.setFont(new Font("Times new roman", Font.BOLD, 18));
            gender.setBounds(x, y + 25, 160, 30);
            p2.add(gender);

            x = 160;
            JLabel gender1 = new JLabel(passengerDetails.getPassengersInfo().get(i).getGender());
            gender1.setFont(new Font("Times new roman", Font.PLAIN, 18));
            gender1.setBounds(x, y + 25, 160, 30);
            p2.add(gender1);

            x = 390;
            JLabel ageLabel = new JLabel("Age:");
            ageLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
            ageLabel.setBounds(x, y + 25, 160, 30);
            p2.add(ageLabel);

            x = 535;
            JLabel age1Label = new JLabel(passengerDetails.getPassengersInfo().get(i).getAge().toString());
            age1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
            age1Label.setBounds(x, y + 25, 160, 30);
            p2.add(age1Label);

            x = 35;
            JLabel mealLabel = new JLabel("Meal Booked:");
            mealLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
            mealLabel.setBounds(x, y + 50, 200, 30);
            p2.add(mealLabel);

            char mealbooked = passengerDetails.getPassengersInfo().get(0).getMeal();
            String mealResponse;
            if (mealbooked == '0') {
                mealResponse = "No";
            } else {
                mealResponse = "Yes";
            }
            x = 160;
            JLabel meal1Label = new JLabel(mealResponse);
            meal1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
            meal1Label.setBounds(x, y + 50, 160, 30);
            p2.add(meal1Label);

            x = 390;
            JLabel berthtype = new JLabel("Berth Type:");
            berthtype.setFont(new Font("Times new roman", Font.BOLD, 18));
            berthtype.setBounds(x, y + 50, 160, 30);
            p2.add(berthtype);

            String berth;
            if (seatNo % 8 == 1 || seatNo % 8 == 4) {
                berth = "Lower Berth";
            } else if (seatNo % 8 == 2 || seatNo % 8 == 5) {
                berth = "Middle Berth";
            } else if (seatNo % 8 == 3 || seatNo % 8 == 6) {
                berth = "Upper Berth";
            } else if (seatNo % 8 == 7) {
                berth = "Side Lower Berth";
            } else {
                berth = "Side Upper Berth";
            }
            x = 535;
            JLabel berthtype1 = new JLabel(berth);
            berthtype1.setFont(new Font("Times new roman", Font.PLAIN, 18));
            berthtype1.setBounds(x, y + 50, 200, 30);
            p2.add(berthtype1);

            y = y + 100;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == back) {
                new HomePage(connection, name, userid).setVisible(true);
                setVisible(false);
            }

            else if (ae.getSource() == submit) {
                p2.removeAll();
                String pnr = pnrText.getText();
                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(19);
                    os.writeUTF(pnr);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                String train = (String) oi.readUTF();
                if (train != " ") {
                    PnrEnquiryFinalInfo passengerDetails = (PnrEnquiryFinalInfo) oi.readObject();
                    showPnrDetails(passengerDetails);
                } else {
                    errorDisplay();
                }
                p2.revalidate();
                p2.repaint();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
