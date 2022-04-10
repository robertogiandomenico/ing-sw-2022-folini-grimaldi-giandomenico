package it.polimi.ingsw.model;

public class FarmerEffect implements Effect {

    public void applyEffect(Board board) {
        //activate the flag FarmerEffect
        board.getCurrentPlayerSchoolBoard().setFarmerEffect(true);

        //call checkProfessorMovement() for EVERY COLOR to check any tie of number of students between players in the diningRoom
        for (int i=0; i<5; i++) {
            board.getCurrentPlayerSchoolBoard().checkProfessorMovement(i, "add");
        }

        //set the flag FarmerEffect back to false
        /* TODO: flag FarmerEffect must go back to false  ** AT THE END OF THE TURN **, NOT IMMEDIATELY, need controller(?) to manage turns and send a signal to de-activate farmerEffect */
        board.getCurrentPlayerSchoolBoard().setFarmerEffect(true);
    }

}