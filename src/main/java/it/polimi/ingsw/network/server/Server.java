package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.network.messages.connectionMessages.DisconnectionMessage;
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

/**
 * This class represents the server. It is used to start and close the games,
 * and to manage clients that connect to play.
 */
public class Server {
    private final int port;
    private ServerSocket serverSocket;
    private final int CLIENT_SOCKET_TIMEOUT = 10000;
    private final Map<Controller, Integer> lobbies;
    private final ExecutorService executor;
    private final Set<String> notAvailableNames;
    private final Object lobbyLock = new Object();
    public static final Logger SERVER_LOGGER = Logger.getLogger(Server.class.getName() + "Logger");

    /**
     * Class constructor.
     *
     * @param port           the Server port.
     */
    public Server(int port) {
        this.port = port;
        lobbies = new ConcurrentHashMap<>();
        notAvailableNames = new HashSet<>();
        this.executor = Executors.newCachedThreadPool();
    }

    /**
     * Initializes server socket, waits for client connections and accepts them,
     * delegating the {@link ClientHandler} for further actions.
     */
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
                clientSocket.setSoTimeout(CLIENT_SOCKET_TIMEOUT);
                SERVER_LOGGER.log(Level.INFO,"New client connected from (" + clientSocket.getInetAddress().getHostAddress() + ")");
                ClientHandler clientConnection= new ClientHandler(this, clientSocket);
                executor.submit(clientConnection);
            }
        } catch (IOException e) {
            SERVER_LOGGER.log(Level.SEVERE, "Error during client acceptance");
        }
    }

    /**
     * Returns the lobbies.
     *
     * @return               the Map that represents the lobbies.
     */
    public Map<Controller, Integer> getLobbies() {
        return lobbies;
    }

    /**
     * Adds a client to the lobby of the given game.
     *
     * @param gameName       the name of the Game.
     * @param clientHandler  a ClientHandler.
     */
    public void addToLobby(String gameName, ClientHandler clientHandler){
        synchronized (lobbyLock){
            Controller gameController = lobbies.keySet().stream().filter(l -> l.getGameName().equalsIgnoreCase(gameName)).findFirst().get();
            gameController.addHandler(clientHandler);
            clientHandler.setController(gameController);
            checkLobby(gameController);
        }
        if(!clientHandler.getController().isGameStarted() && clientHandler.getClientHandlerPhase() != ClientHandlerPhases.WAITING_GAMEMODE && clientHandler.getClientHandlerPhase() != ClientHandlerPhases.WAITING_PLAYERNUMBER) {
            clientHandler.setClientHandlerPhase(ClientHandlerPhases.WAITING_IN_LOBBY);
            clientHandler.sendMsgToClient(new TextMessage("Waiting for the other player(s) to join"));
        }
    }

    /**
     * Checks a lobby removing players if they exceed the required number and
     * starts the game if possible.
     *
     * @param controller     a Controller.
     */
    public void checkLobby(Controller controller) {
        synchronized (lobbyLock){
            if(lobbies.get(controller) == controller.getHandlers().size()) {
                controller.startGame();
                return;
            }
            if (lobbies.get(controller)!= -1 && (lobbies.get(controller) < controller.getHandlers().size())){
                List<ClientHandler> removed = new ArrayList<>();
                for (ClientHandler c : controller.getHandlers()){
                    if (controller.getHandlers().indexOf(c) >= lobbies.get(controller)){
                        removed.add(c);
                    }
                }

                for (ClientHandler c : removed){
                    controller.getHandlers().remove(c);
                    c.sendMsgToClient(new TextMessage("The game is full, you're being disconnected"));
                    notAvailableNames.remove(c.getClientNickname());
                    c.disconnect();
                }
                controller.startGame();
            }
        }
    }

    /**
     * Checks the availability of a nickname:
     * <p>
     * - sets the client handler to the following phase if so;
     * </p> <p>
     * - asks another nickname if not.
     * </p>
     *
     * @param clientHandler  a ClientHandler.
     */
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

    /**
     * Removes a disconnected client and removes the correspondent controller from
     * the lobbies.
     *
     * @param clientHandler  a ClientHandler.
     */
    public void removeClient(ClientHandler clientHandler) {
        Optional<Controller> controller = lobbies.keySet().stream().filter(c -> c.getHandlers().contains(clientHandler)).findFirst();
        if (controller.isPresent()){
            notAvailableNames.remove(clientHandler.getClientNickname());
            synchronized (lobbyLock) {
                lobbies.replace(controller.get(), lobbies.get(controller.get())-1);
                if (lobbies.get(controller.get()) <= 0)
                    lobbies.remove(controller.get());
                else if (!controller.get().isGameEnded())
                    controller.get().broadcastMessage(new DisconnectionMessage(clientHandler.getClientNickname()));
            }

        } else {
            notAvailableNames.remove(clientHandler.getClientNickname());
        }
    }

    /**
     * Disconnects every client handler of the given controller as the game
     * associated to it ends, and then removes the controller from the lobbies.
     *
     * @param controller     the Controller to remove.
     */
    public void disconnectAllAfterEndgame(Controller controller) {
        for (ClientHandler c : controller.getHandlers()){
            notAvailableNames.remove(c.getClientNickname());
            c.disconnect();
        }

        synchronized (lobbyLock){
            lobbies.remove(controller);
        }
    }

}
