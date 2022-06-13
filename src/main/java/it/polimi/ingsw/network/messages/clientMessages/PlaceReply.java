package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

/**
 * This class represents a message that the client sends to the server, that
 * contains the information needed when a student has to be moved.
 * Follows a {@link it.polimi.ingsw.network.messages.serverMessages.PlaceRequest PlaceRequest}.
 */
public class PlaceReply extends GenericClientMessage {
    private final String place;
    private final int archiIndex;

    /**
     * Class constructor specifying the place.
     *
     * @param place      the chosen place.
     */
    public PlaceReply(String place) {
        super(MessageType.PLACE_REPLY);
        this.place = place;
        archiIndex = -1;
    }

    /**
     * Class constructor specifying the place and the index of an archipelago.
     *
     * @param place      the chosen place.
     * @param archiIndex the index of the chosen Archipelago.
     */
    public PlaceReply(String place, int archiIndex) {
        super(MessageType.PLACE_REPLY);
        this.place = place;
        this.archiIndex = archiIndex;
    }

    /**
     * Returns the place.
     *
     * @return          the chosen place.
     */
    public String getPlace() {
        return place;
    }

    /**
     * Returns the index of the archipelago.
     *
     * @return          the index of the chosen Archipelago.
     */
    public int getArchiIndex() {
        return archiIndex;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        clientHandler.getCurrentAction().receiveMessage(this);
    }
}