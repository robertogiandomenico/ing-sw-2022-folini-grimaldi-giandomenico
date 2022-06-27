package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

/**
 * This class implements the {@link Effect} of the Farmer character.
 * Players who use this character take control (during their turn) of any number
 * of professors even if they have the same number of students as the player who
 * currently controls them.
 */
public class FarmerEffect implements Effect {

    /**
     * Applies the changes caused by the effect of the Farmer character.
     * Checks any tie of number of students between players in the dining room, and in
     * the event that there is any, assigns the professor to the player who used the
     * card.
     *
     * @param board            the Board of the game (to access current player
     *                         SchoolBoard).
     * @param archiIndex       the selected Archipelago (unused in this case).
     * @param numOfStudents    the number of Students (unused in this case).
     * @param studColors       the Colors of the Students (unused in this case).
     */
    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        //activate the flag FarmerEffect
        SchoolBoard currentPlayerSchoolBoard = board.getCurrentPlayerSchoolBoard();
        currentPlayerSchoolBoard.setFarmerEffect(true);

        //call checkProfessorMovement() for EVERY COLOR to check any tie of number of students between players in the diningRoom
        for (int i=0; i<5; i++) {
            currentPlayerSchoolBoard.checkProfessorMovement(i, "add");
        }
    }

}