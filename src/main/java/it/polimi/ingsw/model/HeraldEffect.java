package it.polimi.ingsw.model;

public class HeraldEffect implements Effect {

    public void applyEffect(Board board) {
        int archiIndex = setArchiIndex();
        Archipelago selectedArchipelago = board.getArchipelago(archiIndex);
        board.calculateInfluence(selectedArchipelago);

        /*
         * short form:   board.calculateInfluence(board.getArchipelago( setArchiIndex() ));
         */
    }

    //TODO: controller must intervene to set this variable
    public int setArchiIndex() {
        int index = 0; //controller should ask the user for the input
        return index;
    }
}