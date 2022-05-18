package it.polimi.ingsw.controller.phases.turnPhases;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.controller.actions.ActionType;

import java.util.List;

public class MoveStudentsPhase implements TurnPhase {

    @Override
    public void execute(TurnController controller) {

    }

    @Override
    public List<ActionType> getAvailableTurnActions() {
        return List.of(ActionType.MOVE_STUDENT_ACTION,
                       ActionType.MOVE_STUDENT_ACTION,
                       ActionType.MOVE_STUDENT_ACTION,
                       ActionType.BUY_CHARACTER_ACTION);
    }

    @Override
    public String toString() {
        return "MoveStudentsPhase";
    }
}
