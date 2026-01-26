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

    public ServerChat() {
        this.numberOfActiveClients = 0;
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

    public class ConversationWithClient extends Thread {
        private int myNumber;
        private Socket socket;

        public ConversationWithClient(int myNumber, Socket socket) {
            this.myNumber = myNumber;
            this.socket = socket;
        }

        public String sliceString(String str, int event) {
            if (str == null || !str.contains(">"))
                return null;

            String[] parts = str.split(">");

            if (event == 1) {
                return parts[0];
            } else if (event == 2) {
                return parts.length > 1 ? parts[1] : null;
            } else {
                return null;
            }
        }

        public int getClientNumber(String req) {
            // Message>ClientNumber
            // 22>Hello
            // e>Hello
            // >Hello
            // +1>Hello
            // 1>Hello
            if (req.isEmpty())
                return -1;
            if (!Character.isDigit(req.charAt(0))) {
                return -1; // send message to anyOne
            }
            if ((req.contains("-"))) {
                int index = req.indexOf("-") - 1;
                req.split(req, index);
                return 0;
            } else {
                return -1;
            }
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
                System.out.println(">>> Client #" + connection.myNumber + " Send Message: " + req);
                printWriter.println("Client#" + this.myNumber + " :" + req);
            }
        }

        public void broadCastMessageToClientX(String req, int clientNumber) {
            // Get Index of Client
            ConversationWithClient targetClient = null;
            for (ConversationWithClient connection : connections) {
                // if (connection)
                if (connection.myNumber == clientNumber) {
                    targetClient = connection;
                    break;
                }
            }

            try {
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);

                System.out.println("Exception in I/O");
                if (targetClient == null) {
                    System.out.println("There is no Client With this number");
                    printWriter.println("Can not find this client");
                    return;
                } else {
                    System.out.println(">>> Client #" + targetClient.myNumber + " Send Message: " + req + " to Client#"
                            + clientNumber);
                    printWriter.println("Client #" + this.myNumber + ": " + req);
                }
            } catch (Exception e) {
                System.out.println("Exception in I/O");
            }

        }

        @Override
        public void run() {
            // Start Conversation
            System.out.println("Client #" + this.myNumber);

            try {
                // Recive Messages
                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                // Send Messages
                OutputStream outputStream = this.socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);
                while (true) {
                    String messageFromClient = bufferedReader.readLine();
                    if (messageFromClient == null) {
                        int clientIndex = connections.indexOf(this);
                        connections.remove(clientIndex);
                        System.out.println("Client#" + (clientIndex + 1) + " Disconnect");
                        break;
                    }

                    // Remove Space in Messsage from client (Left & Right)
                    messageFromClient = messageFromClient.trim();

                    // Check if Message Match Menu Characters
                    if (messageFromClient.equals("d")) {
                        // Send To This Client all CLients That Exists
                        for (ConversationWithClient con : connections) {
                            if (con.socket != this.socket) {
                                printWriter.println("Client List: ");
                                printWriter.println("Client #" + con.myNumber + " " + con.socket.getLocalAddress() + "/"
                                        + con.socket.getPort());
                            }
                        }
                        continue;
                    }

                    if (connections.size() == 1) {
                        printWriter.println("No Client Here !");
                        continue;
                    }

                    // Get Number Of Client [MESSAGE FORMAT: ClientNumber->Message]
                    String messageToSend = sliceString(messageFromClient, 2);
                    String clientToSend = sliceString(messageFromClient, 1);
                    System.out.println(messageToSend + clientToSend);
                    if (messageToSend == null || clientToSend == null) {
                        broadCastMessage(messageFromClient);
                        continue;
                    }
                    int clientNumber = Integer.parseInt(clientToSend);
                    broadCastMessageToClientX(messageToSend, clientNumber);
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
