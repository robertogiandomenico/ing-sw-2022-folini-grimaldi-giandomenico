package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

/**
 * This class implements the {@link Effect} of the Knight character.
 * During the influence calculation, the player who uses this card counts as
 * having 2 more influence.
 */
public class KnightEffect implements Effect {

    /**
     * Applies the changes caused by the effect of the Knight character.
     * Sets the additional influence.
     *
     * @param board                the Board of the game (to access current player
     *                             SchoolBoard).
     * @param archiIndex           the selected Archipelago (unused in this case).
     * @param numOfStudents        the number of Students (unused in this case).
     * @param studColors           the Colors of the Students (unused in this case).
     */
    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        board.getCurrentPlayerSchoolBoard().setAdditionalInfluence(2);
    }
}