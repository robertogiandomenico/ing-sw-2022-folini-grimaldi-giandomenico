package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

/**
 * This class represents a generic text message that the server sends
 * to the client.
 */
public class TextMessage extends GenericServerMessage {
    private final String message;

    /**
     * Class constructor.
     *
     * @param message       the message to display.
     */
    public TextMessage(String message) {
        super(MessageType.TEXT);
        this.message = message;
    }

    /**
     * Shows the message (CLI or GUI).
     *
     * @param view          a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.displayMessage(message);
    }
}
