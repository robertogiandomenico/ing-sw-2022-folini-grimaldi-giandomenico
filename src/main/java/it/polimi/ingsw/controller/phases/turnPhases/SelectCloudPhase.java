package it.polimi.ingsw.controller.phases.turnPhases;

import it.polimi.ingsw.controller.actions.ActionType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the turn phase in which the player has to choose
 * the cloud to draw from.
 */
public class SelectCloudPhase implements TurnPhase {

    /**
     * Returns the available turn actions.
     *
     * @return      an ActionType List of the available actions for this turn phase.
     */
    @Override
    public List<ActionType> getAvailableTurnActions() {
        List<ActionType> actions = new ArrayList<>();
        actions.add(ActionType.SELECT_CLOUD_ACTION);
        return actions;
    }

    @Override
    public String toString() {
        return "SelectCloudPhase";
    }

}
