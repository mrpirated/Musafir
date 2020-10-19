package MusafirServer;

import java.sql.*;

public class Conn {
    Connection c;
    Statement s;
    public Conn(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            c=DriverManager.getConnection("jdbc:mysql://localhost:3306/musafir", "root", "");
            System.out.println("Connected to db");
            s=c.createStatement();
        }catch(Exception e){ 
            System.out.println(e);
        } 
    }
}
