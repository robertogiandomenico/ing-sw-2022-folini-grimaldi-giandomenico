package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class IsWinner extends GenericServerMessage {
    private boolean result;

    public IsWinner(boolean isWinner) {
        super(MessageType.RESULT);
        this.result = isWinner;
    }

    @Override
    public void show() {

    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }

    public boolean isWinner() {
        return result;
    }
}
