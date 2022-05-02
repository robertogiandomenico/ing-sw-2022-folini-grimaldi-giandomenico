package it.polimi.ingsw.controller.phases.turnPhases;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.controller.actions.ActionType;

import java.util.List;

public class MoveMNPhase implements TurnPhase {
    @Override
    public void execute(TurnController controller) {

    }

    @Override
    public List<ActionType> getAvailableTurnActions() {
        return List.of(ActionType.MOVE_MN_ACTION, ActionType.BUY_CHARACTER_ACTION);
    }
}
