package it.polimi.ingsw.controller.phases.turnPhases;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.controller.actions.Action;
import it.polimi.ingsw.controller.actions.ActionType;

import java.util.Collection;
import java.util.List;

/**
 * This interface represents all the turn phases.
 */
public interface TurnPhase {

    /**
     * Returns the available turn actions.
     *
     * @return      an ActionType List of the available actions for this turn phase.
     */
    List<ActionType> getAvailableTurnActions();
}
