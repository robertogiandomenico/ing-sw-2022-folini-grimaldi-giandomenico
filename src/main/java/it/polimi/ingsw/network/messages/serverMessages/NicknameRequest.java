package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class NicknameRequest extends GenericServerMessage {

    public NicknameRequest() {
        super(MessageType.NICKNAME_REQUEST);
    }

    @Override
    public String toString() {
        return "ServerMessage{" +
                "type=" + getType() +
                '}';
    }
}
