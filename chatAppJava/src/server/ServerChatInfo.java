package server;

import java.net.Socket;
import java.util.ArrayList;

public class ServerChatInfo {
    private int numberOfClients;
    private ArrayList<ConversationWithClient>  serverSockets;
    private ArrayList<Socket>  clientSockets;

    public void addServerSocket(ConversationWithClient conversationWithClient) {
        this.serverSockets.add(conversationWithClient);
    }
    public int addClientNumber() {
        this.numberOfClients++;
        return this.numberOfClients;
    }

    public void addClientSocket(Socket clientSockets) {
        this.clientSockets.add(clientSockets);
    }
}
