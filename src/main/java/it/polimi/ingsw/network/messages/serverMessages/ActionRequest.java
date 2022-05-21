package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

import java.util.List;

public class ActionRequest extends GenericServerMessage {
    private final List<ActionType> possibleActions;
    private final LightBoard lightBoard;
    public ActionRequest(List<ActionType> possibleActions, LightBoard lightBoard) {
        super(MessageType.ACTION_REQUEST);
        this.possibleActions = possibleActions;
        this.lightBoard = lightBoard;
    }

    @Override
    public void show(ViewInterface view) {
        view.printBoard(lightBoard);
        view.askAction(possibleActions);
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}
