package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthTextAreaUI;

public class Client {
    static public void createClient() {
        try {
            Socket clientSocket = new Socket("127.0.0.1", 1234);
            System.out.println("My Port is " + clientSocket.getLocalAddress() + "/"
                    + clientSocket.getLocalPort());

            InputStream inputStream = clientSocket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                // Scanner scanner = new Scanner(System.in);
                // System.out.print("Client: ");
                // String messageToServer = scanner.nextLine();
                // printWriter.println(messageToServer);

                // String messageFromServer = bufferedReader.readLine();
                // if (messageFromServer == null) {
                // System.out.println("OPPS Server Stopped!!");
                // break;
                // }
                // System.out.println("Server: " + messageFromServer);

                String messageFromServer = bufferedReader.readLine();
                System.out.println(messageFromServer);

                String messageToServer = scanner.nextLine();
                printWriter.println(messageToServer);
            }
        } catch (IOException io) {
            System.out.println("Can not create Client Socket");
        }
    }

    public static void main(String[] args) {
        createClient();
    }
}
