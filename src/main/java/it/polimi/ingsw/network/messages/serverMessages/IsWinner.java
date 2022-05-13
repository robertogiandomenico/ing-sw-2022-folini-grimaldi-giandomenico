package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

public class IsWinner extends GenericServerMessage {
    private boolean result;

    public IsWinner(boolean isWinner) {
        super(MessageType.RESULT);
        this.result = isWinner;
    }

    @Override
    public void show(ViewInterface view) {

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
