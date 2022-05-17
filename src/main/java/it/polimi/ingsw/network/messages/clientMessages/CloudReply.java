package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class CloudReply extends GenericClientMessage {
    private int cloudIndex;

    public CloudReply(int cloudIndex) {
        super(MessageType.CLOUD_REPLY);
        this.cloudIndex = cloudIndex;
    }

    public int getCloudIndex() {
        return cloudIndex;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {

    }
}