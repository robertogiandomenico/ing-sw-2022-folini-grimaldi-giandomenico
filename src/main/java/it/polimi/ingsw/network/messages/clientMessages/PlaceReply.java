package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class PlaceReply extends GenericClientMessage {

    private String place;

    public PlaceReply(String place) {
        super(MessageType.PLACE_REPLY);
        this.place = place;
    }

    public String getPlace() {
        return place;
    }
}