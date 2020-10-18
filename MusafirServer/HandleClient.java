package MusafirServer;

import java.io.*;
import java.net.*;
import Classes.*;
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
    private String Login(LoginInfo loginInfo){
        return "dprathi";
    }
    private Boolean Signup(UserInfo userInfo){
        return false;
    }
    @Override
    public void run() {
        while(true){
            try{
                
                ObjectInputStream oi =new ObjectInputStream(socket.getInputStream());
                
                ObjectOutputStream os =new ObjectOutputStream(socket.getOutputStream());
                int choice =(int)oi.readInt();
                switch(choice){
                    case 1:
                    LoginInfo loginInfo=(LoginInfo)oi.readObject();
                    String s= Login(loginInfo);
                    
                    os.writeUTF(s);
                    
                    os.flush();
                    break;

                    case 2:
                    UserInfo userInfo=(UserInfo)oi.readObject();
                    Boolean b =Signup(userInfo);
                    os.writeBoolean(b);
                    os.flush();
                    break;

                }
                
                
            }catch (Exception e){
                e.printStackTrace();
                return;
            }
        }
        
    }
}
