package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

/**
 * This class implements the {@link Effect} of the Mushroom Man character.
 * The color of student chosen by the player adds no influence during the
 * influence calculation.
 */
public class MushroomManEffect implements Effect {

    /**
     * Applies the changes caused by the effect of the Mushroom Man character.
     * Sets the selected color as forbidden.
     *
     *  @param board                the Board of the game (unused in this case).
     *  @param archiIndex           the selected Archipelago (unused in this case).
     *  @param numOfStudents        the number of Students.
     *  @param studColors           the Color of the selected Student.
     */
    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        Archipelago.setForbiddenColor(studColors[numOfStudents-1]);
    }

}