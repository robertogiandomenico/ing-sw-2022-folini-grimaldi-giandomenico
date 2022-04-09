package it.polimi.ingsw.model;

public class GrannyGrassEffect implements Effect {
    private int noEntryTiles = 4;

    public void applyEffect(Board board) {
        if(noEntryTiles > 0 && noEntryTiles <= 4) {
            int archiIndex = setArchiIndex();
            board.getArchipelago(archiIndex).setNoEntryTile(true);
            noEntryTiles--;
        } else {
            //print("no more tiles available, impossible to activate the effect")
        }
    }

    public void putBackTile() {
        noEntryTiles++;
    }

    //TODO: controller must intervene to set this variable
    public int setArchiIndex() {
        int index = 0; //controller should ask the user for the input
        return index;
    }

}