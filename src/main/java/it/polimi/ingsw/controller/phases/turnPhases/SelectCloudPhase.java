package it.polimi.ingsw.controller.phases.turnPhases;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.controller.actions.ActionType;

import java.util.ArrayList;
import java.util.List;

public class SelectCloudPhase implements TurnPhase {
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
