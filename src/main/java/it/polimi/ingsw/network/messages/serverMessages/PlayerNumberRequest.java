package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class PlayerNumberRequest extends GenericServerMessage{

    public PlayerNumberRequest() {
        super(MessageType.PLAYERNUMBER_REQUEST);
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }


}
