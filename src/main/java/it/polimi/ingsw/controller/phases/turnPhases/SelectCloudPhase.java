package it.polimi.ingsw.controller.phases.turnPhases;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.controller.actions.ActionType;

import java.util.List;

public class SelectCloudPhase implements TurnPhase {

    @Override
    public void execute(TurnController controller) {

    }

    @Override
    public List<ActionType> getAvailableTurnActions() {
        return List.of(ActionType.SELECT_CLOUD_ACTION);
    }

    @Override
    public String toString() {
        return "SelectCloudPhase";
    }
}
