package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.controller.phases.gamePhases.GamePhase;
import it.polimi.ingsw.controller.phases.gamePhases.PlanningPhase;
import it.polimi.ingsw.controller.phases.gamePhases.SetupPhase;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SchoolBoard;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.BoardData;
import it.polimi.ingsw.network.messages.serverMessages.IsWinner;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
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
        broadcastMessage(new BoardData(game.getBoard().getLightBoard()));
        if (gamePhase.toString().equals("ActionPhase") && playersOrder.indexOf(currentPlayer) < playersOrder.size() - 1){
            game.setCurrentPlayer(playersOrder.get(playersOrder.indexOf(currentPlayer)+1));
            gamePhase.execute(this);
        } else {
            if(playersOrder.stream().filter(p -> p.getCards().size() == 0).count() == playersOrder.size() ||
               game.getBoard().getBag().getSize() == 0) {
                calculateWinner();
            }
            else {
                for(Cloud c : game.getBoard().getClouds()){
                    c.fill(game.getBoard().drawStudentsArray(c.getCloudContent().length));
                }
                setGamePhase(new PlanningPhase());
            }
        }
    }

    public void checkTowerConditionsWin() {
        Optional<SchoolBoard> winner = Arrays.stream(game.getBoard().getPlayerBoards()).filter(b -> b.getTowersLeft() == 0).findFirst();
        winner.ifPresent(schoolBoard -> warnPlayersAboutGameEnd(schoolBoard.getPlayer().getNickname(), "They built all of their towers!"));
    }

    public void checkIslandConditionsWin() {
        if (game.getBoard().getArchipelagos().size() == 3){
            calculateWinner();
        }
    }

    private void calculateWinner() {
        Player winner = null;
        SchoolBoard[] psb = game.getBoard().getPlayerBoards();
        String condition;
        int minTowers = Arrays.stream(psb).map(SchoolBoard::getTowersLeft).sorted().collect(Collectors.toList()).get(0);
        if(Arrays.stream(psb).filter(sb -> sb.getTowersLeft() == minTowers).count() == 1){
            winner = Arrays.stream(psb).filter(sb -> sb.getTowersLeft() == minTowers).findFirst().get().getPlayer();
            condition = "They built the largest number of towers!";
        } else {
            List<SchoolBoard> eligibleWinners = Arrays.stream(psb).filter(sb -> sb.getTowersLeft() == minTowers).collect(Collectors.toList());
            int maxProfNumber = 0;
            for (SchoolBoard sb : eligibleWinners){
                int currentProfNumber = countProfessors(sb.getProfessorTable());
                if (currentProfNumber > maxProfNumber){
                    maxProfNumber = currentProfNumber;
                    winner = sb.getPlayer();
                }
            }
            condition = "They control the largest number of professors!";
        }

        assert winner != null;
        warnPlayersAboutGameEnd(winner.getNickname(), condition);
    }

    private int countProfessors(boolean[] professorTable) {
        int count = 0;
        for (boolean p : professorTable){
            if(p) count++;
        }
        return count;
    }

    public void warnPlayersAboutGameEnd(String winnerNickname, String condition){
        for (ClientHandler c : clientHandlers){
            c.sendMsgToClient(new IsWinner(winnerNickname, condition, game.getBoard().getLightBoard()));
            c.disconnect();
        }
        server.endGame(this);
    }
}
