package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

/**
 * This class represents a message that the server sends to the client, to
 * inform that they are entering a new phase of the game.
 */
public class PhaseEntering extends GenericServerMessage {
    private final String phaseName;

    /**
     * Class constructor.
     *
     * @param phaseName     the name of the game phase.
     */
    public PhaseEntering(String phaseName) {
        super(MessageType.PHASE_ENTERING);
        this.phaseName = phaseName;
    }

    /**
     * Shows the message (CLI or GUI).
     *
     * @param view          a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {

    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}