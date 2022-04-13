package it.polimi.ingsw.model;

public class HeraldEffect implements Effect {

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        Archipelago selectedArchipelago = board.getArchipelago(archiIndex);
        board.calculateInfluence(selectedArchipelago);

    }

}