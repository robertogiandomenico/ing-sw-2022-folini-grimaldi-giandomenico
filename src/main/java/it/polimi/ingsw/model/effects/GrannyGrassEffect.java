package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

/**
 * This class implements the {@link Effect} of the Granny Grass character.
 * Who uses this character can put a No Entry tile on an island of their choice,
 * and the first time Mother Nature ends there, the influence isn't calculated on
 * that island.
 */
public class GrannyGrassEffect implements Effect {
    private int noEntryTiles = 4;

    /**
     * Applies the changes caused by the effect of the Granny Grass character.
     * Puts a No Entry Tile on the given archipelago.
     *
     * @param board                the Board of the game.
     * @param archiIndex           the selected Archipelago.
     * @param numOfStudents        the number of Students (unused in this case).
     * @param studColors           the Colors of the Students (unused in this case).
     */
    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        if(noEntryTiles > 0 && noEntryTiles <= 4) {
            board.getArchipelago(archiIndex).setNoEntryTile(board.getArchipelago(archiIndex).getNoEntryTile() + 1);
            noEntryTiles--;
        }
    }

    /**
     * Puts a No Entry tile back on the card after its use.
     */
    public void putBackTile() {
        noEntryTiles++;
    }

    /**
     * Returns the number of No Entry tiles on the card.
     *
     * @return                     the number of No Entry tiles.
     */
    public int getNoEntryTiles() {
        return noEntryTiles;
    }
}