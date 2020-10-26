package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;

public class RemoveTrainAdmin extends JFrame implements ActionListener {

    private JLabel headLabel, pnrLabel, trainNo, infoLabel;
    private JPanel p1, p2, panel;
    private JButton back, submit;
    private JTextField pnrText, tf1;
    private String name, Username;
    private Connect connection;

    public RemoveTrainAdmin(Connect connection) {
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
        setTitle(pad + "REMOVE TRAIN");

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

        headLabel = new JLabel("REMOVE TRAIN");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(250, 10, 400, 30);
        p1.add(headLabel);

        infoLabel = new JLabel("Fill The Train No. To Be Removed");
        infoLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 27));
        infoLabel.setForeground(Color.BLACK);
        infoLabel.setBounds(160, 90, 600, 30);
        add(infoLabel);

        trainNo = new JLabel("Train No:");
        trainNo.setFont(new Font("Times new roman", Font.BOLD, 20));
        trainNo.setBounds(200, 180, 150, 32);
        add(trainNo);

        tf1 = new JTextField(7);
        tf1.setFont(new Font("Times new roman", Font.BOLD, 14));
        tf1.setBounds(300, 180, 150, 30);
        add(tf1);

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

            if (ae.getSource() == back) {
                new AdminHome(connection).setVisible(true);
                setVisible(false);
            } else if (ae.getSource() == submit) {
                String trainNoSend = trainNo.getText();

                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(9);
                    os.writeUTF(trainNoSend);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                String s = (String) oi.readUTF();

                if (s.equals("ok")) {
                    JOptionPane.showMessageDialog(null, "Train Removed Successfully.");
                    new AdminHome(connection).setVisible(true);
                    setVisible(false);
                } else {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
