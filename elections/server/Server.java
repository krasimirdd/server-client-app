package election_kontrolno2.server;

import election_kontrolno2.domain.Citizen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    static Map<Integer, Integer> votes = new HashMap<>();
    static List<Citizen> votedCitizensRegister = new ArrayList<>();
    static List<String> codes = new ArrayList<>();
    static String[] candidates = new String[10];

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(1211);

        while (true) {

            Socket socket;
            make(candidates, votes, codes);
            System.out.println("Waiting for client");

            try {

                socket = ss.accept();
                System.out.println("Client has connected");

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                new HelperThread(socket, inputStream, outputStream).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void make(String[] candidates, Map<Integer, Integer> votes, List<String> codes) {
        for (int i = 0; i < 10; i++) {
            candidates[i] = "Candidate " + i;
            votes.put(i, 0);

            if (i == 5) {
                codes.add(i, "admin");
            } else {
                codes.add(i, "980204502" + i);
            }
        }
    }
}
