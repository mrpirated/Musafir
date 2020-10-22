package Musafir;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import Classes.*;

public class LoginAdmin extends JFrame implements ActionListener {
    JLabel l1, l2, l3;
    JTextField tf1;
    JPasswordField pf2;
    JButton b1, b2;

    LoginAdmin() {

        // Move the text to the center

        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("ADMIN LOGIN DIALOG BOX");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        // for (int i=0; i!=w; i++) pad +=" ";
        pad = String.format("%" + w * 0.8 + "s", pad);
        setTitle(pad + "ADMIN LOGIN DIALOG BOX");

        l1 = new JLabel("ADMIN LOGIN");
        l1.setFont(new Font("Times new roman", Font.BOLD, 38));

        l2 = new JLabel("USER ID:");
        l2.setFont(new Font("Times new roman", Font.BOLD, 28));

        l3 = new JLabel("PASSWORD:");
        l3.setFont(new Font("Times new roman", Font.BOLD, 28));

        tf1 = new JTextField(15);
        pf2 = new JPasswordField(15);

        b1 = new JButton("LOG IN");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);

        b2 = new JButton("CLEAR");
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);

        setLayout(null);

        l1.setBounds(100, 10, 400, 32);
        add(l1);

        l2.setBounds(25, 75, 150, 32);
        add(l2);

        tf1.setBounds(200, 75, 230, 30);
        add(tf1);

        l3.setBounds(25, 150, 200, 32);
        add(l3);

        tf1.setFont(new Font("Times new roman", Font.BOLD, 14));

        pf2.setFont(new Font("Times new roman", Font.BOLD, 14));
        pf2.setBounds(200, 150, 230, 30);
        add(pf2);

        b1.setFont(new Font("Times new roman", Font.BOLD, 14));
        b1.setBounds(25, 225, 200, 30);
        add(b1);

        b2.setFont(new Font("Times new roman", Font.BOLD, 14));
        b2.setBounds(275, 225, 200, 30);
        add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);

        setSize(500, 400);
        setLocation(500, 250);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == b1) {
                String username = tf1.getText();
                char[] password = pf2.getPassword();
                LoginInfo admin = new LoginInfo(username, password);
                try {

                    ObjectOutputStream os = new ObjectOutputStream(Connect.socket.getOutputStream());
                    os.writeInt(4);
                    os.writeObject(admin);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(Connect.socket.getInputStream());
                String s = (String) oi.readUTF();
                if (s.equals("")) {

                    JOptionPane.showMessageDialog(null, "Username or Password entered is Incorrect");
                } else {
                    new AdminHome().setVisible(true);
                    setVisible(false);
                }

            } else if (ae.getSource() == b2) {
                tf1.setText("");
                pf2.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new LoginAdmin().setVisible(true);
    }

}
