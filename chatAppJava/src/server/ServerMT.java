package server;

import java.net.ServerSocket;
import java.net.Socket;


public class ServerMT extends Thread {
    private int numberOfClient = 0;

    @Override
    public void run() {
        try {
            // create Server
            ServerSocket serverSocket = new ServerSocket(1234);

            while (true) {
                // Accept Client Connection
                Socket socket = serverSocket.accept();
                this.numberOfClient++;
                System.out.println("New Connection Established For " +
                        socket.getInetAddress() + "/" + socket.getLocalPort());
                new ConversationWithClient(socket, numberOfClient).start();
            }

        } catch (Exception exception) {
            System.out.println("Error in Creating Server Socket");
        }
    }

    public static void main(String []args) {
        new ServerMT().start();
    }
}
