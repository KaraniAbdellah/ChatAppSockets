package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

// Problem with this client should be asynchronous
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
            System.out.println("Write something for users: ");
            Scanner scanner = new Scanner(System.in);
            // Blocking Problem --> We need two threads (Send/Recieve)
            Thread sender = new Sender(printWriter, scanner);
            Thread reciever = new Reciever(bufferedReader);
            while (true) {
                try {
                    sender.start();
                    reciever.start();

                    // What is Join Function
                    sender.join();
                    reciever.join();
                } catch (InterruptedException ie) {
                    System.out.println(ie);
                }

            }
        } catch (IOException io) {
            System.out.println("Can not create Client Socket");
        }
    }

    static class Sender extends Thread {
        PrintWriter printWriter;
        Scanner scanner;

        public Sender(PrintWriter printWriter, Scanner scanner) throws IOException {
            this.printWriter = printWriter;
            this.scanner = scanner;
        }

        @Override
        public void run() {
            try {
                String messageFromServer = this.scanner.nextLine();
                System.out.println("Message From Server: " + messageFromServer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class Reciever extends Thread {
        BufferedReader bufferedReader;

        public Reciever(BufferedReader bufferedReader) throws IOException {
            this.bufferedReader = bufferedReader;

        }

        @Override
        public void run() {
            String messageFromClientX;
            try {
                messageFromClientX = bufferedReader.readLine();
                System.out.println("Message From Client X: " + messageFromClientX);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        createClient();
    }
}


// Fix Send To Sender
// Fix Client Disconnect or Server Disconnect
// Make Client Send to Specific Client
// ...