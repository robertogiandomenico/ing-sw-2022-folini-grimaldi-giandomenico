package it.polimi.ingsw.network.messages.connectionMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.serverMessages.GenericServerMessage;

public class Ping extends GenericConnectionMessage {

    public Ping() {
        super(MessageType.PING);
    }
}
