package it.polimi.ingsw.controller.phases.turnPhases;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.controller.actions.ActionType;

import java.util.List;

public class ChooseAssistantPhase implements TurnPhase {
    @Override
    public void execute(TurnController controller) {

    }

    @Override
    public List<ActionType> getAvailableTurnActions() {
        return List.of(ActionType.CHOOSE_ASSISTANT_ACTION);
    }
}
