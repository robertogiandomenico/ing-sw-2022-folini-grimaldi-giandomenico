package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class GameModeReply extends GenericClientMessage {
    private boolean gameMode;
    public GameModeReply(boolean gameMode) {
        super(MessageType.GAMEMODE_REPLY);
        this.gameMode = gameMode;
    }

    public boolean getGameMode() {
        return gameMode;
    }
}