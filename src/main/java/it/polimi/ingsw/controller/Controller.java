package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.phases.gamePhases.GamePhase;
import it.polimi.ingsw.controller.phases.gamePhases.SetupPhase;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Controller {
    private Game game;
    private String gameName;
    private GamePhase gamePhase;
    private List<ClientHandler> clientHandlers;
    private Server server;
    private final Lock connectionLock;

    public Controller(String gameName, boolean expertMode){
        this.gameName = gameName;
        game = new Game(expertMode);
        connectionLock = new ReentrantLock();
    }

    public void startGame(){
        Server.SERVER_LOGGER.log(Level.INFO, "Starting a new game for these players : " + clientHandlers.stream().map(ClientHandler::getClientNickname).collect(Collectors.toList()));
        setGamePhase(new SetupPhase());
    }

    public Game getGame() {
        return game;
    }

    public String getGameName() {
        return gameName;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public List<ClientHandler> getHandlers() {
        return clientHandlers;
    }

    public void addHandler(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public ClientHandler getHandlerByNickname(String nickname){
        connectionLock.lock();
        try {
            for (ClientHandler c : clientHandlers){
                if(c.getClientNickname().equals(nickname)){
                    return c;
                }
            }
        } finally {
            connectionLock.unlock();
        }

        return null;
    }
}
