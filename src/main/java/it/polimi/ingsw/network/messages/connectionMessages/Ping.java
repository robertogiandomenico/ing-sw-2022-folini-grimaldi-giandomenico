package it.polimi.ingsw.network.messages.connectionMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.serverMessages.GenericServerMessage;

/**
 * This class represents a ping message.
 */
public class Ping extends GenericConnectionMessage {

    /**
     * Sends a ping message.
     */
    public Ping() {
        super(MessageType.PING);
    }
}
