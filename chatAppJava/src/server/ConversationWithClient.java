package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.Random;

public class ConversationWithClient extends Thread {
    private int numberOfClient;
    private int numberSecret;
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
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            Random r = new Random();
            int num = r.nextInt(1000);
            System.out.printf("Server generate %d?", num);
            printWriter.println("Guest my Number ?");

            while (true) {
                // String messageFromClient = bufferedReader.readLine();
                // if (messageFromClient == null) {
                // System.out.println("Client #" + this.numberOfClient + " Disconnect");
                // break;
                // }
                // System.out.println("Client #" + this.numberOfClient + ": " +
                // messageFromClient);

                // Scanner scanner = new Scanner(System.in);
                // System.out.print("Server: ");
                // String messageToClient = scanner.nextLine();
                // printWriter.println(messageToClient);
                // printWriter.flush(); // send message if PrinterWriter take false

                String numberFromServer = bufferedReader.readLine();
                int numFromClient = Integer.parseInt(numberFromServer);
                System.out.printf(">>>>> Client # %d Generated %d\n", this.numberOfClient, numFromClient);
                if (numFromClient < num) {
                    printWriter.println("Small Number, try It Again ?");
                } else if (numFromClient > num) {
                    printWriter.println("Big Number, try It Again ?");
                } else {
                    printWriter.println("Right NICE");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Can Not Start Conversation");
        }

    }
}
