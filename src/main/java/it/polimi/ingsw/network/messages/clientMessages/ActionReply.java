package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class ActionReply extends GenericClientMessage {
    private int actionIndex;

    public ActionReply(int actionIndex) {
        super(MessageType.ACTION_REPLY);
        this.actionIndex = actionIndex;
    }

    public int getAction() {
        return actionIndex;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        clientHandler.getController().receiveMessage(this);
    }
}