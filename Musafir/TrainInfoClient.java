package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.util.Vector;

public class TrainInfoClient extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, trainNoLabel;
    private JPanel p1, p2, panel;
    private JButton back, submit, b1;
    private JTextField pnrText, tf1;
    private Connect connection;
    private JScrollPane scroll;
    private int noOfHalts = 0;
    private String name, Username;

    public TrainInfoClient(Connect connection, String name, String Username) {
        this.connection = connection;
        this.name = name;
        this.Username = Username;

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

        p2 = new Panel();
        p2.setLayout(null);

        scroll = new JScrollPane(p2);
        scroll.setBounds(15, 150, 690, 550);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scroll);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        back.addActionListener(this);
        b1.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    public void errorDisplay() {
        JLabel trainNoLabel = new JLabel("INVALID TRAIN NUMBER.");
        trainNoLabel.setFont(new Font("Times new roman", Font.BOLD, 25));
        trainNoLabel.setBounds(150, 200, 500, 30);
        p2.add(trainNoLabel);
        return;
    }

    public void showTrainDetails(TrainBasicInfoAdminInfo trainDetails, Vector<AddTrainAdminNextInfo> stationsInfo) {
        noOfHalts = stationsInfo.size();

        JLabel trainNoLabel = new JLabel("TRAIN NO:");
        trainNoLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        trainNoLabel.setBounds(15, 5, 160, 30);
        p2.add(trainNoLabel);

        String trainNox = trainDetails.getTrain_no().toString();
        JLabel trainNo1Label = new JLabel(trainNox);
        trainNo1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
        trainNo1Label.setBounds(120, 5, 160, 30);
        p2.add(trainNo1Label);

        JLabel trainNameLabel = new JLabel("TRAIN NAME:");
        trainNameLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        trainNameLabel.setBounds(370, 5, 160, 30);
        p2.add(trainNameLabel);

        JLabel trainName1Label = new JLabel(trainDetails.getTrain_name());
        trainName1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
        trainName1Label.setBounds(515, 5, 160, 30);
        p2.add(trainName1Label);

        JLabel src = new JLabel("SOURCE:");
        src.setFont(new Font("Times new roman", Font.BOLD, 18));
        src.setBounds(15, 35, 160, 30);
        p2.add(src);

        JLabel src1 = new JLabel(trainDetails.getSrc());
        src1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        src1.setBounds(110, 35, 160, 30);
        p2.add(src1);

        JLabel dest = new JLabel("DESTINATION:");
        dest.setFont(new Font("Times new roman", Font.BOLD, 18));
        dest.setBounds(370, 35, 160, 30);
        p2.add(dest);

        JLabel dest1 = new JLabel(trainDetails.getDest());
        dest1.setFont(new Font("Times new roman", Font.PLAIN, 18));
        dest1.setBounds(515, 35, 160, 30);
        p2.add(dest1);

        JLabel runningDaysLabel = new JLabel("RUNS ON:");
        runningDaysLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        runningDaysLabel.setBounds(15, 65, 160, 30);
        p2.add(runningDaysLabel);

        JLabel runDays[] = new JLabel[7];
        Integer x1 = 115;
        String runningDays1 = trainDetails.getRunningDays();
        for (int i = 0; i < 7; i++) {
            String day = new String();
            if (i == 0) {
                day = "Mon";
            } else if (i == 1) {
                day = "Tue";
            } else if (i == 2) {
                day = "Wed";
            } else if (i == 3) {
                day = "Thu";
            } else if (i == 4) {
                day = "Fri";
            } else if (i == 5) {
                day = "Sat";
            } else if (i == 6) {
                day = "Sun";
            }
            if (runningDays1.charAt(i) == '1') {
                runDays[i] = new JLabel(day);
                runDays[i].setFont(new Font("Times new roman", Font.PLAIN, 18));
                runDays[i].setBounds(x1, 65, 160, 30);
                p2.add(runDays[i]);
                x1 = x1 + 50;
            }
        }

        JLabel tsSlrLabel = new JLabel("SEATS IN SLR:");
        tsSlrLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        tsSlrLabel.setBounds(15, 95, 160, 30);
        p2.add(tsSlrLabel);

        JLabel tsSlr1Label = new JLabel(trainDetails.getTs_slr().toString());
        tsSlr1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
        tsSlr1Label.setBounds(150, 95, 160, 30);
        p2.add(tsSlr1Label);

        JLabel tsAcLabel = new JLabel("SEATS IN AC:");
        tsAcLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        tsAcLabel.setBounds(370, 95, 160, 30);
        p2.add(tsAcLabel);

        JLabel tsAc1Label = new JLabel(trainDetails.getTs_ac().toString());
        tsAc1Label.setFont(new Font("Times new roman", Font.PLAIN, 18));
        tsAc1Label.setBounds(495, 95, 160, 30);
        p2.add(tsAc1Label);

        JLabel srnoLabel = new JLabel("| SR NO  |");
        srnoLabel.setFont(new Font("Times new roman", Font.BOLD, 15));
        srnoLabel.setBounds(5, 135, 200, 30);
        p2.add(srnoLabel);

        JLabel stationLabel = new JLabel("STATION  |");
        stationLabel.setFont(new Font("Times new roman", Font.BOLD, 15));
        stationLabel.setBounds(90, 135, 200, 30);
        p2.add(stationLabel);

        JLabel arrivalLabel = new JLabel("ARRIVAL      |");
        arrivalLabel.setFont(new Font("Times new roman", Font.BOLD, 15));
        arrivalLabel.setBounds(180, 135, 200, 30);
        p2.add(arrivalLabel);

        JLabel departureLabel = new JLabel("DEPARTURE   |");
        departureLabel.setFont(new Font("Times new roman", Font.BOLD, 15));
        departureLabel.setBounds(290, 135, 200, 30);
        p2.add(departureLabel);

        JLabel distanceLabel = new JLabel("DISTANCE    |");
        distanceLabel.setFont(new Font("Times new roman", Font.BOLD, 15));
        distanceLabel.setBounds(405, 135, 200, 30);
        p2.add(distanceLabel);

        JLabel dayLabel = new JLabel("DAY  |");
        dayLabel.setFont(new Font("Times new roman", Font.BOLD, 15));
        dayLabel.setBounds(520, 135, 200, 30);
        p2.add(dayLabel);

        JLabel platformLabel = new JLabel("PLATFORM |");
        platformLabel.setFont(new Font("Times new roman", Font.BOLD, 15));
        platformLabel.setBounds(580, 135, 200, 30);
        p2.add(platformLabel);

        JLabel[] srNo = new JLabel[noOfHalts];
        JLabel[] stationName = new JLabel[noOfHalts];
        JLabel[] arrival = new JLabel[noOfHalts];
        JLabel[] departure = new JLabel[noOfHalts];
        JLabel[] distance = new JLabel[noOfHalts];
        JLabel[] day = new JLabel[noOfHalts];
        JLabel[] platform = new JLabel[noOfHalts];

        String str;
        int x, y = 160;
        for (int i = 0; i < noOfHalts; i++) {
            x = 20;
            str = String.valueOf(i + 1);
            srNo[i] = new JLabel(str);
            srNo[i].setFont(new Font("Times new roman", Font.PLAIN, 12));
            srNo[i].setBounds(x, y, 30, 30);
            p2.add(srNo[i]);

            x = 75;
            str = String.valueOf(stationsInfo.get(i).getStation());
            stationName[i] = new JLabel(str);
            stationName[i].setFont(new Font("Times new roman", Font.PLAIN, 12));
            stationName[i].setBounds(x, y, 200, 30);
            p2.add(stationName[i]);

            x = 180;
            if (i != 0) {
                str = String.valueOf(stationsInfo.get(i).getArrival());
            } else {
                str = "Source";
            }
            arrival[i] = new JLabel(str);
            arrival[i].setFont(new Font("Times new roman", Font.PLAIN, 12));
            arrival[i].setBounds(x, y, 150, 30);
            p2.add(arrival[i]);

            x = 290;
            if (i != noOfHalts - 1) {
                str = String.valueOf(stationsInfo.get(i).getDeparture());
            } else {
                str = "Destination";
            }
            departure[i] = new JLabel(str);
            departure[i].setFont(new Font("Times new roman", Font.PLAIN, 12));
            departure[i].setBounds(x, y, 150, 30);
            p2.add(departure[i]);

            x = 420;
            str = String.valueOf(stationsInfo.get(i).getDistance()) + " km";
            distance[i] = new JLabel(str);
            distance[i].setFont(new Font("Times new roman", Font.PLAIN, 12));
            distance[i].setBounds(x, y, 150, 30);
            p2.add(distance[i]);

            x = 530;
            str = String.valueOf(stationsInfo.get(i).getDay());
            day[i] = new JLabel(str);
            day[i].setFont(new Font("Times new roman", Font.PLAIN, 12));
            day[i].setBounds(x, y, 150, 30);
            p2.add(day[i]);

            x = 600;
            str = String.valueOf(stationsInfo.get(i).getPlatform());
            platform[i] = new JLabel(str);
            platform[i].setFont(new Font("Times new roman", Font.PLAIN, 12));
            platform[i].setBounds(x, y, 150, 30);
            p2.add(platform[i]);

            y = y + 30;

        }

    }

    class Panel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            if (noOfHalts > 14)
                return new Dimension(670, 540 + 50 * (noOfHalts - 8));
            else
                return new Dimension(670, 530);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == back) {
                new HomePage(connection, name, Username).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == b1) {
                p2.removeAll();
                String trainNo = tf1.getText();
                ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                os.writeInt(8);
                os.writeUTF(trainNo);
                os.flush();
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                TrainBasicInfoAdminInfo trainDetails = (TrainBasicInfoAdminInfo) oi.readObject();
                if (trainDetails.getTrain_no() != 0) {
                    Vector<AddTrainAdminNextInfo> stationsInfo = (Vector<AddTrainAdminNextInfo>) oi.readObject();
                    showTrainDetails(trainDetails, stationsInfo);
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
