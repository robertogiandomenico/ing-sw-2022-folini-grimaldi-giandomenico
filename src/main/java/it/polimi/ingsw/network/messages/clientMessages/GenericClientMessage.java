package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

import java.io.Serializable;

public abstract class GenericClientMessage implements Serializable {
    MessageType type;

    public GenericClientMessage(MessageType type){
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public abstract void execute(Server server, ClientHandler clientHandler);
    @Override
    public String toString() {
        return "ClientMessage {" +
                "type=" + type +
                '}';
    }
}
