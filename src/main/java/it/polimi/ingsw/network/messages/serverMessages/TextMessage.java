package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class TextMessage extends GenericServerMessage {

    private final String message;

    public TextMessage(String message) {
        super(MessageType.TEXT);
        this.message = message;
    }

    @Override
    public void show() {

    }

    public String getMessage() {
        return message;
    }
}
