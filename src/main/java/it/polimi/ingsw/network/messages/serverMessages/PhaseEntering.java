package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class PhaseEntering extends GenericServerMessage {
    private final String phaseName;

    public PhaseEntering(String phaseName) {
        super(MessageType.PHASE_ENTERING);
        this.phaseName = phaseName;
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }

    public String getPhaseName() {
        return phaseName;
    }
}