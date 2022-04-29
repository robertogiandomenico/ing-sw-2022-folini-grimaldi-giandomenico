package it.polimi.ingsw.network.server;

import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Server server;
    private final Socket socket;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
