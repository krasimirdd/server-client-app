package election_kontrolno2.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        try {
            Scanner scanner = new Scanner(System.in);

            InetAddress ip = InetAddress.getByName("localhost");
            Socket socket = new Socket(ip, 1211);

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String received = inputStream.readUTF();
                System.out.println(received);

                String send = scanner.nextLine();
                outputStream.writeUTF(send);

                if (received.equals("Already voted!") || received.equals("U voted!") || received.equals("REFUSED")) {
                    System.out.println("Connectiong closeing");
                    socket.close();
                    inputStream.close();
                    outputStream.close();

                    break;
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
