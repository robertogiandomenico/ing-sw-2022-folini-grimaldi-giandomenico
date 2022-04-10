package it.polimi.ingsw.model;

public class KnightEffect implements Effect {

    public void applyEffect(Board board) {
        board.getCurrentPlayerSchoolBoard().setAdditionalInfluence(2);

        /* is it really the applyEffect that must call the calculateInfluence() ?
        board.calculateInfluence(selectedArchipelago); */

        //additionalInfluence goes back to 0
        /* TODO: AdditionalInfluence must go back to 0  ** AT THE END OF THE TURN **, NOT IMMEDIATELY, need controller(?) to manage turns and send a signal to de-activate the effect */
        board.getCurrentPlayerSchoolBoard().setAdditionalInfluence(0);
    }

}