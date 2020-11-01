package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.time.*;
import java.sql.Date;

import org.jdesktop.swingx.JXDatePicker;

public class PassengerInfoAdmin extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, trainNo, dateLabel;
    private int userid;
    private JPanel p1, p2, panel;
    private JButton back, submit;
    private JTextField pnrText;
    private String name, Username;
    private Connect connection;
    private JScrollPane scroll;
    private Vector<String> trainList;
    private Integer noOfPassengers2 = 0, noOfPassengers = 0, y1 = 0, noOfTickets = 0, noOfTickets2 = 0;
    private JComboBox tf1;
    private JXDatePicker picker;
    private java.util.Date dt = new java.util.Date();

    public PassengerInfoAdmin(Connect connection, Vector<String> trainList) {
        this.connection = connection;
        this.trainList = trainList;

        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "PASSENGER INFO");

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

        headLabel = new JLabel("PASSENGER INFO");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(250, 10, 400, 30);
        p1.add(headLabel);

        trainNo = new JLabel("Train:");
        trainNo.setFont(new Font("Times new roman", Font.BOLD, 20));
        trainNo.setBounds(15, 70, 150, 32);
        add(trainNo);

        tf1 = new JComboBox(trainList);
        tf1.setFont(new Font("Times new roman", Font.BOLD, 14));
        tf1.setBounds(100, 70, 300, 30);
        add(tf1);

        dateLabel = new JLabel("Date:");
        dateLabel.setFont(new Font("Times new roman", Font.BOLD, 20));
        dateLabel.setBounds(450, 70, 150, 32);
        add(dateLabel);

        panel = new JPanel();
        picker = new JXDatePicker();
        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        panel.setBackground(Color.white);
        panel.setBounds(550, 70, 150, 30);
        panel.add(picker);
        add(panel);

        submit = new JButton("Get Details");
        submit.setBackground(Color.BLACK);
        submit.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        submit.setForeground(Color.WHITE);
        submit.setBorder(emptyBorder);
        submit.setBounds(300, 120, 100, 30);
        add(submit);

        p2 = new Panel();
        p2.setLayout(null);

        scroll = new JScrollPane(p2);
        scroll.setBounds(15, 160, 690, 500);
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
            if (noOfTickets > 2)
                return new Dimension(670, 1000 + 1000 * (noOfTickets - 2));
            else
                return new Dimension(670, 1000);
        }
    }

    public void showPnrDetails(PnrEnquiryFinalInfo passengerDetails) {
        submit.setEnabled(false);
        noOfPassengers = passengerDetails.getPassengersInfo().size();
        y1 += 5;
        JLabel trainNoLabel = new JLabel("TRAIN NO:");
        trainNoLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        trainNoLabel.setBounds(15, y1, 160, 30);
        p2.add(trainNoLabel);

        String trainNox = passengerDetails.getTrainNo();
        JLabel trainNo1Label = new JLabel(trainNox);
        trainNo1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
        trainNo1Label.setBounds(120, y1, 160, 30);
        p2.add(trainNo1Label);

        JLabel trainNameLabel = new JLabel("TRAIN NAME:");
        trainNameLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        trainNameLabel.setBounds(370, y1, 160, 30);
        p2.add(trainNameLabel);

        JLabel trainName1Label = new JLabel(passengerDetails.getTrainName());
        trainName1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
        trainName1Label.setBounds(515, y1, 160, 30);
        p2.add(trainName1Label);

        y1 += 30;
        JLabel src = new JLabel("SOURCE:");
        src.setFont(new Font("Times new roman", Font.BOLD, 18));
        src.setBounds(15, y1, 160, 30);
        p2.add(src);

        JLabel src1 = new JLabel(passengerDetails.getSrc());
        src1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        src1.setBounds(110, y1, 160, 30);
        p2.add(src1);

        JLabel dest = new JLabel("DESTINATION:");
        dest.setFont(new Font("Times new roman", Font.BOLD, 18));
        dest.setBounds(370, y1, 160, 30);
        p2.add(dest);

        JLabel dest1 = new JLabel(passengerDetails.getDest());
        dest1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        dest1.setBounds(515, y1, 160, 30);
        p2.add(dest1);

        y1 += 30;
        JLabel pnrLabel = new JLabel("PNR:");
        pnrLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        pnrLabel.setBounds(15, y1, 250, 30);
        p2.add(pnrLabel);

        JLabel pnrLabel1 = new JLabel(passengerDetails.getPnr());
        pnrLabel1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        pnrLabel1.setBounds(90, y1, 160, 30);
        p2.add(pnrLabel1);

        y1 += 30;
        JLabel doj = new JLabel("DATE OF JOURNEY:");
        doj.setFont(new Font("Times new roman", Font.BOLD, 18));
        doj.setBounds(15, y1, 250, 30);
        p2.add(doj);

        JLabel doj1 = new JLabel(passengerDetails.getDoj().toString());
        doj1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        doj1.setBounds(240, y1, 160, 30);
        p2.add(doj1);

        JLabel noOfpass = new JLabel("NO OF PASSENGERS:");
        noOfpass.setFont(new Font("Times new roman", Font.BOLD, 18));
        noOfpass.setBounds(370, y1, 250, 30);
        p2.add(noOfpass);

        JLabel noOfpass1 = new JLabel(noOfPassengers.toString());
        noOfpass1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        noOfpass1.setBounds(595, y1, 160, 30);
        p2.add(noOfpass1);

        y1 += 60;
        JLabel passengerLabel = new JLabel("PASSENGER DETAILS");
        passengerLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        passengerLabel.setBounds(240, y1, 300, 30);
        p2.add(passengerLabel);

        int x;
        y1 = y1 + 50;
        for (int i = 0; i < noOfPassengers; i++) {
            x = 2;
            Integer srNo = i + 1;
            JLabel srNo1 = new JLabel(srNo.toString() + ".");
            srNo1.setFont(new Font("Times new roman", Font.BOLD, 20));
            srNo1.setBounds(x, y1 + 20, 160, 30);
            p2.add(srNo1);

            x = 35;
            JLabel nameLabel = new JLabel("Name:");
            nameLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
            nameLabel.setBounds(x, y1, 160, 30);
            p2.add(nameLabel);

            x = 160;
            String nameX = passengerDetails.getPassengersInfo().get(i).getName();
            JLabel name1Label = new JLabel(nameX);
            name1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
            name1Label.setBounds(x, y1, 160, 30);
            p2.add(name1Label);

            x = 390;

            JLabel berthLabel = new JLabel("Berth No:");
            berthLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
            berthLabel.setBounds(x, y1, 160, 30);
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
            berth1Label.setBounds(x, y1, 160, 30);
            p2.add(berth1Label);

            x = 35;
            JLabel gender = new JLabel("Gender:");
            gender.setFont(new Font("Times new roman", Font.BOLD, 18));
            gender.setBounds(x, y1 + 25, 160, 30);
            p2.add(gender);

            x = 160;
            JLabel gender1 = new JLabel(passengerDetails.getPassengersInfo().get(i).getGender());
            gender1.setFont(new Font("Times new roman", Font.PLAIN, 18));
            gender1.setBounds(x, y1 + 25, 160, 30);
            p2.add(gender1);

            x = 390;
            JLabel ageLabel = new JLabel("Age:");
            ageLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
            ageLabel.setBounds(x, y1 + 25, 160, 30);
            p2.add(ageLabel);

            x = 535;
            JLabel age1Label = new JLabel(passengerDetails.getPassengersInfo().get(i).getAge().toString());
            age1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
            age1Label.setBounds(x, y1 + 25, 160, 30);
            p2.add(age1Label);

            x = 35;
            JLabel mealLabel = new JLabel("Meal Booked:");
            mealLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
            mealLabel.setBounds(x, y1 + 50, 200, 30);
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
            meal1Label.setBounds(x, y1 + 50, 160, 30);
            p2.add(meal1Label);

            x = 390;
            JLabel berthtype = new JLabel("Berth Type:");
            berthtype.setFont(new Font("Times new roman", Font.BOLD, 18));
            berthtype.setBounds(x, y1 + 50, 160, 30);
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
            berthtype1.setBounds(x, y1 + 50, 200, 30);
            p2.add(berthtype1);

            y1 = y1 + 100;
        }

        x = 325;
        JLabel line = new JLabel("----X----");
        line.setFont(new Font("Times new roman", Font.PLAIN, 15));
        line.setBounds(x, y1, 160, 30);
        p2.add(line);

        y1 = y1 + 80;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == back) {
                new AdminHome(connection).setVisible(true);
                setVisible(false);
            }

            else if (ae.getSource() == submit) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                String train = (String) tf1.getSelectedItem();
                // String parts[] = train.split(" ", 2);
                // String trainNoSend = parts[0];

                dt = picker.getDate();
                String d = df.format(dt);
                Date date = Date.valueOf(d);
                PassengerInfoAdminInfo newRequest = new PassengerInfoAdminInfo(train, date);

                p2.removeAll();
                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(22);
                    os.writeObject(newRequest);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                Vector<PnrEnquiryFinalInfo> bookingHistory = (Vector<PnrEnquiryFinalInfo>) oi.readObject();
                noOfTickets = bookingHistory.size();
                System.out.println(noOfTickets);
                for (int i = 0; i < noOfTickets; i++) {
                    showPnrDetails(bookingHistory.get(i));
                }

                p2.revalidate();
                p2.repaint();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
