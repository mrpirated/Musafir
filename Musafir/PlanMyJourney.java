package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.jdesktop.swingx.JXDatePicker;

public class PlanMyJourney extends JFrame implements ActionListener {
    JPanel p1, p2, panel;
    JButton back, b1, b2, b3, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, submit;
    JComboBox from, to;
    JPasswordField pf2;
    private String name;

    PlanMyJourney(String name,String[][] cities) {
        this.name = name;

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
        l5.setBounds(270, 80, 400, 60);
        add(l5);

        JLabel l3 = new JLabel("From");
        l3.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        l3.setForeground(Color.BLACK);
        l3.setBounds(150, 170, 100, 30);
        add(l3);

        JLabel l4 = new JLabel("To");
        l4.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        l4.setForeground(Color.BLACK);
        l4.setBounds(520, 170, 100, 30);
        add(l4);

        from = new JComboBox(cities[0]);
        from.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        from.setForeground(Color.BLACK);
        from.setBounds(110, 220, 200, 30);
        add(from);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        to = new JComboBox(cities[0]);
        to.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        to.setForeground(Color.BLACK);
        to.setBounds(470, 220, 200, 30);
        add(to);

        JLabel l6 = new JLabel("Date");
        l6.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
        l6.setForeground(Color.BLACK);
        l6.setBounds(350, 300, 100, 30);
        add(l6);

        panel = new JPanel();
        JXDatePicker picker = new JXDatePicker();
        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        panel.setBackground(Color.white);
        panel.setBounds(300, 350, 150, 30);
        panel.add(picker);
        add(panel);

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        submit.setForeground(Color.WHITE);
        submit.setBorder(emptyBorder);
        submit.setBounds(300, 600, 100, 30);
        add(submit);

        back.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == back) {
                new HomePage(name).setVisible(true);
                setVisible(false);
            }

            else if (ae.getSource() == submit) {
                
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
