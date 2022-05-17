package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

public class GameModeRequest extends GenericServerMessage {

    public GameModeRequest() {
        super(MessageType.GAMEMODE_REQUEST);
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
}
