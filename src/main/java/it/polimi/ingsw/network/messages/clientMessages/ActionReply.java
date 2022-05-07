package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class ActionReply extends GenericClientMessage {
    private ActionType action;

    public ActionReply(ActionType action) {
        super(MessageType.ACTION_REPLY);
        this.action = action;
    }

    public ActionType getAction() {
        return action;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {

    }
}