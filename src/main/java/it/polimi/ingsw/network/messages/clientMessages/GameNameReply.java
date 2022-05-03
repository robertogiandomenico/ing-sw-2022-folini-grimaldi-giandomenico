package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class GameNameReply extends GenericClientMessage {
    private String gameName;
    public GameNameReply(String gameName) {
        super(MessageType.GAMENAME_REPLY);
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }
}