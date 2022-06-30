package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

import java.util.List;

/**
 * This class represents a message that the server sends to the client,
 * asking what action they want to undertake.
 */
public class ActionRequest extends GenericServerMessage {
    private static final long serialVersionUID = -7361173493858617269L;
    private final List<ActionType> possibleActions;
    private final LightBoard lightBoard;

    /**
     * Class constructor.
     *
     * @param possibleActions   an ActionType List of possible actions.
     * @param lightBoard        the LightBoard.
     */
    public ActionRequest(List<ActionType> possibleActions, LightBoard lightBoard) {
        super(MessageType.ACTION_REQUEST);
        this.possibleActions = possibleActions;
        this.lightBoard = lightBoard;
    }

    /**
     * Shows the request (CLI or GUI).
     *
     * @param view      a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.printBoard(lightBoard);
        view.askAction(possibleActions);
    }
}
