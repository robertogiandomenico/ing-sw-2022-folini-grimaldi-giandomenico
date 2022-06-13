package it.polimi.ingsw.network.messages.connectionMessages;

import it.polimi.ingsw.network.messages.MessageType;

import java.io.Serializable;

/**
 * This abstract class represents a generic message regarding connection.
 */
public abstract class GenericConnectionMessage implements Serializable {
    private final MessageType type;

    /**
     * Class constructor specifying the type of the message.
     *
     * @param type     the MessageType.
     */
    public GenericConnectionMessage(MessageType type){
        this.type = type;
    }

    /**
     * Gets the type of the message.
     *
     * @return         the MessageType.
     */
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
