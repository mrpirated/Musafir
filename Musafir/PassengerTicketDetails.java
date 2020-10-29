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
import java.time.LocalDate;

public class PassengerTicketDetails extends JFrame implements ActionListener {
    private JLabel headLabel, pnrLabel, l1, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18,
            l19, l20, l21,l22;
    private JPanel p1, p2, panel;
    private JButton back, submit, form,getfare;
    private JTextField pnrText;
    private String Username, trainname, name, src, dest, datetime1, datetime2, trainNo, duration;
    private int noOfPassenger = 1,type,avail,srcint,destint, userid;
    private float fare;
    private JComboBox noOfPassengers;
    private JTextField[] nameOfPassenger, age;
    private JComboBox[] gender, preference;
    private JLabel[] number;
    private Connect connection;
    private LocalDate date;

    public PassengerTicketDetails( Connect connection,  String name, String trainNo, String trainname,int type, String src,int srcint,
            String dest,int destint, String datetime1, String datetime2, String duration, LocalDate date,float fare,int avail, int userid) {
        this.name = name;
        this.trainNo = trainNo;
        this.trainname = trainname;
        this.src = src;
        this.dest = dest;
        this.datetime1 = datetime1;
        this.datetime2 = datetime2;
        this.duration = duration;
        this.date = date;
        this.fare = fare;
        this.type = type;
        this.avail = avail;
        this.srcint = srcint;
        this.destint = destint;
        this.userid=userid;
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

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 230, 690, 300);
        panel.setBackground(Color.WHITE);
        add(panel);

        
        p2 = new JPanel();
        p2.setLayout(null);
        p2.setBackground(Color.WHITE);
        p2.setBounds(380, 600, 100, 30);
        add(p2);


        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/backArrow.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        back = new JButton(i3);
        back.setBackground(Color.BLACK);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        back.setBorder(emptyBorder);
        back.setBounds(5, 8, 30, 30);
        add(back);

        headLabel = new JLabel("Add Passengers Details");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 28));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(220, 10, 400, 30);
        p1.add(headLabel);

        l7 = new JLabel("FROM: ");
        l7.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l7.setForeground(Color.BLACK);
        l7.setBounds(55, 100, 400, 30);
        add(l7);

        l8 = new JLabel("TO: ");
        l8.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 18));
        l8.setForeground(Color.BLACK);
        l8.setBounds(400, 100, 400, 30);
        add(l8);

        l9 = new JLabel("SCHEDULED DEPARTURE: ");
        l9.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 15));
        l9.setForeground(Color.BLACK);
        l9.setBounds(55, 130, 400, 30);
        add(l9);

        l15 = new JLabel("SCHEDULED ARRIVAL: ");
        l15.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 15));
        l15.setForeground(Color.BLACK);
        l15.setBounds(400, 130, 400, 30);
        add(l15);

        l10 = new JLabel("TRAIN NO: ");
        l10.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        l10.setForeground(Color.BLACK);
        l10.setBounds(55, 70, 400, 30);
        add(l10);

        l14 = new JLabel(String.valueOf(trainNo));
        l14.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 20));
        l14.setForeground(Color.BLACK);
        l14.setBounds(175, 70, 400, 30);
        add(l14);

        l20 = new JLabel("TRAIN NAME: ");
        l20.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        l20.setForeground(Color.BLACK);
        l20.setBounds(400, 70, 400, 30);
        add(l20);

        l21 = new JLabel(String.valueOf(trainname));
        l21.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 20));
        l21.setForeground(Color.BLACK);
        l21.setBounds(545, 70, 400, 30);
        add(l21);

        l11 = new JLabel(src);
        l11.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 18));
        l11.setForeground(Color.BLACK);
        l11.setBounds(155, 100, 400, 30);
        add(l11);

        l12 = new JLabel(dest);
        l12.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 18));
        l12.setForeground(Color.BLACK);
        l12.setBounds(475, 100, 400, 30);
        add(l12);

        l13 = new JLabel(datetime1);
        l13.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 15));
        l13.setForeground(Color.BLACK);
        l13.setBounds(260, 130, 400, 30);
        add(l13);

        l16 = new JLabel(datetime2);
        l16.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 15));
        l16.setForeground(Color.BLACK);
        l16.setBounds(580, 130, 400, 30);
        add(l16);

        l17 = new JLabel("DURATION:");
        l17.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 15));
        l17.setForeground(Color.BLACK);
        l17.setBounds(55, 160, 400, 30);
        add(l17);

        l18 = new JLabel(duration);
        l18.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 15));
        l18.setForeground(Color.BLACK);
        l18.setBounds(150, 160, 400, 30);
        add(l18);

        l19 = new JLabel("PASSENGERS:");
        l19.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 15));
        l19.setForeground(Color.BLACK);
        l19.setBounds(400, 160, 400, 30);
        add(l19);
        String arr[] = { "1", "2", "3", "4", "5", "6" };
        noOfPassengers = new JComboBox(arr);
        noOfPassengers.setBounds(530, 165, 100, 25);
        noOfPassengers.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 15));
        add(noOfPassengers);

        int l = 190;
        l3 = new JLabel("Name Of Passenger");
        l3.setFont(new Font("Times new roman", Font.BOLD, 20));
        l3.setBounds(75, l, 400, 32);
        add(l3);

        l4 = new JLabel("Age");
        l4.setFont(new Font("Times new roman", Font.BOLD, 20));
        l4.setBounds(320, l, 400, 32);
        add(l4);

        l5 = new JLabel("Gender");
        l5.setFont(new Font("Times new roman", Font.BOLD, 20));
        l5.setBounds(430, l, 400, 32);
        add(l5);

        l6 = new JLabel("Preference");
        l6.setFont(new Font("Times new roman", Font.BOLD, 20));
        l6.setBounds(570, l, 400, 32);
        add(l6);

        l22 = new JLabel("Total Fare:");
        l22.setFont(new Font("Times new roman", Font.BOLD, 22));
        l22.setBounds(260, 600, 400, 32);
        add(l22);

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        submit.setForeground(Color.WHITE);
        submit.setBorder(emptyBorder);
        submit.setBounds(520, 650, 100, 30);
        add(submit);
        submit.setEnabled(false);

        getfare = new JButton("Get Fare");
        getfare.setBackground(Color.BLACK);
        getfare.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        getfare.setForeground(Color.WHITE);
        getfare.setBorder(emptyBorder);
        getfare.setBounds(320, 650, 100, 30);
        add(getfare);


        form = new JButton("Get Form");
        form.setBackground(Color.BLACK);
        form.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        form.setForeground(Color.WHITE);
        form.setBorder(emptyBorder);
        form.setBounds(80, 650, 100, 30);
        add(form);

        formelements();

        back.addActionListener(this);
        submit.addActionListener(this);
        form.addActionListener(this);
        getfare.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    /*public static void main(String[] args) {

        LocalDate d = LocalDate.now();
        new PassengerTicketDetails("Deepesh", "00000", "Rajdhani", "Mumbai", "Delhi", "27-10-2020 8:30",
                "28-10-2020 9:45", "8 hrs 32 min", d,1150);
    }*/

    public void formelements() {
        nameOfPassenger = new JTextField[noOfPassenger];
        age = new JTextField[noOfPassenger];
        gender = new JComboBox[noOfPassenger];
        preference = new JComboBox[noOfPassenger];

        number = new JLabel[noOfPassenger];

        Integer i = 0, y1 = 20;

        for (i = 0; i < noOfPassenger; i++) {
            String str = String.valueOf(i + 1);
            number[i] = new JLabel(str + ".");
            number[i].setFont(new Font("Times new roman", Font.BOLD, 20));
            number[i].setBounds(20, y1 - 8, 400, 32);
            panel.add(number[i]);

            Integer x1 = 55;

            nameOfPassenger[i] = new JTextField(50);
            nameOfPassenger[i].setBounds(x1, y1, 200, 25);
            nameOfPassenger[i].setFont(new Font("Times new roman", Font.BOLD, 20));
            nameOfPassenger[i].setText("");
            panel.add(nameOfPassenger[i]);

            x1 = 295;
            age[i] = new JTextField(51);
            age[i].setBounds(x1, y1, 80, 25);
            age[i].setFont(new Font("Times new roman", Font.BOLD, 20));
            age[i].setText("");
            panel.add(age[i]);

            x1 = 410;
            String genderx[] = { "Other", "Male", "Female" };
            gender[i] = new JComboBox(genderx);
            gender[i].setBounds(x1, y1, 100, 25);
            gender[i].setFont(new Font("Times new roman", Font.BOLD, 18));
            panel.add(gender[i]);

            x1 = 560;
            String preferencex[] = { "NONE", "LB", "MB", "UB", "SLB", "SUB" };
            preference[i] = new JComboBox(preferencex);
            preference[i].setBounds(x1, y1, 80, 25);
            preference[i].setFont(new Font("Times new roman", Font.BOLD, 18));
            panel.add(preference[i]);

            y1 = y1 + 50;

        }

    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            /*
             * if (ae.getSource() == back) { new BasicTicketDetails(connection, name,
             * Username, trainNo, type, src, dest, date).setVisible(true);
             * setVisible(false); }
             */
            
            if (ae.getSource() == form) {
                panel.removeAll();
                noOfPassenger = Integer.parseInt((String) noOfPassengers.getSelectedItem());
                formelements();
                panel.revalidate();
                panel.repaint();

            }
            if(ae.getSource()== getfare){
                p2.removeAll();
                float totalfare = 0;
                for (int i = 0; i < noOfPassenger; i++) {
                    String passengerNameSend = nameOfPassenger[i].getText();
                    String agex = age[i].getText();
                    String genderx = (String) gender[i].getSelectedItem();
                    int agenum= 0;
                    if(agex.equals("")||passengerNameSend.equals("")){
                        JOptionPane.showMessageDialog(null, "Fill All Details");
                        submit.setEnabled(false);
                    }
                    
                    try{
                        agenum = Integer.parseInt(agex);
                        submit.setEnabled(true);
                    }catch(Exception e){
                        submit.setEnabled(false);
                        JOptionPane.showMessageDialog(null, "Enter valid age");
                        
                    }
                    
                    if(agenum <= 12){
                        totalfare += (float)fare/2;
                    }else if(agenum > 60&&genderx.equals("Male")){
                        totalfare += (float)fare*0.6;
                    }else if(agenum > 58&&genderx.equals("Female")){
                        totalfare += (float)fare/2;
                    }
                    else totalfare += (float)fare;
                    
                    
                }
                JLabel fare = new JLabel(String.valueOf(totalfare));
                fare.setBounds(0,0,100,30);
                fare.setFont(new Font("Times new roman", Font.BOLD, 20));
                p2.add(fare);
                p2.revalidate();
                p2.repaint();
            }
            if (ae.getSource() == submit) {
                
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
                PassengersDetailForm passengerDetailForm = new PassengersDetailForm(name, trainNo,trainname, srcint,
                        destint, date, noOfPassenger,passengerInfo,type, userid);

                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(10);
                    os.writeObject(passengerDetailForm);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                BookedTicket bookedTicket= (BookedTicket) oi.readObject();
                int[][] t= bookedTicket.getSeats(); 
                System.out.println(t[0][0] +" "+t[0][1] + " " +t[0][2] );
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
