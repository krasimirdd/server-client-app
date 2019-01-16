package testove_kontrolno2.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerAssistant extends Thread {

    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final List<Question> test;
    final List<String> examined;
    private final List<String> loggedIn;

    ServerAssistant(List<String> loggedIn, List<String> examined, Socket socket, DataInputStream inputStream, DataOutputStream outputStream) throws FileNotFoundException {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.socket = socket;
        test = readText();
        this.examined = examined;
        this.loggedIn = loggedIn;
    }

    private static List<Question> readText() throws FileNotFoundException {
        List<Question> test = new ArrayList<>();
        Scanner scan = new Scanner(new File("E:\\Projects\\IntelliJ_Projects\\PIK_3\\src\\testove_kontrolno2\\questions.txt"));
        while (scan.hasNextLine()) {

            List<String> answers = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                answers.add(j + "");
            }

            String line = scan.nextLine();
            Question q = new Question(line, answers, 2);
            test.add(q);
        }
        return test;
    }

    private void askQuestion(List<Question> test, int i) throws IOException {
        outputStream.writeUTF(test.get(i).toString());
        outputStream.flush();
    }

    @Override
    public void run() {
        int correct = 0;

        try {
            outputStream.writeUTF("Username:");

            String username;
            username = inputStream.readUTF();

            if (!loggedIn.contains(username)) {
                loggedIn.add(username);
            } else {
                outputStream.writeUTF("WARNING!!! U already logged in");
                outputStream.flush();
                socket.close();
            }

            if (examined.contains(username)) {
                outputStream.writeUTF("U already examined, this will not change ur mark, press 1");
                outputStream.flush();
            }
            synchronized (this) {
                examined.add(username);
            }

            for (int i = 0; i < test.size(); i++) {
                askQuestion(test, i);
                outputStream.flush();

                Integer answer;
                answer = Integer.valueOf(inputStream.readUTF());

                if (answer.equals(test.get(i).getCorrectAnswer())) {
                    correct++;
                }
            }

            outputStream.writeUTF("Correct answers count : " + correct);
            outputStream.flush();

            loggedIn.remove(username);
            outputStream.writeUTF("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
