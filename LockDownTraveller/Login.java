package LockDownTraveller;

import java.awt.*;
import javax.swing.*;

public class Login extends JFrame {
    JLabel l1, l2, l3;
    JTextField tf1;
    JPasswordField pf2;
    JButton b1, b2, b3;

    Login() {

        // Move the text to the center

        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("LOGIN DIALOG BOX");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        // for (int i=0; i!=w; i++) pad +=" ";
        pad = String.format("%" + w*1.3 + "s", pad);
        setTitle(pad + "LOGIN DIALOG BOX");

        l1 = new JLabel("LOGIN");
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

        b3 = new JButton("SIGN UP");
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);

        setLayout(null);

        l1.setBounds(175, 10, 200, 32);
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
        b1.setBounds(300, 400, 100, 30);
        add(b1);

        b2.setFont(new Font("Times new roman", Font.BOLD, 14));
        b2.setBounds(430, 400, 100, 30);
        add(b2);

        b3.setFont(new Font("Times new roman", Font.BOLD, 14));
        b3.setBounds(300, 450, 230, 30);
        add(b3);

        getContentPane().setBackground(Color.WHITE);

        setSize(500, 300);
        setLocation(500, 250);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }

}
