package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.serverMessages.GameModeRequest;
import it.polimi.ingsw.network.messages.serverMessages.GameNameRequest;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

/**
 * This class represents a message that the client sends to the server, that
 * contains the chosen game name.
 * Follows a {@link it.polimi.ingsw.network.messages.serverMessages.GameNameRequest GameNameRequest}.
 */
public class GameNameReply extends GenericClientMessage {
    private static final long serialVersionUID = 3624677593154184852L;
    private String gameName;

    /**
     * Class constructor.
     *
     * @param gameName  the name of the Game.
     */
    public GameNameReply(String gameName) {
        super(MessageType.GAMENAME_REPLY);
        this.gameName = gameName;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        if(!(clientHandler.getClientHandlerPhase() == ClientHandlerPhases.WAITING_GAMENAME)){
            return;
        }
        String REGEX = "^([a-zA-Z]\\w{2,10})$";
        if(gameName == null || !gameName.matches(REGEX)){
            clientHandler.sendMsgToClient(new GameNameRequest());
        } else if(!alreadyExists(gameName, server)){
            //if a game with that name doesn't already exist
            Controller controller = new Controller(gameName);
            server.getLobbies().put(controller, -1);
            clientHandler.setClientHandlerPhase(ClientHandlerPhases.WAITING_GAMEMODE);
            server.addToLobby(gameName, clientHandler);
            clientHandler.sendMsgToClient(new GameModeRequest());
        } else {
            if(!server.getLobbies().keySet().stream().filter(c -> c.getGameName().equalsIgnoreCase(gameName)).findFirst().get().isGameStarted()){
                //if a game with that name exists, and it hasn't started yet
                server.addToLobby(gameName, clientHandler);
            } else {
                //if a game with that name exists, but it has already started, server asks the client for a game name
                clientHandler.sendMsgToClient(new GameNameRequest());
            }
        }
    }

    /**
     * States whether a game with the given name already exists on the server.
     *
     * @param gameName  the name of the game to check.
     * @param server    the Server.
     * @return          a boolean whose value is:
     *                  <p>
     *                  -{@code true} if a game with that name already exists;
     *                  </p> <p>
     *                  -{@code false} otherwise.
     *                  </p>
     */
    private boolean alreadyExists(String gameName, Server server) {
        return server.getLobbies().keySet().stream().map(Controller::getGameName).anyMatch(n -> n.equals(gameName));
    }
}