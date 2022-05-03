package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class MNStepsReply extends GenericClientMessage {
    private int mnSteps;

    public MNStepsReply(int mnSteps) {
        super(MessageType.MNSTEPS_REPLY);
        this.mnSteps = mnSteps;
    }

    public int getMnSteps() {
        return mnSteps;
    }
}