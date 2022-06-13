package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

/**
 * This class represents a message that the server sends to the client, to
 * inform that their turn is starting.
 */
public class StartTurn extends GenericServerMessage {

    /**
     * Class constructor.
     */
    public StartTurn() {
        super(MessageType.START_TURN);
    }

    /**
     * Shows the message (CLI or GUI).
     *
     * @param view          a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {

    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}