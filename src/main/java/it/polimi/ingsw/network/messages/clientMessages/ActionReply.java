package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.network.messages.MessageType;

public class ActionReply extends GenericClientMessage {
    private ActionType action;

    public ActionReply(ActionType action) {
        super(MessageType.ACTION_REPLY);
        this.action = action;
    }

    public ActionType getAction() {
        return action;
    }
}