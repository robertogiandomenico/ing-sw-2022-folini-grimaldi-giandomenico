package it.polimi.ingsw.effects;
import it.polimi.ingsw.model.*;

public class KnightEffect implements Effect {

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        board.getCurrentPlayerSchoolBoard().setAdditionalInfluence(2);
    }

    /* TODO: AdditionalInfluence must go back to 0  ** AT THE END OF THE TURN **, NOT IMMEDIATELY, need controller(?) to manage turns and send a signal to de-activate the effect */
}