package it.polimi.ingsw.effects;
import it.polimi.ingsw.model.*;

public class FarmerEffect implements Effect {

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        //activate the flag FarmerEffect
        SchoolBoard currentPlayerSchoolBoard = board.getCurrentPlayerSchoolBoard();
        currentPlayerSchoolBoard.setFarmerEffect(true);

        //call checkProfessorMovement() for EVERY COLOR to check any tie of number of students between players in the diningRoom
        for (int i=0; i<5; i++) {
            currentPlayerSchoolBoard.checkProfessorMovement(i, "add");
        }


        //TODO: flag FarmerEffect must go back to false  ** AT THE END OF THE TURN **, NOT IMMEDIATELY, need controller(?) to manage turns and send a signal to de-activate farmerEffect */
    }

}