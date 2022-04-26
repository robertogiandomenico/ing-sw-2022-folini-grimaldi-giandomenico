package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

/**
 * This class implements the {@link Effect} of the Herald character.
 * A chosen island is resolved as if Mother Nature had ended her movement there.
 */
public class HeraldEffect implements Effect {

    /**
     * Applies the changes caused by the effect of the Herald character.
     * Influence is calculated on the given archipelago.
     *
     * @param board                the Board of the game.
     * @param archiIndex           the selected Archipelago.
     * @param numOfStudents        the number of Students (unused in this case).
     * @param studColors           the Colors of the Students (unused in this case).
     */
    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        Archipelago selectedArchipelago = board.getArchipelago(archiIndex);
        board.calculateInfluence(selectedArchipelago);

    }

}