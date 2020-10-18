package LockDownTraveller;
import java.io.*;
import java.net.Socket;

public class Connect {
    static Socket socket;
    static ObjectInputStream oi;
    static ObjectOutputStream os;
    static DataInputStream din;
    static DataOutputStream dos;
    public static void ConnectServer(){
        try{
            socket=new Socket("localhost",5000);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
