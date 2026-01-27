package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerChat extends Thread {
    public ArrayList<ConversationWithClient> connections;
    public int numberOfActiveClients;

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
                    ConversationWithClient newCon = new ConversationWithClient(
                            this.numberOfActiveClients, socket, connections);
                    connections.add(newCon);
                    newCon.start();
                }
            }
        } catch (Exception exception) {
            System.out.println("Error in Creating Server Socket");
        }
    }

    public void setNumberOfActiveClients() {
        this.numberOfActiveClients--;
    }

    public static void main(String[] args) {
        new ServerChat().start();
    }
}
