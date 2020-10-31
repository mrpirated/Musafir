package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import javax.xml.crypto.Data;

import com.mysql.jdbc.log.Slf4JLogger;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.time.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Vector;

import org.jdesktop.swingx.JXDatePicker;

import Classes.AvailabilityInfo;
import Classes.ScheduleEnq;

public class PlanMyJourney extends JFrame implements ActionListener {
    private JPanel p1, panel;
    private JButton back, submit;
    private JComboBox from, to;
    private JXDatePicker picker;
    private String name;
    private int userid;
    private Connect connection;
    private int trains = 0;
    private Panel availability;
    private JScrollPane scroll;
    private JLabel[] arrival, departure, trainName, train, srno, day1, day2, duration;
    private JButton[] sl, ac, getFare;
    private float[] fare;
    private int[] srcint, destint;
    private String[] trainNo;
    private java.util.Date dt = new java.util.Date();
    

    PlanMyJourney(Connect connection, String name, String[][] cities, int userid) {
        this.name = name;
        this.userid = userid;
        this.connection = connection;
        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("PLAN MY JOURNEY");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "PLAN MY JOURNEY");

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

        JLabel l2 = new JLabel("PLAN MY JOURNEY");
        l2.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        l2.setForeground(Color.WHITE);
        l2.setBounds(220, 10, 400, 30);
        p1.add(l2);

        JLabel l5 = new JLabel("Fill the details");
        l5.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        l5.setForeground(Color.BLACK);
        l5.setBounds(300, 50, 400, 60);
        add(l5);

        JLabel l3 = new JLabel("From");
        l3.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        l3.setForeground(Color.BLACK);
        l3.setBounds(100, 100, 100, 30);
        add(l3);

        JLabel l4 = new JLabel("To");
        l4.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        l4.setForeground(Color.BLACK);
        l4.setBounds(375, 100, 100, 30);
        add(l4);

        from = new JComboBox(cities[0]);
        from.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        from.setForeground(Color.BLACK);
        from.setBounds(35, 135, 200, 30);
        add(from);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        to = new JComboBox(cities[0]);
        to.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        to.setForeground(Color.BLACK);
        to.setBounds(300, 135, 200, 30);
        add(to);

        JLabel l6 = new JLabel("Date");
        l6.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        l6.setForeground(Color.BLACK);
        l6.setBounds(600, 100, 100, 30);
        add(l6);

        panel = new JPanel();
        picker = new JXDatePicker();
        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        panel.setBackground(Color.white);
        panel.setBounds(550, 135, 150, 30);
        panel.add(picker);
        add(panel);

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        submit.setForeground(Color.WHITE);
        submit.setBorder(emptyBorder);
        submit.setBounds(300, 650, 100, 30);
        add(submit);

        availability = new Panel();
        availability.setLayout(null);

        scroll = new JScrollPane(availability);
        scroll.setBounds(30, 175, 690, 450);
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

    public void showTrains(Vector<AvailabilityInfo> availabilityInfo) {
        trains = availabilityInfo.size();
        int diff;
        if (trains == 0) {
            JLabel notrains = new JLabel("SORRY!! NO TRAINS AVAILABLE");
            notrains.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
            notrains.setForeground(Color.gray);
            notrains.setBounds(150, 200, 400, 30);
            availability.add(notrains);
            return;
        }

        JLabel srnoLabel = new JLabel("| Sr No  |");
        srnoLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        srnoLabel.setBounds(5, 5, 100, 30);
        availability.add(srnoLabel);

        JLabel trainLable = new JLabel("Train No  |");
        trainLable.setFont(new Font("Times new roman", Font.BOLD, 18));
        trainLable.setBounds(80, 5, 100, 30);
        availability.add(trainLable);

        JLabel trainNameLable = new JLabel("Train Name        |");
        trainNameLable.setFont(new Font("Times new roman", Font.BOLD, 18));
        trainNameLable.setBounds(210, 5, 200, 30);
        availability.add(trainNameLable);

        JLabel arrivalLabel = new JLabel("Dep   |");
        arrivalLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        arrivalLabel.setBounds(370, 5, 100, 30);
        availability.add(arrivalLabel);

        JLabel departLabel = new JLabel("Arr    |");
        departLabel.setFont(new Font("Times new roman", Font.BOLD, 18));
        departLabel.setBounds(440, 5, 100, 30);
        availability.add(departLabel);

        JLabel SLseats = new JLabel("SL      |");
        SLseats.setFont(new Font("Times new roman", Font.BOLD, 18));
        SLseats.setBounds(530, 5, 100, 30);
        availability.add(SLseats);

        JLabel ACseats = new JLabel("AC      |");
        ACseats.setFont(new Font("Times new roman", Font.BOLD, 18));
        ACseats.setBounds(620, 5, 100, 30);
        availability.add(ACseats);

        arrival = new JLabel[trains];
        departure = new JLabel[trains];
        trainName = new JLabel[trains];
        train = new JLabel[trains];
        srno = new JLabel[trains];
        day1 = new JLabel[trains];
        day2 = new JLabel[trains];
        duration = new JLabel[trains];
        sl = new JButton[trains];
        ac = new JButton[trains];
        fare = new float[trains];
        trainNo = new String[trains];
        srcint = new int[trains];
        destint = new int[trains];
        getFare = new JButton[trains];

        String str;
        int seats;

        int x, y = 50;
        for (int i = 0; i < trains; i++) {
            diff = availabilityInfo.get(i).getDay2() - availabilityInfo.get(i).getDay1();
            x = 35;
            str = String.valueOf(i + 1);
            srno[i] = new JLabel(str);
            srno[i].setFont(new Font("Times new roman", Font.BOLD, 18));
            srno[i].setBounds(x, y, 30, 30);
            availability.add(srno[i]);

            x = 90;
            str = String.valueOf(availabilityInfo.get(i).getTrain());
            trainNo[i] = str;
            train[i] = new JLabel(str);
            train[i].setFont(new Font("Times new roman", Font.BOLD, 18));
            train[i].setBounds(x, y, 50, 30);
            availability.add(train[i]);

            x = 180;
            str = String.valueOf(availabilityInfo.get(i).getTrainName());
            trainName[i] = new JLabel(str);
            trainName[i].setFont(new Font("Times new roman", Font.BOLD, 18));
            trainName[i].setBounds(x, y, 150, 30);
            availability.add(trainName[i]);

            x = 370;
            str = timestr(availabilityInfo.get(i).getDeparture());
            departure[i] = new JLabel(str);
            departure[i].setFont(new Font("Times new roman", Font.BOLD, 15));
            departure[i].setBounds(x, y, 150, 30);
            availability.add(departure[i]);

            str = String.valueOf(availabilityInfo.get(i).getDate().plusDays(availabilityInfo.get(i).getDay1()-1));
            day1[i] = new JLabel(str);
            day1[i].setFont(new Font("Times new roman", Font.BOLD, 12));
            day1[i].setBounds(x - 20, y - 20, 150, 30);
            availability.add(day1[i]);

            str = durationofjourney(availabilityInfo.get(i).getArrival(), availabilityInfo.get(i).getDeparture(), diff);
            duration[i] = new JLabel(str);
            duration[i].setFont(new Font("Times new roman", Font.BOLD, 12));
            duration[i].setBounds(380, y + 20, 150, 30);
            availability.add(duration[i]);

            x = 440;
            str = timestr(availabilityInfo.get(i).getArrival());
            arrival[i] = new JLabel(str);
            arrival[i].setFont(new Font("Times new roman", Font.BOLD, 15));
            arrival[i].setBounds(x, y, 150, 30);
            availability.add(arrival[i]);

            str = String.valueOf((availabilityInfo.get(i).getDate()).plusDays(availabilityInfo.get(i).getDay2()-1));
            day2[i] = new JLabel(str);
            day2[i].setFont(new Font("Times new roman", Font.BOLD, 12));
            day2[i].setBounds(x - 20, y - 20, 150, 30);
            availability.add(day2[i]);

            x = 500;
            seats = availabilityInfo.get(i).getSl();
            str = String.valueOf(Math.abs(seats));
            sl[i] = new JButton(str);
            sl[i].setFont(new Font("Times new roman", Font.BOLD, 15));
            sl[i].setBounds(x, y - 10, 70, 20);
            if (seats < 0) {
                sl[i].setBackground(Color.RED);
                sl[i].setForeground(Color.white);
            } else
                sl[i].setBackground(Color.GREEN);
            availability.add(sl[i]);
            sl[i].addActionListener(this);

            x = 600;
            seats = availabilityInfo.get(i).getAc();
            str = String.valueOf(Math.abs(seats));
            ac[i] = new JButton(str);
            ac[i].setFont(new Font("Times new roman", Font.BOLD, 15));
            ac[i].setBounds(x, y - 10, 70, 20);
            if (seats < 0) {
                ac[i].setBackground(Color.RED);
                ac[i].setForeground(Color.white);
            } else
                ac[i].setBackground(Color.GREEN);
            availability.add(ac[i]);
            ac[i].addActionListener(this);

            x = 515;
            getFare[i] = new JButton("Get Fare");
            getFare[i].setFont(new Font("Times new roman", Font.BOLD, 18));
            getFare[i].setBounds(x, y + 15, 140, 20);
            getFare[i].setBackground(Color.BLACK);
            getFare[i].setForeground(Color.WHITE);
            availability.add(getFare[i]);
            getFare[i].addActionListener(this);

            y = y + 50;
            fare[i] = availabilityInfo.get(i).getFare();
            srcint[i] = availabilityInfo.get(i).getSrcint();
            destint[i] = availabilityInfo.get(i).getDestint();

        }

    }

    public String durationofjourney(Timestamp arr, Timestamp dep, int days) {
        long time = days * 86400 + (arr.getTime() - dep.getTime()) / 1000;
        time = time / 60;
        int hrs = (int) time / 60;
        int min = (int) time % 60;
        String s = hrs + " hrs " + min + " min";
        return s;
    }

    public String timestr(Timestamp t) {
        String s;
        if (t.getMinutes() < 10)
            s = t.getHours() + ":0" + t.getMinutes();
        else
            s = t.getHours() + ":" + t.getMinutes();

        return s;
    }

    class Panel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            if (trains > 8)
                return new Dimension(670, 440 + 50 * (trains - 8));
            else
                return new Dimension(670, 430);
        }
    }

    /*
     * public static void main(String[] args) { String[][] s=
     * {{"as","cs"},{"dfs","dsfs"}}; new PlanMyJourney("asa",s,"adas"); }
     */
    Vector<AvailabilityInfo> availabilityInfo = null;
    public void actionPerformed(ActionEvent ae) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        
        try {

            if (ae.getSource() == back) {
                new HomePage(connection, name, userid).setVisible(true);
                setVisible(false);
            }

            else if (ae.getSource() == submit) {
                availability.removeAll();
                String source = (String) from.getSelectedItem();
                String dest = (String) to.getSelectedItem();
                dt = picker.getDate();
                System.out.println(dt);
                String d = df.format(dt);
                Date date = Date.valueOf(d);
                ScheduleEnq scheduleEnq = new ScheduleEnq(source, dest, date);
                ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                os.writeInt(5);
                os.writeObject(scheduleEnq);
                os.flush();
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                
                availabilityInfo = (Vector<AvailabilityInfo>) oi.readObject();
                showTrains(availabilityInfo);
                availability.revalidate();
                availability.repaint();
                System.out.println(date);

            }
            for (int i = 0; i < trains; i++) {
                if (ae.getSource() == sl[i]) {
                    new PassengerTicketDetails(connection, name, train[i].getText(), trainName[i].getText(), 1,
                            (String) from.getSelectedItem(), srcint[i], (String) to.getSelectedItem(), destint[i],
                            day1[i].getText() + " " + departure[i].getText(),
                            day2[i].getText() + " " + arrival[i].getText(), duration[i].getText(),
                            availabilityInfo.get(i).getDate(), fare[i], Integer.parseInt(sl[i].getText()),
                            userid,availabilityInfo.get(i).getDay1()).setVisible(true);

                } else if (ae.getSource() == ac[i]) {
                    new PassengerTicketDetails(connection, name, train[i].getText(), trainName[i].getText(), 2,
                            (String) from.getSelectedItem(), srcint[i], (String) to.getSelectedItem(), destint[i],
                            day1[i].getText() + " " + departure[i].getText(),
                            day2[i].getText() + " " + arrival[i].getText(), duration[i].getText(),
                            availabilityInfo.get(i).getDate(), fare[i], Integer.parseInt(ac[i].getText()),
                            userid,availabilityInfo.get(i).getDay1()).setVisible(true);
                } else if (ae.getSource() == getFare[i]) {
                    new GetFareClient(connection, name, fare[i], userid, trainNo[i]).setVisible(true);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
