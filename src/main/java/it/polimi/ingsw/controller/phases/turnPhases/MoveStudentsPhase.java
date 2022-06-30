package it.polimi.ingsw.controller.phases.turnPhases;

import it.polimi.ingsw.controller.actions.ActionType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the turn phase in which the player can move students.
 */
public class MoveStudentsPhase implements TurnPhase {
    private final int numberOfPlayers;

    /**
     * Class constructor.
     *
     * @param numberOfPlayers       the number of players of the game.
     */
    public MoveStudentsPhase(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Returns the available turn actions.
     *
     * @return                      an ActionType List of the available actions for this turn phase.
     */
    @Override
    public List<ActionType> getAvailableTurnActions() {
        List<ActionType> actions = new ArrayList<>();
        actions.add(ActionType.MOVE_STUDENT_ACTION);
        actions.add(ActionType.MOVE_STUDENT_ACTION);
        actions.add(ActionType.MOVE_STUDENT_ACTION);
        if(numberOfPlayers == 3) actions.add(ActionType.MOVE_STUDENT_ACTION);
        return actions;
    }

    @Override
    public String toString() {
        return "MoveStudentsPhase";
    }

}
