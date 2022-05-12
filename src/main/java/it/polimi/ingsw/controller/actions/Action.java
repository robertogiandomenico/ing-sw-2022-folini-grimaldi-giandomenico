package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;

public interface Action {

    void execute();

    void resetAction(Player currentPlayer);

    ActionType getType();

    void receiveMessage(GenericClientMessage msg);
}
