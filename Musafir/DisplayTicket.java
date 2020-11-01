package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.lang.System.Logger;
import java.awt.image.BufferedImage;

public class DisplayTicket extends JFrame implements ActionListener {
    private JLabel PNR, seat, berth, name, trainno, trainname, quota, date, fare, musafir, dest, src, age, gender, srno,
            status;
    private JPanel panel;
    private JButton print;

    public DisplayTicket(BookedTicket bookedTicket, PassengersDetailForm passengersDetailForm) {
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
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        panel.setBounds(0, 0, 750, 650);
        add(panel);

        musafir = new JLabel("MUSAFIR e-Ticket");
        musafir.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 40));
        musafir.setForeground(Color.BLACK);
        musafir.setBounds(190, 20, 500, 30);
        panel.add(musafir);

        trainno = new JLabel("Train No: ");
        trainno.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        trainno.setForeground(Color.BLACK);
        trainno.setBounds(50, 100, 150, 30);
        panel.add(trainno);

        trainno = new JLabel("12487");
        trainno.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        trainno.setForeground(Color.BLACK);
        trainno.setBounds(170, 100, 120, 30);
        panel.add(trainno);

        trainname = new JLabel("Train Name: ");
        trainname.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        trainname.setForeground(Color.BLACK);
        trainname.setBounds(370, 100, 200, 30);
        panel.add(trainname);

        trainname = new JLabel("Mumbai Rajdhani");
        trainname.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        trainname.setForeground(Color.BLACK);
        trainname.setBounds(540, 100, 200, 30);
        panel.add(trainname);

        PNR = new JLabel("PNR :");
        PNR.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        PNR.setForeground(Color.BLACK);
        PNR.setBounds(50, 140, 200, 30);
        panel.add(PNR);

        PNR = new JLabel("1547896324");
        PNR.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        PNR.setForeground(Color.BLACK);
        PNR.setBounds(120, 140, 200, 30);
        panel.add(PNR);

        quota = new JLabel("Quota :");
        quota.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        quota.setForeground(Color.BLACK);
        quota.setBounds(370, 140, 200, 30);
        panel.add(quota);

        quota = new JLabel("General");
        quota.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        quota.setForeground(Color.BLACK);
        quota.setBounds(470, 140, 200, 30);
        panel.add(quota);

        fare = new JLabel("Fare :");
        fare.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        fare.setForeground(Color.BLACK);
        fare.setBounds(50, 180, 200, 30);
        panel.add(fare);

        fare = new JLabel("1248.21");
        fare.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        fare.setForeground(Color.BLACK);
        fare.setBounds(130, 180, 200, 30);
        panel.add(fare);

        date = new JLabel("Date Of Journey:");
        date.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        date.setForeground(Color.BLACK);
        date.setBounds(370, 180, 200, 30);
        panel.add(date);

        date = new JLabel("23/12/2001");
        date.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        date.setForeground(Color.BLACK);
        date.setBounds(570, 180, 200, 30);
        panel.add(date);

        src = new JLabel("Source: ");
        src.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        src.setForeground(Color.BLACK);
        src.setBounds(50, 220, 200, 30);
        panel.add(src);

        src = new JLabel("LokmanyaTilak T ");
        src.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        src.setForeground(Color.BLACK);
        src.setBounds(130, 220, 200, 30);
        panel.add(src);

        src = new JLabel("Departure: ");
        src.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        src.setForeground(Color.BLACK);
        src.setBounds(50, 260, 200, 30);
        panel.add(src);

        src = new JLabel("08:56 on 24/12/2001");
        src.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        src.setForeground(Color.BLACK);
        src.setBounds(160, 260, 200, 30);
        panel.add(src);

        dest = new JLabel("Destination: ");
        dest.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        dest.setForeground(Color.BLACK);
        dest.setBounds(370, 220, 200, 30);
        panel.add(dest);

        dest = new JLabel("Chennai Central ");
        dest.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        dest.setForeground(Color.BLACK);
        dest.setBounds(500, 220, 200, 30);
        panel.add(dest);

        dest = new JLabel("Arrival: ");
        dest.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        dest.setForeground(Color.BLACK);
        dest.setBounds(370, 260, 200, 30);
        panel.add(dest);

        dest = new JLabel("08:56 on 24/12/2001");
        dest.setFont(new Font("TIMES NEW ROMAN", Font.PLAIN, 22));
        dest.setForeground(Color.BLACK);
        dest.setBounds(460, 260, 200, 30);
        panel.add(dest);

        srno = new JLabel("SrNo");
        srno.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        srno.setForeground(Color.BLACK);
        srno.setBounds(50, 300, 200, 30);
        panel.add(srno);

        name = new JLabel("Name");
        name.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        name.setForeground(Color.BLACK);
        name.setBounds(150, 300, 200, 30);
        panel.add(name);

        age = new JLabel("Age");
        age.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        age.setForeground(Color.BLACK);
        age.setBounds(310, 300, 200, 30);
        panel.add(age);

        gender = new JLabel("Gender");
        gender.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        gender.setForeground(Color.BLACK);
        gender.setBounds(360, 300, 200, 30);
        panel.add(gender);

        seat = new JLabel("Seat");
        seat.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        seat.setForeground(Color.BLACK);
        seat.setBounds(450, 300, 200, 30);
        panel.add(seat);

        berth = new JLabel("Berth");
        berth.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        berth.setForeground(Color.BLACK);
        berth.setBounds(520, 300, 200, 30);
        panel.add(berth);

        status = new JLabel("Status");
        status.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
        status.setForeground(Color.BLACK);
        status.setBounds(600, 300, 200, 30);
        panel.add(status);
        int x1 = 70, y1 = 350;
        for (int i = 0; i < passengersDetailForm.getPassengerInfo().length; i++) {

            srno = new JLabel(String.valueOf(i + 1) + ".");
            srno.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
            srno.setForeground(Color.BLACK);
            srno.setBounds(70, y1, 200, 30);
            panel.add(srno);

            name = new JLabel(bookedTicket.getName()[i]);
            name.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
            name.setForeground(Color.BLACK);
            name.setBounds(110, y1, 200, 30);
            panel.add(name);

            age = new JLabel(String.valueOf(passengersDetailForm.getPassengerInfo()[i].getAge()));
            age.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
            age.setForeground(Color.BLACK);
            age.setBounds(320, y1, 200, 30);
            panel.add(age);

            gender = new JLabel(String.valueOf(passengersDetailForm.getPassengerInfo()[i].getGender()));
            gender.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
            gender.setForeground(Color.BLACK);
            gender.setBounds(380, y1, 200, 30);
            panel.add(gender);

            if (bookedTicket.getCoach()[i] != 0) {
                if (bookedTicket.getType() == 1) {
                    seat = new JLabel(
                            String.valueOf("S" + bookedTicket.getCoach()[i] + "/" + bookedTicket.getSeats()[i]));
                } else
                    seat = new JLabel(
                            String.valueOf("B" + bookedTicket.getCoach()[i] + "/" + bookedTicket.getSeats()[i]));
                
                if (bookedTicket.getSeats()[i] % 8 == 1 || bookedTicket.getSeats()[i] % 8 == 4) {
                    berth = new JLabel("LB");
                } else if (bookedTicket.getSeats()[i] % 8 == 2 || bookedTicket.getSeats()[i] % 8 == 5) {
                    berth = new JLabel("MB");
                } else if (bookedTicket.getSeats()[i] % 8 == 3 || bookedTicket.getSeats()[i] % 8 == 6) {
                    berth = new JLabel("UB");
                } else if (bookedTicket.getSeats()[i] % 8 == 7) {
                    berth = new JLabel("SLB");
                } else {
                    berth = new JLabel("SUB");
                }
                

                status = new JLabel("CNF");
                status.setForeground(Color.GREEN);
                
            }else{
                seat = new JLabel("--");
                berth = new JLabel("--");
                status = new JLabel("WL/" +bookedTicket.getSeats()[i]);
                status.setForeground(Color.RED);
            }
            seat.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
                seat.setForeground(Color.BLACK);
                seat.setBounds(440, y1, 200, 30);
                panel.add(seat);

                berth.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
                berth.setForeground(Color.BLACK);
                berth.setBounds(530, y1, 200, 30);
                panel.add(berth);

                status.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 22));
                
                status.setBounds(600, y1, 200, 30);
                panel.add(status);
            y1 = y1 + 50;
        }
        print = new JButton("Print");
        print.setBackground(Color.BLACK);
        print.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        print.setForeground(Color.WHITE);

        print.setBounds(300, 650, 100, 30);
        add(print);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    // public static void main(String[] args) {
    // new DisplayTicket().setVisible(true);

    // }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == print) {

        }

    }

}
