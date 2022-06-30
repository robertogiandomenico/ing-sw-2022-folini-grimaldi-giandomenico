package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.serverMessages.TextMessage;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

/**
 * This class represents a message that the client sends to the server, that
 * contains the chosen number of players.
 * Follows a {@link it.polimi.ingsw.network.messages.serverMessages.PlayerNumberRequest PlayerNumberRequest}.
 */
public class PlayerNumberReply extends GenericClientMessage {
    private static final long serialVersionUID = 5921830997540223602L;
    private int playerNumber;

    /**
     * Class constructor.
     *
     * @param playerNumber  the chosen number of Players.
     */
    public PlayerNumberReply(int playerNumber) {
        super(MessageType.PLAYERNUMBER_REPLY);
        this.playerNumber = playerNumber;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        if(!(clientHandler.getClientHandlerPhase() == ClientHandlerPhases.WAITING_PLAYERNUMBER)){
            return;
        }
        server.getLobbies().replace(clientHandler.getController(), playerNumber);
        clientHandler.setClientHandlerPhase(ClientHandlerPhases.WAITING_IN_LOBBY);
        clientHandler.sendMsgToClient(new TextMessage("Waiting for the other player(s) to join"));
        server.checkLobby(clientHandler.getController());
    }
}