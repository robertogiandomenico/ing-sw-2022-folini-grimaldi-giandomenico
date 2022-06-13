package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

/**
 * This class represents a message that the client sends to the server, that
 * contains the index of the action the user wants to proceed with.
 * Follows an {@link it.polimi.ingsw.network.messages.serverMessages.ActionRequest ActionRequest}.
 */
public class ActionReply extends GenericClientMessage {
    private int actionIndex;

    /**
     * Class constructor.
     *
     * @param actionIndex   the index of the action.
     */
    public ActionReply(int actionIndex) {
        super(MessageType.ACTION_REPLY);
        this.actionIndex = actionIndex;
    }

    /**
     * Returns the action.
     *
     * @return              the index of the action.
     */
    public int getAction() {
        return actionIndex;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        clientHandler.getController().receiveMessage(this);
    }
}