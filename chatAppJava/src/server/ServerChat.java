package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerChat extends Thread {
    private int numberOfClient = 0;
    ArrayList<ConversationWithClient> connections = new ArrayList<ConversationWithClient>();

    @Override
    public void run() {
        try {
            // create Server
            ServerSocket serverSocket = new ServerSocket(1234);
            while (true) {
                // Accept Client Connection
                Socket socket = serverSocket.accept();
                this.numberOfClient++;
                System.out.println("New Connection Established In: " +
                        socket.getInetAddress() + "/" + socket.getLocalPort());
                ConversationWithClient newConversationWithClient = new ConversationWithClient(socket, numberOfClient, connections);
                this.connections.add(newConversationWithClient);
                newConversationWithClient.start();
            }
        } catch (Exception exception) {
            System.out.println("Error in Creating Server Socket");
        }
    }

    public static void main(String []args) {
        new ServerChat().start();
    }
}
