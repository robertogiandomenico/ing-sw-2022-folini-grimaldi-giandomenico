package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

/**
 * This class represents a message that the server sends to the client,
 * asking the game mode.
 */
public class GameModeRequest extends GenericServerMessage {
    private static final long serialVersionUID = 4873171479370449853L;

    /**
     * Class constructor.
     */
    public GameModeRequest() {
        super(MessageType.GAMEMODE_REQUEST);
    }

    /**
     * Shows the request (CLI or GUI).
     *
     * @param view      a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.askGameMode();
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}
