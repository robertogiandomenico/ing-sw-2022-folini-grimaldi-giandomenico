package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

/**
 * This class implements the {@link Effect} of the Centaur character.
 * When resolving a conquering on the selected island, towers (if present) do not
 * count towards influence.
 */
public class CentaurEffect implements Effect {

    /**
     * Applies the changes caused by the effect of the Centaur character.
     * Sets the number of towers present as negative, to balance influence count.
     *
     * @param board                the Board of the game.
     * @param archiIndex           the selected Archipelago.
     * @param numOfStudents        the number of Students (unused in this case).
     * @param studColors           the Colors of the Students (unused in this case).
     */
    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        Archipelago selectedArchipelago = board.getArchipelago(archiIndex);
        TowerColor team = selectedArchipelago.getTowerColor();

        if(team != null) {
            int negativeInfluence;

            /* negativeInfluence is equal to the n. of towers in the archipelago, which corresponds with the actual n. of islands
            in the archipelago since every island can have one and one only tower */
            negativeInfluence = selectedArchipelago.getIslands().size() * (-1);

            board.getPlayerSchoolBoardByTeam(team).setAdditionalInfluence(negativeInfluence);
        }
    }
}