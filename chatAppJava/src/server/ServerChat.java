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
    private int numberOfActiveClients;
    private int numberOfDesactiveClients;

    public ServerChat() {
        this.numberOfActiveClients = 0;
        this.numberOfDesactiveClients = 0;
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
                    this.numberOfActiveClients++;
                    ConversationWithClient conversationWithClient = new ConversationWithClient(
                            this.numberOfActiveClients, socket);
                    this.connections.add(conversationWithClient);
                    conversationWithClient.start();
                }
            }
        } catch (Exception exception) {
            System.out.println("Error in Creating Server Socket");
        }
    }

    public void setNumberOfActiveClients() {
        this.numberOfActiveClients--;
    }

    public void setNumberOfDisActiveClients() {
        this.numberOfDesactiveClients++;
    }

    public class ConversationWithClient extends Thread {
        private int myNumber;
        private Socket socket;

        public ConversationWithClient(int myNumber, Socket socket) {
            this.myNumber = myNumber;
            this.socket = socket;
        }

        public int getMyNumber() {
            return myNumber;
        }

        public void broadCastMessage(String req) throws IOException {
            // Here We can send message to all clients execept the client that send message
            System.out.println("We Have: " + connections.size());
            for (ConversationWithClient connection : connections) {
                if (connection.equals(this)) {
                    continue;
                }
                OutputStream outputStream = connection.socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);
                System.out.println(">>> Client #" + connection.myNumber + " || Send Message: " + req);
                printWriter.println("Client #" + this.myNumber + ": " + req);
            }
        }

        public void broadCastMessageToClientX(String req, int clientNumber) throws IOException {
            // Get Index of Client
            ConversationWithClient targetClient = null;
            for (ConversationWithClient connection : connections) {
                // if (connection)
                if (connection.getMyNumber() == clientNumber) {
                    targetClient = connection;
                    System.out.println("The Reciver is: " + clientNumber);
                    break;
                }
            }
            if (targetClient == null) {
                System.out.println("There is no Client With this number");
                return;
            } else {
                OutputStream outputStream = targetClient.socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);
                System.out.println(">>> Client #" + targetClient.myNumber + " || Send Message: " + req);
                printWriter.println("Client #" + this.myNumber + ": " + req);
            }
        }

        public int getClientNumber(String req) {
            // ClientNumber->Message
            //  22-->Hello
            //  e-->Hello
            //  -->Hello
            //  +1-->Hello
            //  1-->Hello
            if ((req.contains("-"))) {
                int index = req.indexOf("-") - 1;
                if (index == 0) return -1; 
                return 0;
            } else {
                return -1;
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
                while (true) {
                    String messageFromClient = bufferedReader.readLine();
                    if (messageFromClient == null) {
                        int clientIndex = connections.indexOf(this);
                        connections.remove(clientIndex);
                        System.out.println("Client#" + (clientIndex + 1) + " Disconnect");
                        break;
                    }
                    if (connections.size() == 1) {
                        System.out.println("No Client Here !");
                    }
                    // Get Number Of Client
                    System.out.println("With This Message I can Extract Number Of Client: " + messageFromClient);
                    // ClientNumber->Message

                    int clientNumber = 1;
                    broadCastMessageToClientX(messageFromClient, clientNumber);
                    // broadCastMessage(messageFromClient);
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
