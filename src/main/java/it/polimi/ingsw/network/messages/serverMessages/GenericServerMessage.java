package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;

public abstract class GenericServerMessage {
    private MessageType type;

    public GenericServerMessage(MessageType type){
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + type +
                '}';
    }
}
