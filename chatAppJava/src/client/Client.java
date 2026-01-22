package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

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

            try {
                Thread sender = new Sender(printWriter, scanner);
                Thread receiver = new Receiver(bufferedReader);

                sender.start();
                receiver.start();

                sender.join();
                receiver.join();
                clientSocket.close();
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted: " + ie);
            }

            clientSocket.close();
        } catch (IOException io) {
            System.out.println("Can not create Client Socket: " + io.getMessage());
        }
    }

    // SENDER: Reads from keyboar and sends to server
    static class Sender extends Thread {
        PrintWriter printWriter;
        Scanner scanner;

        public Sender(PrintWriter printWriter, Scanner scanner) {
            this.printWriter = printWriter;
            this.scanner = scanner;
        }

        @Override
        public void run() {
            try {
                System.out.println("type message ... (ClientNumber->Message):");
                while (true) {
                    String messageToServer = this.scanner.nextLine();
                    printWriter.println(messageToServer);
                }
            } catch (Exception e) {
                System.out.println("Sender error: " + e.getMessage());
            }
        }
    }

    // RECEIVER: Reads from server and prints to console
    static class Receiver extends Thread {
        BufferedReader bufferedReader;

        public Receiver(BufferedReader bufferedReader) {
            this.bufferedReader = bufferedReader;
        }

        @Override
        public void run() {
            try {
                String messageFromServer = bufferedReader.readLine();
                while (messageFromServer != null) {
                    System.out.println(messageFromServer);
                    messageFromServer = bufferedReader.readLine();
                }
                System.out.println("Connection closed by server. Try to Connect Again");
            } catch (IOException e) {
                System.out.println("Receiver error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        createClient();
    }
}