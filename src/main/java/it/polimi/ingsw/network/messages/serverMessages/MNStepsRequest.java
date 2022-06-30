package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

/**
 * This class represents a message that the server sends to the client,
 * asking the number of steps for a Mother Nature movement.
 */
public class MNStepsRequest extends GenericServerMessage {
    private static final long serialVersionUID = -7020861182647833795L;
    private final int maxMNSteps;

    /**
     * Class constructor.
     *
     * @param maxMNSteps    the maximum number of steps.
     */
    public MNStepsRequest(int maxMNSteps) {
        super(MessageType.MNSTEPS_REQUEST);
        this.maxMNSteps = maxMNSteps;
    }

    /**
     * Shows the request (CLI or GUI).
     *
     * @param view          a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.askMNSteps(maxMNSteps);
    }
}