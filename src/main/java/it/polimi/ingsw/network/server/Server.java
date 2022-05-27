package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.network.messages.serverMessages.GameNameRequest;
import it.polimi.ingsw.network.messages.serverMessages.NicknameRequest;
import it.polimi.ingsw.network.messages.serverMessages.TextMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private final int port;
    private ServerSocket serverSocket;
    private final Map<Controller, Integer> lobbies;
    private ExecutorService executor;
    private Set<String> notAvailableNames;
    private final Object lobbyLock = new Object();
    public static final Logger SERVER_LOGGER = Logger.getLogger(Server.class.getName() + "Logger");

    public Server(int port) {
        this.port = port;
        lobbies = new ConcurrentHashMap<>();
        notAvailableNames = new HashSet<>();
        this.executor = Executors.newCachedThreadPool();
    }

    public void start(){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            SERVER_LOGGER.severe("Unable to open port " + port);
            return;
        }

        SERVER_LOGGER.info("Server started on port " + port);

        try {
            while (true){
                Socket clientSocket = serverSocket.accept();
                SERVER_LOGGER.log(Level.INFO,"New client connected from (" + clientSocket.getInetAddress().getHostAddress() + ")");
                ClientHandler clientConnection= new ClientHandler(this, clientSocket);
                executor.submit(clientConnection);
            }
        } catch (IOException e) {
            SERVER_LOGGER.log(Level.SEVERE, "Error during client acceptance");
        }
    }

    public Map<Controller, Integer> getLobbies() {
        return lobbies;
    }

    public void addToLobby(String gameName, ClientHandler clientHandler){
        synchronized (lobbyLock){
            Controller gameController = lobbies.keySet().stream().filter(l -> l.getGameName().equalsIgnoreCase(gameName)).findFirst().get();
            gameController.addHandler(clientHandler);
            clientHandler.setController(gameController);
            checkLobby(gameController);
        }
        if(clientHandler.getClientHandlerPhase() != ClientHandlerPhases.WAITING_GAMEMODE && clientHandler.getClientHandlerPhase() != ClientHandlerPhases.WAITING_PLAYERNUMBER) {
            clientHandler.setClientHandlerPhase(ClientHandlerPhases.WAITING_IN_LOBBY);
            clientHandler.sendMsgToClient(new TextMessage("Waiting for the other player(s) to join"));
        }
    }

    public void checkLobby(Controller controller) {
        synchronized (lobbyLock){
            if(lobbies.get(controller) == controller.getHandlers().size()){
                controller.startGame();
            }
        }
    }

    public void validateNickname(ClientHandler clientHandler){
        if(!notAvailableNames.contains(clientHandler.getClientNickname())){
            notAvailableNames.add(clientHandler.getClientNickname());
            clientHandler.setClientHandlerPhase(ClientHandlerPhases.WAITING_GAMENAME);
            clientHandler.sendMsgToClient(new GameNameRequest());
        } else {
            clientHandler.setClientHandlerPhase(ClientHandlerPhases.WAITING_NICKNAME);
            clientHandler.sendMsgToClient(new NicknameRequest());
        }
    }
}
