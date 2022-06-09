package it.polimi.ingsw.network.messages.connectionMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

public class DisconnectionMessage extends GenericConnectionMessage {

    private final String clientNickname;

    public DisconnectionMessage(String clientNickname) {
        super(MessageType.DISCONNECTION);
        this.clientNickname = clientNickname;
    }

    public void show(ViewInterface view){
        view.displayDisconnectionMessage(clientNickname, " disconnected, the game will end");
    }
}
