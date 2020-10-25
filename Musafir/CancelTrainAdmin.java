package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.jdesktop.swingx.JXDatePicker;
import java.text.ParseException;
import java.util.Date;

public class CancelTrainAdmin extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, infoLabel, startDate, endDate, trainNo;
    private JPanel p1, p2, panel, startDatePanel, endDatePanel;
    private JButton back, submit;
    private JTextField pnrText, tf1;
    private JXDatePicker picker, picker1;
    private Connect connection;
    public CancelTrainAdmin(Connect connection) {
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
        setTitle(pad + "CANCEL TRAIN");

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

        headLabel = new JLabel("CANCEL TRAIN");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(250, 10, 400, 30);
        p1.add(headLabel);

        infoLabel = new JLabel("Fill Following Information");
        infoLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 27));
        infoLabel.setForeground(Color.BLACK);
        infoLabel.setBounds(220, 90, 400, 30);
        add(infoLabel);

        trainNo = new JLabel("Train No:");
        trainNo.setFont(new Font("Times new roman", Font.BOLD, 20));
        trainNo.setBounds(200, 180, 150, 32);
        add(trainNo);

        tf1 = new JTextField(7);
        tf1.setFont(new Font("Times new roman", Font.BOLD, 14));
        tf1.setBounds(300, 180, 150, 30);
        add(tf1);

        startDate = new JLabel("From:");
        startDate.setFont(new Font("Times new roman", Font.BOLD, 20));
        startDate.setBounds(70, 250, 200, 32);
        add(startDate);

        startDatePanel = new JPanel();
        picker = new JXDatePicker();
        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        startDatePanel.setBackground(Color.white);
        startDatePanel.setBounds(40, 290, 150, 30);
        startDatePanel.add(picker);
        add(startDatePanel);

        endDate = new JLabel("To:");
        endDate.setFont(new Font("Times new roman", Font.BOLD, 20));
        endDate.setBounds(500, 250, 200, 32);
        add(endDate);

        endDatePanel = new JPanel();
        picker1 = new JXDatePicker();
        picker1.setDate(Calendar.getInstance().getTime());
        picker1.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        endDatePanel.setBackground(Color.white);
        endDatePanel.setBounds(470, 290, 150, 30);
        endDatePanel.add(picker1);
        add(endDatePanel);

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        submit.setForeground(Color.WHITE);
        submit.setBorder(emptyBorder);
        submit.setBounds(300, 500, 100, 30);
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

            SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");

            if (ae.getSource() == back) {
                new AdminHome(connection).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == submit) {
                String trainNo = tf1.getText();
                String from = formater.format(picker.getDate());
                String to = formater.format(picker1.getDate());
                Date dateFrom = formater.parse(from);
                Date dateTo = formater.parse(to);

                CancelTrainAdminInfo cancelRequest = new CancelTrainAdminInfo(trainNo, dateFrom, dateTo);

                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(5);
                    os.writeObject(cancelRequest);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                String s = (String) oi.readUTF();
                if (s.equals("")) {
                    JOptionPane.showMessageDialog(null, "Username or Password entered is Incorrect");
                } else {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
