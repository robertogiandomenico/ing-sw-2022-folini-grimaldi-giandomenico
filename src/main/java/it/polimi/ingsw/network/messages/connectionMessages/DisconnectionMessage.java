package it.polimi.ingsw.network.messages.connectionMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

/**
 * This class represents a message sent when a disconnection occurs.
 */
public class DisconnectionMessage extends GenericConnectionMessage {
    private static final long serialVersionUID = 3339787176056507046L;
    private final String clientNickname;

    /**
     * Class constructor.
     *
     * @param clientNickname    the nickname of the disconnected Player.
     */
    public DisconnectionMessage(String clientNickname) {
        super(MessageType.DISCONNECTION);
        this.clientNickname = clientNickname;
    }

    /**
     * Shows the message (CLI or GUI).
     *
     * @param view              a ViewInterface.
     */
    public void show(ViewInterface view){
        view.displayDisconnectionMessage(clientNickname, " disconnected, the game will end");
    }
}
