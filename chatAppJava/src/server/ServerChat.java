package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerChat extends Thread {
    private ArrayList<ConversationWithClient> connections;
    private int numberOfClients;

    public ServerChat() {
        this.numberOfClients = 0;
        this.connections = new ArrayList<ConversationWithClient>();
    }

    @Override
    public void run() {
        try {
            try (
                    ServerSocket serverSocket = new ServerSocket(1234)) {
                System.out.println("Server Started");
                while (true) {
                    // Accept Client Connection
                    Socket socket = serverSocket.accept();
                    System.out.println("New Connection Established In: " +
                            socket.getInetAddress() + "/" + socket.getLocalPort());
                    this.numberOfClients++;
                    ConversationWithClient conversationWithClient = new ConversationWithClient(
                            this.numberOfClients, socket);
                    this.connections.add(conversationWithClient);
                    conversationWithClient.start();
                }
            }
        } catch (Exception exception) {
            System.out.println("Error in Creating Server Socket");
        }
    }

    public class ConversationWithClient extends Thread {
        private int myNumber;
        private Socket socket;

        public ConversationWithClient(int myNumber, Socket socket) {
            this.myNumber = myNumber;
            this.socket = socket;
        }

        public void broadCastMessage(String req, ArrayList<ConversationWithClient> connections) throws IOException {
            // Here We can send message to all clients
            System.out.println("The Clients that We Have: " + connections.size());
            for (ConversationWithClient connection: connections) {
                OutputStream outputStream = connection.socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);
                System.out.println(">>> Client #" + connection.myNumber + " || Send Message: " + req);
                printWriter.println(req);
            }
        }

        @Override
        public void run() {
            // Start Conversation
            System.out.println("Client #" + this.myNumber);

            try {
                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                OutputStream outputStream = socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);

                while (true) {
                    String messageFromClient = bufferedReader.readLine();
                    // if (messageFromClient == null)
                    System.out.println("Message to All Clients" + messageFromClient);
                    if (messageFromClient == null) {
                        // remove it from connections
                        
                        System.out.println("Client#" + this.myNumber + " Disconnect");
                        break;
                    }
                    broadCastMessage(messageFromClient, connections);
                }
            } catch (IOException e) {
                System.out.println("Can Not Start Conversation");
            }

        }
    }

    public static void main(String[] args) {
        new ServerChat().start();
    }
}
