package Musafir;

import java.net.Socket;

public class Connect {
    static Socket socket;

    public static void ConnectServer(){
        try{
            socket=new Socket("localhost",5000);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
