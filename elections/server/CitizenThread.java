package election_kontrolno2.server;

import election_kontrolno2.domain.Citizen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class CitizenThread extends HelperThread {

    private String code;

    public CitizenThread(Socket socket, DataInputStream inputStream, DataOutputStream outputStream, String code) {
        super(socket, inputStream, outputStream);
        this.code = code;
    }

    @Override
    public void run() {
        try {
            outputStream.writeUTF("CANDIDATES TO VOTE [index is number of president]\n"
                    + Arrays.toString(Server.candidates) +
                    "\nenter: Name Address Candidate");
            String[] tokens = inputStream.readUTF().split(" ");

            String name = tokens[0];
            String address = tokens[1];
            Integer indexOfPresident = Integer.valueOf(tokens[2]);

            Citizen citizen = new Citizen(name, code, address, false);

            boolean isVoted = false;
            for (Citizen voted : Server.votedCitizensRegister) {
                if (voted.getId().equals(citizen.getId())) {
                    isVoted = true;
                }
            }
            if (!isVoted) {
                Integer currVotes = Server.votes.get(indexOfPresident);

                synchronized (this) {
                    Server.votes.put(indexOfPresident, currVotes + 1);
                    Server.votedCitizensRegister.add(citizen);
                }

                outputStream.writeUTF("U voted!");
            } else {
                outputStream.writeUTF("Already voted!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
