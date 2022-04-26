package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

/**
 * This class implements the {@link Effect} of the Magic Mailman character.
 * Mother Nature can be moved up to 2 additional islands than it is indicated by
 * the assistant card played.
 */
public class MagicMailmanEffect implements Effect {

    /**
     * Applies the changes caused by the effect of the Magic Mailman character.
     * Sets the additional steps.
     *
     * @param board             the Board of the game (to access current player
     *                          SchoolBoard).
     * @param archiIndex        the selected Archipelago (unused in this case).
     * @param numOfStudents     the number of Students (unused in this case).
     * @param studColors        the Colors of the Students (unused in this case).
     */
    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        board.getCurrentPlayerSchoolBoard().getPlayer().setAdditionalSteps();
    }

}