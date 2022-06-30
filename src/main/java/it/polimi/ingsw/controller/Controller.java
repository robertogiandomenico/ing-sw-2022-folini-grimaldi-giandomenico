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

/**
 * This class controls the {@link Game} evolution.
 */
public class Controller {
    private Game game;
    private final String gameName;
    private Boolean expertMode;
    private GamePhase gamePhase;
    private final List<ClientHandler> clientHandlers;
    private final Lock connectionLock;
    private boolean gameStarted;
    private boolean gameEnded;

    /**
     * Class constructor specifying the name of the game this controller is
     * associated to.
     *
     * @param gameName       the Game name.
     */
    public Controller(String gameName){
        this.gameName = gameName;
        connectionLock = new ReentrantLock();
        gameStarted = false;
        clientHandlers = new ArrayList<>();
    }

    /**
     * States whether the game is started.
     *
     * @return               a boolean whose value is:
     *                       <p>
     *                       -{@code true} if the game is started;
     *                       </p> <p>
     *                       -{@code false} otherwise.
     *                       </p>
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Sets the game mode as "expert".
     *
     * @param expertMode     a Boolean whose value is:
     *                       <p>
     *                       -{@code true} if Expert mode was chosen;
     *                       </p> <p>
     *                       -{@code false} otherwise.
     *                       </p>
     */
    public void setExpertMode(Boolean expertMode){
        this.expertMode = expertMode;
    }

    /**
     * Starts the game for the connected players.
     */
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

    /**
     * Returns the game associated to this controller.
     *
     * @return               the Game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Returns the name of the game associated to this Controller.
     *
     * @return               the Game name.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets the given game phase and executes it.
     *
     * @param gamePhase      a GamePhase.
     */
    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
        gamePhase.execute(this);
    }

    /**
     * Returns the client handlers.
     *
     * @return               a ClientHandler List.
     */
    public List<ClientHandler> getHandlers() {
        return clientHandlers;
    }

    /**
     * Adds a client handler.
     *
     * @param clientHandler  a ClientHandler.
     */
    public void addHandler(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
    }

    /**
     * Returns the client handler corresponding to the given nickname.
     *
     * @param nickname       the nickname of a Player.
     * @return               the corresponding ClientHandler.
     */
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

    /**
     * Sends a message to all the clients.
     *
     * @param msg            the message to be sent.
     */
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

    /**
     * Gets a message.
     *
     * @param msg            the received GenericClientMessage.
     */
    public void receiveMessage(GenericClientMessage msg){
        gamePhase.receiveMessage(msg);
    }

    /**
     * Moves to the next turn determining the new current player or sets a new
     * game phase.
     */
    public void nextTurn() {
        Player currentPlayer = game.getCurrentPlayer();
        List<Player> playersOrder = game.getPlayerOrder();
        broadcastMessage(new BoardData(game.getBoard().getLightBoard()));
        game.getBoard().getCurrentPlayerSchoolBoard().setFarmerEffect(false);

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

    /**
     * Checks whether one of the players has built all of their towers. If so,
     * they are the winner of the game.
     */
    public void checkTowerConditionsWin() {
        Optional<SchoolBoard> winner = Arrays.stream(game.getBoard().getPlayerBoards()).filter(b -> b.getTowersLeft() == 0).findFirst();
        winner.ifPresent(schoolBoard -> warnPlayersAboutGameEnd(schoolBoard.getPlayer().getNickname(), "They built all of their towers!"));
    }

    /**
     * Checks whether there are only 3 archipelagos on the board. If so, the
     * game ends and a winner has to be determined.
     */
    public void checkIslandConditionsWin() {
        if (game.getBoard().getArchipelagos().size() == 3){
            calculateWinner();
        }
    }

    /**
     * Determines which player is the winner of the game.
     * (Could be because they built the largest number of towers or because they
     * control the largest number of players).
     */
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
        gameEnded = true;
        warnPlayersAboutGameEnd(winner.getNickname(), condition);
    }

    /**
     * Counts the number of professors in a school board.
     *
     * @param professorTable the boolean array representing the professor table.
     * @return               the number of professors in the professor table.
     */
    private int countProfessors(boolean[] professorTable) {
        int count = 0;
        for (boolean p : professorTable){
            if(p) count++;
        }
        return count;
    }

    /**
     * Broadcasts a message to all the players communicating who is the winner of
     * the game.
     *
     * @param winnerNickname the nickname of the Player who won.
     * @param condition      the winning condition.
     */
    public void warnPlayersAboutGameEnd(String winnerNickname, String condition){
        broadcastMessage(new IsWinner(winnerNickname, condition, game.getBoard().getLightBoard()));
    }

    /**
     * States whether the game has ended.
     *
     * @return              a boolean whose value is:
     *                      <p>
     *                      -{@code true} if the game ended;
     *                      </p> <p>
     *                      -{@code false} otherwise.
     *                      </p>
     */
    public boolean isGameEnded() {
        return gameEnded;
    }

}
