package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;

import java.io.Serializable;

public abstract class GenericClientMessage implements Serializable {
    private final MessageType type;

    public GenericClientMessage(MessageType type){
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ClientMessage {" +
                "type=" + type +
                '}';
    }
}
