package Musafir;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import Classes.*;

public class SignUp extends JFrame implements ActionListener{

    public static String monthtonum(String month){
        if(month.equals("January"))
        return "01";
        if(month.equals("February"))
        return "02";
        if(month.equals("March"))
        return "03";
        if(month.equals("April"))
        return "04";
        if(month.equals("May"))
        return "05";
        if(month.equals("June"))
        return "06";
        if(month.equals("July"))
        return "07";
        if(month.equals("August"))
        return "08";
        if(month.equals("September"))
        return "09";
        if(month.equals("October"))
        return "10";
        if(month.equals("November"))
        return "11";
        if(month.equals("December"))
        return "12";

        return "0";

        
    }

    JLabel head, name, date, phone, dob, month, year, gender, email, pass, cnfpass, cnfpass2;
    JTextField namet, emailt, phonet;
    JPasswordField passt,cnfpasst;
    JRadioButton m, o, fl;
    JComboBox dd, mm, yy;
    JButton done,back;

    SignUp() {
        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("LOCKDOWN TRAVELLER");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 1.7 + "s", pad);
        setTitle(pad + "LOCKDOWN TRAVELLER");

        setLayout(null);

        head = new JLabel("SIGNUP");
        head.setFont(new Font("Times new Roman", Font.BOLD, 38));
        head.setBounds(265, 66, 460, 32);
        add(head);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Musafir/icons/mainicon.png"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(25, 25, 100, 100);
        add(l1);
        JLabel l2 = new JLabel(i3);
        l2.setBounds(560, 25, 100, 100);
        add(l2);

        name = new JLabel("NAME");
        name.setFont(new Font("Times new Roman", Font.BOLD, 20));
        name.setBounds(30, 150, 100, 32);
        add(name);

        namet = new JTextField();
        namet.setFont(new Font("Times new Roman", Font.CENTER_BASELINE, 14));
        namet.setBounds(200, 150, 400, 25);
        add(namet);

        dob = new JLabel("DATE OF BIRTH");
        dob.setFont(new Font("Times new Roman", Font.BOLD, 20));
        dob.setBounds(30, 200, 300, 32);
        add(dob);

        date = new JLabel("Date");
        date.setFont(new Font("Times new Roman", Font.BOLD, 18));
        date.setBounds(200, 197, 75, 32);
        add(date);

        String date[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", };
        dd = new JComboBox(date);
        dd.setBackground(Color.WHITE);
        dd.setBounds(245, 200, 40, 25);
        add(dd);

        month = new JLabel("Month");
        month.setFont(new Font("Times new Roman", Font.BOLD, 18));
        month.setBounds(300, 197, 100, 32);
        add(month);

        String month[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        mm = new JComboBox(month);
        mm.setBackground(Color.WHITE);
        mm.setBounds(365, 200, 120, 25);
        add(mm);

        year = new JLabel("Year");
        year.setFont(new Font("Times new Roman", Font.BOLD, 18));
        year.setBounds(500, 197, 100, 32);
        add(year);

        String year[] = { "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984",
                "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997",
                "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2007", "2008", "2009", "2010" };
        yy = new JComboBox(year);
        yy.setBackground(Color.WHITE);
        yy.setBounds(550, 200, 60, 25);
        add(yy);

        gender = new JLabel("GENDER");
        gender.setFont(new Font("Times new Roman", Font.BOLD, 20));
        gender.setBounds(30, 250, 100, 32);
        add(gender);

        m = new JRadioButton("Male");
        m.setFont(new Font("Times new Roman", Font.BOLD, 18));
        m.setBackground(Color.WHITE);
        m.setBounds(200, 250, 75, 32);
        add(m);

        fl = new JRadioButton("Female");
        fl.setFont(new Font("Times new Roman", Font.BOLD, 18));
        fl.setBackground(Color.WHITE);
        fl.setBounds(300, 250, 100, 32);
        add(fl);

        o = new JRadioButton("Other");
        o.setFont(new Font("Times new Roman", Font.BOLD, 18));
        o.setBackground(Color.WHITE);
        o.setBounds(420, 250, 75, 32);
        add(o);

        email = new JLabel("EMAIL");
        email.setFont(new Font("Times new Roman", Font.BOLD, 20));
        email.setBounds(30, 300, 100, 32);
        add(email);

        emailt = new JTextField();
        emailt.setFont(new Font("Times new Roman", Font.CENTER_BASELINE, 14));
        emailt.setBounds(200, 300, 400, 25);
        add(emailt);

        phone = new JLabel("PHONE");
        phone.setFont(new Font("Times new Roman", Font.BOLD, 20));
        phone.setBounds(30, 350, 100, 32);
        add(phone);

        phonet = new JTextField();
        phonet.setFont(new Font("Times new Roman", Font.CENTER_BASELINE, 14));
        phonet.setBounds(200, 350, 200, 25);
        add(phonet);

        pass = new JLabel("PASSWORD");
        pass.setFont(new Font("Times new Roman", Font.BOLD, 20));
        pass.setBounds(30, 400, 150, 32);
        add(pass);

        passt = new JPasswordField();
        passt.setFont(new Font("Times new Roman", Font.CENTER_BASELINE, 14));
        passt.setBounds(200, 400, 200, 25);
        add(passt);

        cnfpass = new JLabel("CONFIRM");
        cnfpass.setFont(new Font("Times new Roman", Font.BOLD, 20));
        cnfpass.setBounds(30, 450, 100, 32);
        add(cnfpass);
        cnfpass2 = new JLabel("PASSWORD");
        cnfpass2.setFont(new Font("Times new Roman", Font.BOLD, 20));
        cnfpass2.setBounds(30, 475, 150, 32);
        add(cnfpass2);

        cnfpasst = new JPasswordField();
        cnfpasst.setFont(new Font("Times new Roman", Font.CENTER_BASELINE, 14));
        cnfpasst.setBounds(200, 450, 200, 25);
        add(cnfpasst);

        done = new JButton("DONE");
        done.setFont(new Font("Times new Roman", Font.BOLD, 20));
        done.setBackground(Color.BLACK);
        done.setForeground(Color.WHITE);
        done.setBounds(500, 600, 100, 32);
        add(done);

        back =new JButton("Back");
        back.setFont(new Font("Times new Roman", Font.BOLD, 20));
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(100, 600, 100, 32);
        add(back);

        done.addActionListener(this);
        back.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setSize(700, 700);
        setLocation(500, 90);
        setVisible(true);

    }
    public void actionPerformed(ActionEvent ae){
        try{
            if(ae.getSource()==done)
            {
                
                String name =namet.getText();
                String email=emailt.getText();
                char[] password = passt.getPassword();
                char[] cnfpass =cnfpasst.getPassword();
                String p=new String(password);
                String cp =new String(cnfpass);
                if(!p.equals(cp))
                {
                    JOptionPane.showMessageDialog(null, "Passwords don't match");
                }else{
                String phone =phonet.getText();
                String date = (String)dd.getSelectedItem();
                String month =(String)mm.getSelectedItem();
                month=monthtonum(month);
                String year =(String)yy.getSelectedItem(); 
                
                char gender;
                if(m.isSelected())
                gender='M';
                else if(fl.isSelected())
                gender ='F';
                else 
                gender='O';
                if(name==""||email==""||phone==""||(!m.isSelected()&&!fl.isSelected()&&!o.isSelected()))
                {
                    JOptionPane.showMessageDialog(null, "Fill all fields");
                }
                UserInfo user=new UserInfo(name,email,phone,gender,password,date,month,year);
                ObjectOutputStream os =new ObjectOutputStream(Connect.socket.getOutputStream());
                os.writeInt(2);
                os.writeObject(user);
                os.flush();
                ObjectInputStream oi =new ObjectInputStream(Connect.socket.getInputStream());
                Boolean b=(Boolean)oi.readBoolean();
                if(b){
                new Login().setVisible(true);
                setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Account not created");
                }
                }

            }
            if(ae.getSource()==back)
            {
                setVisible(false);
                new Login().setVisible(true);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    
}