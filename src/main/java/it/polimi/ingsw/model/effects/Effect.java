package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Color;

/**
 * This interface represents the effects of the characters that players can choose
 * during the game.
 * Each effect is then implemented based on its specific features.
 */
public interface Effect {

    /**
     * Modifies the board according to the chosen character.
     *
     * @param board             the Board of the game.
     * @param archiIndex        the index of a selected Archipelago.
     * @param numOfStudents     the number of Students employed for the effect.
     * @param studColors        a variable number of Colors (depending on how many
     *                          students the effect affects).
     */
    //Color...studColor means that the function can take a variable number of colors (this is useful because some effects
    //affect only one student while others affect > 1)
    void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors);
}
