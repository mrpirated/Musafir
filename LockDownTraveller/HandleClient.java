package LockDownTraveller;

import java.io.*;
import java.net.*;

public class HandleClient implements Runnable{

    private Socket socket;
    public HandleClient(Socket socket){
        this.socket=socket;
        try{
            Connect.oi=new ObjectInputStream(this.socket.getInputStream());

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        
            try{
                Connect.din =new DataInputStream(socket.getInputStream());
                Boolean b= (Boolean)Connect.din.readBoolean();
                System.out.println(b);
                
            }catch (Exception e){
                e.printStackTrace();
                return;
            }
        
    }
}
