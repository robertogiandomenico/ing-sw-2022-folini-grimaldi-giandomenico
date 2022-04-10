package it.polimi.ingsw.model;

public class MagicMailmanEffect implements Effect {

    public void applyEffect(Board board) {
        board.getCurrentPlayerSchoolBoard().getPlayer().setAdditionalSteps();
    }

}