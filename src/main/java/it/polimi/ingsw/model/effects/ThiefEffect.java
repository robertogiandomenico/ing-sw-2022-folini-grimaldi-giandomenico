package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

/**
 * This class implements the {@link Effect} of the Thief character.
 * 3 students of a selected color must be returned by EVERY player's dining room
 * to the bag.
 */
public class ThiefEffect implements Effect {

    /**
     * Applies the changes caused by the effect of the Thief character.
     * Removes 3 students of the selected color from every player's dining room. If
     * players have less than 3 students, as many as they have are returned.
     *
     * @param board                the Board of the game.
     * @param archiIndex           the selected Archipelago (unused in this case).
     * @param numOfStudents        the number of Students.
     * @param studColors           the Colors of the Students.
     */
    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        int indexSelectedColor = board.mapToIndex(studColors[numOfStudents-1]);

        for(SchoolBoard s : board.getPlayerBoards()) {
            for(int i=0; i<3; i++) {
                if(s.getDiningRoom()[indexSelectedColor] > 0) {
                    s.removeFromDiningRoom(indexSelectedColor, true);
                    board.getBag().put(new Student(studColors[numOfStudents-1]));
                } else {
                    break;
                }
            }
        }

    }

}