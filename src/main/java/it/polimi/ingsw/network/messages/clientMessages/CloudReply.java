package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class CloudReply extends GenericClientMessage {
    private int cloudIndex;

    public CloudReply(int cloudIndex) {
        super(MessageType.CLOUD_REPLY);
        this.cloudIndex = cloudIndex;
    }

    public int getCloudIndex() {
        return cloudIndex;
    }
}