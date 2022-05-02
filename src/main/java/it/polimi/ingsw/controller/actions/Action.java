package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.model.Player;

public interface Action {

    void execute();

    void resetAction(Player currentPlayer);

    ActionType getType();
}
