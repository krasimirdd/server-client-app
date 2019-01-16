package election_kontrolno2.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperThread extends Thread {

    final Socket socket;
    final DataInputStream inputStream;
    final DataOutputStream outputStream;

    public HelperThread(Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {

        try {

            outputStream.writeUTF("enter code: ");
            String code = inputStream.readUTF();

            if (Server.codes.contains(code)) {

                Matcher m = Pattern.compile("[a-zA-z]+").matcher(code);
                if (m.matches()) {
                    //TODO
                    //admin thread
                } else {
                    Thread citizenThread = new CitizenThread(socket, inputStream, outputStream, code);
                    citizenThread.start();
                }

            } else {
                outputStream.writeUTF("REFUSED");
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
