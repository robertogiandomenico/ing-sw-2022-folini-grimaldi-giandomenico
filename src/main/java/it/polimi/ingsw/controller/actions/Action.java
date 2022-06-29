package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;

/**
 * This interface represents the actions that can be done by a player during
 * a single turn.
 */
public interface Action {

    /**
     * Executes the action.
     */
    void execute();

    /**
     * Returns the type of the action.
     *
     * @return      the ActionType.
     */
    ActionType getType();

    /**
     * Handles a received message.
     *
     * @param msg   the received message.
     */
    void receiveMessage(GenericClientMessage msg);
}
