package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

import java.io.Serializable;

/**
 * This abstract class represents a generic message sent by the server to the
 * client.
 */
public abstract class GenericServerMessage implements Serializable {
    private static final long serialVersionUID = -4496975448812853270L;
    private final MessageType type;

    /**
     * Class constructor specifying the type of the message.
     *
     * @param type     the MessageType.
     */
    public GenericServerMessage(MessageType type){
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

    /**
     * Shows the message (CLI or GUI).
     *
     * @param view     a ViewInterface.
     */
    public abstract void show(ViewInterface view);

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + type +
                '}';
    }
}
