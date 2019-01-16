package testove_kontrolno2.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(1234);
        List<String> examined = new ArrayList<>();
        List<String> loggedIn = new ArrayList<>();

        while (true) {

            Socket socket = null;
            System.out.println("Waiting for adventurer");

            try {
                socket = ss.accept();
                System.out.println(socket + "has connected\n");

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                Thread th = new ServerAssistant(loggedIn, examined, socket, inputStream, outputStream);
                th.start();
                examined = ((ServerAssistant) th).examined;

            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }
    }
}
