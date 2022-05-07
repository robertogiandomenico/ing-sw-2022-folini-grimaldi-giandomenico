package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.serverMessages.PlayerNumberRequest;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class GameModeReply extends GenericClientMessage {
    private boolean gameMode; //true if expertMode, otherwise false
    public GameModeReply(boolean gameMode) {
        super(MessageType.GAMEMODE_REPLY);
        this.gameMode = gameMode;
    }

    public boolean getGameMode() {
        return gameMode;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        if(!(clientHandler.getClientHandlerPhase() == ClientHandlerPhases.WAITING_GAMEMODE)){
            return;
        }
        clientHandler.getController().setExpertMode(gameMode);
        clientHandler.setClientHandlerPhase(ClientHandlerPhases.WAITING_PLAYERNUMBER);
        clientHandler.sendMsgToClient(new PlayerNumberRequest());
    }
}