package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class PlayerNumberReply extends GenericClientMessage {
    private int playerNumber;

    public PlayerNumberReply(int playerNumber) {
        super(MessageType.PLAYERNUMBER_REPLY);
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}