package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;

public class ActionRequest extends GenericServerMessage {
    private List<ActionType> possibleActions;

    public ActionRequest(List<ActionType> possibleActions) {
        super(MessageType.ACTION_REQUEST);
        this.possibleActions = possibleActions;
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

    public List<ActionType> getPossibleActions() {
        return possibleActions;
    }
}
