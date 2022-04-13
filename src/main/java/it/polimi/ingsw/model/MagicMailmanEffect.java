package it.polimi.ingsw.model;

public class MagicMailmanEffect implements Effect {

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        board.getCurrentPlayerSchoolBoard().getPlayer().setAdditionalSteps();
    }

}