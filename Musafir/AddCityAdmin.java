package Musafir;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import Classes.*;
import java.io.*;

public class AddCityAdmin extends JFrame implements ActionListener {

    private JLabel headLabel, cityName, stationCode, number,srno;
    private JPanel p1, p2;
    private JButton back, submit,getform;
    private JTextField num;
    private JTextField[] station, code;
    private JLabel[] sr;
    private int n;
    private Panel panel;
    private JScrollPane scroll;
    private Connect connection;

    public AddCityAdmin(Connect connection) {
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
        setTitle(pad + "ADD STATIONS");

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(Color.BLACK);
        p1.setBounds(0, 0, 750, 45);
        add(p1);

        panel = new Panel();
        panel.setLayout(null);
        scroll = new JScrollPane(panel);
        scroll.setBounds(20,150,700,430);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        getContentPane().add(scroll);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/backArrow.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        back = new JButton(i3);
        back.setBackground(Color.BLACK);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        back.setBorder(emptyBorder);
        back.setBounds(5, 8, 30, 30);
        p1.add(back);

        headLabel = new JLabel("ADD STATIONS");
        headLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 30));
        headLabel.setForeground(Color.WHITE);
        headLabel.setBounds(250, 10, 400, 30);
        p1.add(headLabel);

        number = new JLabel("Number of cities to be added: ");
        number.setFont(new Font("Times new roman", Font.BOLD, 20));
        number.setBounds(180, 70, 300, 32);
        add(number);

        num = new JTextField();
        num.setFont(new Font("Times new roman", Font.BOLD, 20));
        num.setBounds(460, 70, 50, 30);
        add(num);

        srno = new JLabel("SR NO.");
        srno.setFont(new Font("Times new roman", Font.BOLD, 20));
        srno.setBounds(50, 120, 150, 32);
        add(srno);

        cityName = new JLabel("Station Name");
        cityName.setFont(new Font("Times new roman", Font.BOLD, 20));
        cityName.setBounds(250, 120, 150, 32);
        add(cityName);

        

        stationCode = new JLabel("Station Code");
        stationCode.setFont(new Font("Times new roman", Font.BOLD, 20));
        stationCode.setBounds(510, 120, 150, 32);
        add(stationCode);

        getform = new JButton("Get Form");
        getform.setBackground(Color.BLACK);
        getform.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        getform.setForeground(Color.WHITE);
        getform.setBorder(emptyBorder);
        getform.setBounds(100, 600, 100, 30);
        add(getform);

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
        submit.setForeground(Color.WHITE);
        submit.setBorder(emptyBorder);
        submit.setBounds(520, 600, 100, 30);
        add(submit);

        

        back.addActionListener(this);
        submit.addActionListener(this);
        getform.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        setSize(750, 750);
        setLocation(400, 50);
        setVisible(true);
    }
    public void form(){
        station = new JTextField[n];
        code = new JTextField[n];
        sr = new JLabel[n];
        int x,y = 20;
        for(int i=0;i<n;i++){
            x = 50;
            sr[i] =  new JLabel(String.valueOf(i+1)+".");
            sr[i].setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
            sr[i].setBounds(x,y,50,30);
            panel.add(sr[i]);

            x = 220;
            station[i] = new JTextField();
            station[i].setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
            station[i].setBounds(x,y,150,30);
            panel.add(station[i]);

            x = 480;

            code[i] = new JTextField();
            code[i].setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
            code[i].setBounds(x,y,150,30);
            panel.add(code[i]);
            y = y +50;
        }
    }
    class Panel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            if (n > 8)
                return new Dimension(670, 440 + 50 * (n- 8));
            else
                return new Dimension(670, 430);
        }
    }

    

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == back) {
                new AdminHome(connection).setVisible(true);
                setVisible(false);
            } 
            if(ae.getSource() == getform){
                panel.removeAll();
                try{
                    n = Integer.parseInt(num.getText());
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Enter valid number");
                }
                form();
                panel.revalidate();
                panel.repaint();
            }
            else if (ae.getSource() == submit) {
                
                AddCityAdminInfo[] addCityInfo = new AddCityAdminInfo[n];
                try{
                    for(int i=0;i<n;i++){
                        addCityInfo[i] = new AddCityAdminInfo(station[i].getText(), code[i].getText());
                    }
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Enter all fields");
                    
                }
                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(9);
                    os.writeObject(addCityInfo);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                String s = (String) oi.readUTF();

                if (s.equals("ok")) {
                    JOptionPane.showMessageDialog(null, "City Added Successfully.");
                    new AdminHome(connection).setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Failure, try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
