package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import services.Services;

public class ConversationWithClient extends Thread {
    public int myNumber;
    public Socket socket;
    public ArrayList<ConversationWithClient> connections;
    public Services services;

    public ConversationWithClient(int myNumber, Socket socket, ArrayList<ConversationWithClient> connections) {
        this.myNumber = myNumber;
        this.socket = socket;
        this.connections = connections;
        this.services = new Services();
    }

    public int getMyNumber() {
        return myNumber;
    }

    public void broadCastMessage(String req) throws IOException {
        // Here We can send message to all clients execept the client that send message
        System.out.println("We Have: " + this.connections.size());
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

            System.out.println("Exception in I/O");

            if (targetClient == null) {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                System.out.println("There is no Client With this number");
                printWriter.println("Can not find this client");
                return;
            } else {
                PrintWriter printWriter = new PrintWriter(targetClient.socket.getOutputStream(), true);
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
                    printWriter.println("Client List: ");
                    if (connections.size() == 0) {
                        printWriter.println("No Client Found");
                        continue;
                    }
                    for (ConversationWithClient con : connections) {
                        if (con.socket != this.socket) {
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

                // Get Number Of Client [MESSAGE FORMAT: ClientNumber > Message]
                String messageToSend = this.services.sliceString(messageFromClient, 2);
                String clientToSend = this.services.sliceString(messageFromClient, 1);
                if (messageToSend == null || clientToSend == null) {
                    broadCastMessage(messageFromClient);
                    continue;
                }

                clientToSend = clientToSend.trim();
                int clientNumber = Integer.parseInt(clientToSend);
                broadCastMessageToClientX(messageToSend, clientNumber);
            }

        } catch (IOException e) {
            System.out.println("Can Not Start Conversation");
        }
    }

}
