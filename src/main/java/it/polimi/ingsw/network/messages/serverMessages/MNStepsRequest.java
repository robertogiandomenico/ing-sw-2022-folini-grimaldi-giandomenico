package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class MNStepsRequest extends GenericServerMessage {
    int maxMNSteps;

    public MNStepsRequest(int maxMNSteps) {
        super(MessageType.MNSTEPS_REQUEST);
        this.maxMNSteps = maxMNSteps;
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }

    public int getMaxMNSteps() {
        return maxMNSteps;
    }
}