package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class PlayerNumberReply extends GenericClientMessage {
    private int playerNumber;

    public PlayerNumberReply(int playerNumber) {
        super(MessageType.PLAYERNUMBER_REPLY);
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        if(!(clientHandler.getClientHandlerPhase() == ClientHandlerPhases.WAITING_PLAYERNUMBER)){
            return;
        }
        server.getLobbies().replace(clientHandler.getController(), playerNumber);
        server.checkLobby(clientHandler.getController());
    }
}