package it.polimi.ingsw.controller.phases.turnPhases;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.controller.actions.Action;
import it.polimi.ingsw.controller.actions.ActionType;

import java.util.Collection;
import java.util.List;

public interface TurnPhase {
    List<ActionType> getAvailableTurnActions();
}
