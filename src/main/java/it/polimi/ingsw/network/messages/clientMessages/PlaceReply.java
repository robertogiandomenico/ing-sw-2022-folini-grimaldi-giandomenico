package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class PlaceReply extends GenericClientMessage {

    private final String place;

    private final int archiIndex;

    public PlaceReply(String place) {
        super(MessageType.PLACE_REPLY);
        this.place = place;
        archiIndex = -1;
    }

    public PlaceReply(String place, int archiIndex) {
        super(MessageType.PLACE_REPLY);
        this.place = place;
        this.archiIndex = archiIndex;
    }

    public String getPlace() {
        return place;
    }

    public int getArchiIndex() {
        return archiIndex;
    }
}