package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

/**
 * This class represents a message that the server sends to the client,
 * asking the game name.
 */
public class GameNameRequest extends GenericServerMessage {

    /**
     * Class constructor.
     */
    public GameNameRequest() {
        super(MessageType.GAMENAME_REQUEST);
    }

    /**
     * Shows the request (CLI or GUI).
     *
     * @param view      a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.askGameName();
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}
