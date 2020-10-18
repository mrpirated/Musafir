package MusafirServer;

import java.io.*;
import java.net.*;

public class HandleClient implements Runnable{

    private Socket socket;
    public HandleClient(Socket socket){
        this.socket=socket;
        /*try{
            ObjectInputStream oi=new ObjectInputStream(this.socket.getInputStream());

        }catch(IOException e)
        {
            e.printStackTrace();
        }*/
    }

    @Override
    public void run() {
        while(true){
            try{
                DataInputStream din =new DataInputStream(socket.getInputStream());
                Boolean b= (Boolean)din.readBoolean();
                System.out.println(b);
                DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("asdas");
                dos.flush();
                
            }catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
        
    }
}
