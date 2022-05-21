package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.controller.phases.gamePhases.ActionPhase;
import it.polimi.ingsw.controller.phases.gamePhases.GamePhase;
import it.polimi.ingsw.controller.phases.gamePhases.PlanningPhase;
import it.polimi.ingsw.controller.phases.gamePhases.SetupPhase;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.GenericServerMessage;
import it.polimi.ingsw.network.messages.serverMessages.PhaseEntering;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Controller {
    private Game game;
    private final String gameName;
    private Boolean expertMode;
    private GamePhase gamePhase;
    private final List<ClientHandler> clientHandlers;
    private Server server;
    private final Lock connectionLock;

    private boolean gameStarted;

    public Controller(String gameName){
        this.gameName = gameName;
        connectionLock = new ReentrantLock();
        gameStarted = false;
        clientHandlers = new ArrayList<>();
    }

    public boolean isGameStarted() {
        return gameStarted;
    }
    public void setExpertMode(Boolean expertMode){
        this.expertMode = expertMode;
    }

    public void startGame(){
        if(game == null) game = new Game(expertMode);
        game.setNumberOfPlayers(clientHandlers.size());
        Server.SERVER_LOGGER.info("Starting a new game for these players : " + clientHandlers.stream().map(ClientHandler::getClientNickname).collect(Collectors.toList()));
        for (ClientHandler c : clientHandlers){
            c.setClientHandlerPhase(ClientHandlerPhases.WAITING_WIZARD);
        }
        gameStarted = true;
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
        if(gamePhase instanceof PlanningPhase || gamePhase instanceof ActionPhase){
            broadcastMessage(new PhaseEntering(gamePhase.toString()));
        }
        gamePhase.execute(this);
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

    public void broadcastMessage(Serializable msg){
        connectionLock.lock();
        try {
            for (ClientHandler c : clientHandlers){
                c.sendMsgToClient(msg);
            }
        } finally {
            connectionLock.unlock();
        }

    }

    public void receiveMessage(GenericClientMessage msg){
        gamePhase.receiveMessage(msg);
    }

    public void nextTurn() {
        Player currentPlayer = game.getCurrentPlayer();
        List<Player> playersOrder = game.getPlayerOrder();
        if (gamePhase.toString().equals("ActionPhase") && playersOrder.indexOf(currentPlayer) < playersOrder.size() - 1){
            game.setCurrentPlayer(playersOrder.get(playersOrder.indexOf(currentPlayer)+1));
            gamePhase.execute(this);
        } else {
            for(Cloud c : game.getBoard().getClouds()){
                c.fill(game.getBoard().drawStudentsArray(c.getCloudContent().length));
            }
            setGamePhase(new PlanningPhase());
        }
    }
}
