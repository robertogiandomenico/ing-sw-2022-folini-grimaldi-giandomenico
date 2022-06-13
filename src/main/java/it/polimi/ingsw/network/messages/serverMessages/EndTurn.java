package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

/**
 * This class represent a message that the server sends to the client, to
 * inform that their turn has ended.
 */
public class EndTurn extends GenericServerMessage {

    /**
     * Class constructor.
     */
    public EndTurn() {
        super(MessageType.END_TURN);
    }

    /**
     * Shows the message (CLI or GUI).
     *
     * @param view      a ViewInterface.
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