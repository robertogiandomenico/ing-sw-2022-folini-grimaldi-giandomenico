package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class GameNameRequest extends GenericServerMessage {

    public GameNameRequest() {
        super(MessageType.GAMENAME_REQUEST);
    }

    @Override
    public void show() {

    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}
