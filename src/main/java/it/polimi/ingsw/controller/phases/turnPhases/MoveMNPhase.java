package it.polimi.ingsw.controller.phases.turnPhases;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.controller.actions.ActionType;

import java.util.ArrayList;
import java.util.List;

public class MoveMNPhase implements TurnPhase {
    @Override
    public void execute(TurnController controller) {

    }

    @Override
    public List<ActionType> getAvailableTurnActions() {
        List<ActionType> actions = new ArrayList<>();
        actions.add(ActionType.MOVE_MN_ACTION);
        return actions;
    }

    @Override
    public String toString() {
        return "MoveMNPhase";
    }
}
