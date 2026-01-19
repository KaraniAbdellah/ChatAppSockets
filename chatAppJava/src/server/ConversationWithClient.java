package server;

import javax.sound.midi.SysexMessage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class ConversationWithClient extends Thread {
    private int numberOfClient;
    private int numberSecret;
    private Socket socket;
    private ArrayList<ConversationWithClient> connections;

    public ConversationWithClient() {

    }

    public ConversationWithClient(Socket socket, int numberOfClient, ArrayList<ConversationWithClient> connections) {
        this.numberOfClient = numberOfClient;
        this.socket = socket;
        this.connections = connections;
    }


    public void broadCastMessage(String req, ArrayList<ConversationWithClient> connections) throws IOException {
        // Here We can send message to all this clients
        System.out.println("The Clients that We Have: " + connections.size());
        for (int i = 0; i < connections.size(); i++) {
            ConversationWithClient connection = connections.get(i);
            OutputStream outputStream = connection.socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            System.out.println("I am Client Number: " + connection.numberOfClient);
            printWriter.println(req);
        }
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

            while (true) {
                String messageFromClient = bufferedReader.readLine();
                System.out.println("Message to All Clients" + messageFromClient);
                broadCastMessage(messageFromClient, connections);
            }
        } catch (IOException e) {
            System.out.println("Can Not Start Conversation");
        }

    }
}
