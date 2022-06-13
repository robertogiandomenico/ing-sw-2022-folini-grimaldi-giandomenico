package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

/**
 * This class represents a message that the server sends to the client,
 * asking to choose a nickname.
 */
public class NicknameRequest extends GenericServerMessage {

    /**
     * Class constructor.
     */
    public NicknameRequest() {
        super(MessageType.NICKNAME_REQUEST);
    }

    /**
     * Shows the request (CLI or GUI).
     *
     * @param view      a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.askNickname();
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}
