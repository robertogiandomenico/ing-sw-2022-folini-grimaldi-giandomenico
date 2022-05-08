package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class PlaceRequest extends GenericServerMessage {
    private int maxArchis;

    public PlaceRequest(int maxArchis) {
        super(MessageType.PLACE_REQUEST);
        this.maxArchis = maxArchis;
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }

    public int getMaxArchis() {
        return maxArchis;
    }
}