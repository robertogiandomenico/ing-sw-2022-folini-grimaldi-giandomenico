package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

import java.io.Serializable;

/**
 * This abstract class represents a generic message sent by the client to the
 * server.
 */
public abstract class GenericClientMessage implements Serializable {
    private static final long serialVersionUID = 6277215629364450602L;
    private final MessageType type;

    /**
     * Class constructor specifying the type of the message.
     *
     * @param type             the MessageType.
     */
    public GenericClientMessage(MessageType type){
        this.type = type;
    }

    /**
     * Gets the type of the message.
     *
     * @return                 the MessageType.
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Executes the specific action based on the message.
     *
     * @param server           the Server.
     * @param clientHandler    the ClientHandler.
     */
    public abstract void execute(Server server, ClientHandler clientHandler);
}
