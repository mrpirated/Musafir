package MusafirServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket socket;
        System.out.println("Server Started");
        try {
            serverSocket = new ServerSocket(5000);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        new HandleDatabase();
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("Connected to Client");

                Thread t = new Thread(new HandleClient(socket));
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }

    }
}
