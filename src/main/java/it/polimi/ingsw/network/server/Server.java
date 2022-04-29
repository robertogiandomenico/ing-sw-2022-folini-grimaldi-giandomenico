package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private List<ClientHandler> lobby;
    private ExecutorService executor;
    public static final Logger SERVER_LOGGER = Logger.getLogger(Server.class.getName() + "Logger");
    private final Object lockLobby;

    public Server(int port) {
        this.port = port;
        lobby = new ArrayList<>();
        lockLobby = new Object();
    }

    public void start(){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            SERVER_LOGGER.log(Level.SEVERE, "Unable to open port " + port);
            return;
        }

        SERVER_LOGGER.log(Level.INFO,"Server started on port " + port);

        try {
            while (true){
                Socket clientSocket = serverSocket.accept();
                SERVER_LOGGER.log(Level.INFO,"New client connected from ( " + clientSocket.getInetAddress().getHostAddress() + ")");
                executor.submit(new ClientHandler(this, clientSocket));
            }
        } catch (IOException e) {
            SERVER_LOGGER.log(Level.SEVERE, "Error during client acceptance");
        }
    }
}
