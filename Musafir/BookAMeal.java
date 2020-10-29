package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;
import java.util.*;

public class BookAMeal extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel;
    private JPanel p1, p2, panel;
    private JButton back, submit;
    private JTextField pnrText;
    private String name, Username;
    private Connect connection;
    private JComboBox pnrListBox;
    private int userid;
    private Vector<String> pnrList = new Vector<String>();

    public BookAMeal(Connect connection, String name, int userid, Vector<String> pnrList) {
        this.name = name;
        this.userid=userid;
        this.connection = connection;
        this.pnrList = pnrList;

        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN HOME PAGE");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 2.5 + "s", pad);
        setTitle(pad + "BOOK A MEAL");

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

        headLabel = new JLabel("BOOK A MEAL");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(250, 10, 400, 30);
        p1.add(headLabel);

        pnrLabel = new JLabel("Train No:");
        pnrLabel.setFont(new Font("Times new roman", Font.BOLD, 20));
        pnrLabel.setBounds(200, 180, 150, 32);
        add(pnrLabel);

        pnrListBox = new JComboBox(pnrList);
        pnrListBox.setFont(new Font("Times new roman", Font.BOLD, 14));
        pnrListBox.setBounds(320, 180, 300, 30);
        add(pnrListBox);

        submit = new JButton("Book Meal");
        submit.setBackground(Color.BLACK);
        submit.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        submit.setForeground(Color.WHITE);
        submit.setBorder(emptyBorder);
        submit.setBounds(300, 500, 200, 30);
        add(submit);

        back.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            String selectedPNR = (String) pnrListBox.getSelectedItem();
            if (ae.getSource() == back) {
                new HomePage(connection, name, userid).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == submit) {
                try {
                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(14);
                    os.writeUTF(selectedPNR);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                String s = (String) oi.readUTF();
                if (s.equals("ok")) {
                    JOptionPane.showMessageDialog(null, "Meal Booked Successfully for the PNR: " + selectedPNR
                            + ". Payment is to be done at the time of journey.");
                    new AdminHome(connection).setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Request Not processed. Try Again.");

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
