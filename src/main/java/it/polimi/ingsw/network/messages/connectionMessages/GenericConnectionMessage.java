package it.polimi.ingsw.network.messages.connectionMessages;

import it.polimi.ingsw.network.messages.MessageType;

import java.io.Serializable;

public abstract class GenericConnectionMessage implements Serializable {
    private final MessageType type;

    public GenericConnectionMessage(MessageType type){
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ConnectionMessage {" +
                "type=" + type +
                '}';
    }
}
