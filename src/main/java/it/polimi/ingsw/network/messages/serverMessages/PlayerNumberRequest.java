package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

/**
 * This class represents a message that the server sends to the client,
 * asking the number of players.
 */
public class PlayerNumberRequest extends GenericServerMessage {
    private static final long serialVersionUID = -2083165574856696922L;

    /**
     * Class constructor.
     */
    public PlayerNumberRequest() {
        super(MessageType.PLAYERNUMBER_REQUEST);
    }

    /**
     * Shows the request (CLI or GUI).
     *
     * @param view      a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.askPlayerNumber();
    }
}
