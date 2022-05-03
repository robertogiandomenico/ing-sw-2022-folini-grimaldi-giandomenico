package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class PlaceRequest extends GenericServerMessage {

    public PlaceRequest() {
        super(MessageType.PLACE_REQUEST);
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}