package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.List;

public class ActionRequest extends GenericServerMessage {
    private List<ActionType> possibleActions;

    public ActionRequest(List<ActionType> possibleActions) {
        super(MessageType.ACTION_REQUEST);
        this.possibleActions = possibleActions;
    }

    @Override
    public void show() {

    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }

    public List<ActionType> getPossibleActions() {
        return possibleActions;
    }
}
