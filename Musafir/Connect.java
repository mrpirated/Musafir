package Musafir;

import java.net.Socket;

public class Connect {
    public Socket socket;

    public Connect(){
        try{
            socket=new Socket("localhost",5000);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
