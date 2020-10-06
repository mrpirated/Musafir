package LockDownTraveller;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class SignUp extends JFrame{
    
    JLabel head,name,date,phone,dob,month,year,gender,email;
    JTextField namet,emailt;
    JRadioButton m,o,fl;
    JComboBox dd,mm,yy;
    SignUp(){
        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("LOCKDOWN TRAVELLER");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z/y;
        String pad ="";   
        pad = String.format("%"+w*1.7+"s", pad);
        setTitle(pad+"LOCKDOWN TRAVELLER");


        setLayout(null);

        head=new JLabel("SIGNUP");
        head.setFont(new Font("Times new Roman",Font.BOLD,38));
        head.setBounds(265, 16, 460, 32);
        add(head);
        
        name=new JLabel("NAME");
        name.setFont(new Font("Times new Roman",Font.BOLD,20));
        name.setBounds(30, 75, 100, 32);
        add(name);

        namet=new JTextField();
        namet.setFont(new Font("Times new Roman",Font.CENTER_BASELINE,14));
        namet.setBounds(200, 75, 400, 25);
        add(namet);


        dob=new JLabel("DATE OF BIRTH");
        dob.setFont(new Font("Times new Roman",Font.BOLD,20));
        dob.setBounds(30, 125, 300, 32);
        add(dob);

        date=new JLabel("Date");
        date.setFont(new Font("Times new Roman",Font.BOLD,18));
        date.setBounds(200, 122, 75, 32);
        add(date);

        String date[]={"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",};
        dd=new JComboBox(date);
        dd.setBackground(Color.WHITE);
        dd.setBounds(245,125,40,25);
        add(dd);

        month=new JLabel("Month");
        month.setFont(new Font("Times new Roman",Font.BOLD,18));
        month.setBounds(300, 122, 100, 32);
        add(month);

        String month[]={"January","February","March","April","May","June","July","August","September","October","November","December"};
        mm=new JComboBox(month);
        mm.setBackground(Color.WHITE);
        mm.setBounds(365,125,120,25);
        add(mm);

        year=new JLabel("Year");
        year.setFont(new Font("Times new Roman",Font.BOLD,18));
        year.setBounds(500, 122, 100, 32);
        add(year);

        String year[]={"1974","1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2007","2008","2009","2010"};
        yy=new JComboBox(year);
        yy.setBackground(Color.WHITE);
        yy.setBounds(550,125,60,25);
        add(yy);

        gender=new JLabel("GENDER");
        gender.setFont(new Font("Times new Roman",Font.BOLD,20));
        gender.setBounds(30, 175, 100, 32);
        add(gender);

        m=new JRadioButton("Male");
        m.setFont(new Font("Times new Roman",Font.BOLD,18));
        m.setBackground(Color.WHITE);
        m.setBounds(200, 175, 75, 32);
        add(m);

        fl=new JRadioButton("Female");
        fl.setFont(new Font("Times new Roman",Font.BOLD,18));
        fl.setBackground(Color.WHITE);
        fl.setBounds(300, 175, 100, 32);
        add(fl);

        o=new JRadioButton("Other");
        o.setFont(new Font("Times new Roman",Font.BOLD,18));
        o.setBackground(Color.WHITE);
        o.setBounds(420, 175, 75, 32);
        add(o);

        email=new JLabel("EMAIL");
        email.setFont(new Font("Times new Roman",Font.BOLD,20));
        email.setBounds(30, 225, 100, 32);
        add(email);

        emailt=new JTextField();
        emailt.setFont(new Font("Times new Roman",Font.CENTER_BASELINE,14));
        emailt.setBounds(200, 225, 400, 25);
        add(emailt);






        getContentPane().setBackground(Color.WHITE);
        setSize(700,700);
        setLocation(500,90);
        setVisible(true);

    }
    public static void main(String[] args) {
        new SignUp().setVisible(true);
    }
}