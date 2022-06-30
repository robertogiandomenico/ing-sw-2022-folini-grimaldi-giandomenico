package it.polimi.ingsw.controller.phases.turnPhases;

import it.polimi.ingsw.controller.actions.ActionType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the turn phase in which Mother Nature can be moved.
 */
public class MoveMNPhase implements TurnPhase {

    /**
     * Returns the available turn actions.
     *
     * @return      an ActionType List of the available actions for this turn phase.
     */
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
