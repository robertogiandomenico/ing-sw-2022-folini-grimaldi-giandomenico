package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

public class PhaseEntering extends GenericServerMessage {
    private final String phaseName;

    public PhaseEntering(String phaseName) {
        super(MessageType.PHASE_ENTERING);
        this.phaseName = phaseName;
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

    public String getPhaseName() {
        return phaseName;
    }
}