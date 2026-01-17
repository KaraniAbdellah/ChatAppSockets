package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ConversationWithClient extends Thread{
    private int numberOfClient;
    private Socket socket;
    public ConversationWithClient() {

    }
    public ConversationWithClient(Socket socket, int numberOfClient) {
        this.numberOfClient = numberOfClient;
        this.socket = socket;
    }
    @Override
    public void run() {
        // Start Conversation
        System.out.println("Client #" + this.numberOfClient + " HERE");

        try {
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);

            while (true) {
                String messageFromClient = bufferedReader.readLine();
                if (messageFromClient == null) {
                    System.out.println("Client #" + this.numberOfClient + " Disconnect");
                    break;
                }
                System.out.println("Client #" + this.numberOfClient + ": " + messageFromClient);

                Scanner scanner = new Scanner(System.in);
                System.out.print("Server: ");
                String messageToClient = scanner.nextLine();
                printWriter.println(messageToClient);
                printWriter.flush(); // send message
            }
        } catch (IOException e) {
            System.out.println("Can Not Start Conversation");
        }

    }
}
