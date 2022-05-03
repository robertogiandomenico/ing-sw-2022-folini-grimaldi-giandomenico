package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class Ping extends GenericServerMessage {

    public Ping() {
        super(MessageType.PING);
    }

}
