package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class MNStepsReply extends GenericClientMessage {
    private int mnSteps;

    public MNStepsReply(int mnSteps) {
        super(MessageType.MNSTEPS_REPLY);
        this.mnSteps = mnSteps;
    }

    public int getMnSteps() {
        return mnSteps;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        clientHandler.getCurrentAction().receiveMessage(this);
    }
}