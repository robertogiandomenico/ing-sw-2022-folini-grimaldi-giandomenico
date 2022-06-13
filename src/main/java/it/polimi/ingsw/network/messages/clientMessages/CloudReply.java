package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

/**
 * This class represents a message that the client sends to the server, that
 * contains the information needed when a player chooses to use a character.
 * Follows a {@link it.polimi.ingsw.network.messages.serverMessages.CloudRequest CloudRequest}.
 */
public class CloudReply extends GenericClientMessage {
    private int cloudIndex;

    /**
     * Class constructor.
     *
     * @param cloudIndex    the index of the chosen Cloud.
     */
    public CloudReply(int cloudIndex) {
        super(MessageType.CLOUD_REPLY);
        this.cloudIndex = cloudIndex;
    }

    /**
     * Returns the index of the cloud.
     *
     * @return              the index of the chosen Cloud.
     */
    public int getCloudIndex() {
        return cloudIndex;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        clientHandler.getCurrentAction().receiveMessage(this);
    }
}