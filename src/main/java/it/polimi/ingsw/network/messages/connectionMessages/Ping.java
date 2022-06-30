package it.polimi.ingsw.network.messages.connectionMessages;

import it.polimi.ingsw.network.messages.MessageType;

/**
 * This class represents a ping message.
 */
public class Ping extends GenericConnectionMessage {
    private static final long serialVersionUID = 6607713467234885884L;

    /**
     * Sends a ping message.
     */
    public Ping() {
        super(MessageType.PING);
    }
}
