package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

/**
 * This class represents a message that the client sends to the server, to
 * move Mother Nature of a certain number of steps.
 * Follows a {@link it.polimi.ingsw.network.messages.serverMessages.MNStepsRequest MNStepsRequest}.
 */
public class MNStepsReply extends GenericClientMessage {
    private int mnSteps;

    /**
     * Class constructor.
     *
     * @param mnSteps   the number of steps.
     */
    public MNStepsReply(int mnSteps) {
        super(MessageType.MNSTEPS_REPLY);
        this.mnSteps = mnSteps;
    }

    /**
     * Returns the number of steps.
     *
     * @return          the chosen number of steps.
     */
    public int getMnSteps() {
        return mnSteps;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        clientHandler.getCurrentAction().receiveMessage(this);
    }
}