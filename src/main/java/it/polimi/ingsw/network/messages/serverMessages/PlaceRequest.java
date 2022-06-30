package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

/**
 * This class represents a message that the server sends to the client,
 * asking a place to move a student.
 */
public class PlaceRequest extends GenericServerMessage {
    private static final long serialVersionUID = 7060318590315655178L;
    private final int maxArchis;

    /**
     * Class constructor.
     *
     * @param maxArchis     the number of Archipelagos.
     */
    public PlaceRequest(int maxArchis) {
        super(MessageType.PLACE_REQUEST);
        this.maxArchis = maxArchis;
    }

    /**
     * Shows the request (CLI or GUI).
     *
     * @param view          a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.askPlace(maxArchis);
    }
}