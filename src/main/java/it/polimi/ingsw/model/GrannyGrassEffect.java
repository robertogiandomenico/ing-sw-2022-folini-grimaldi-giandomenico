package it.polimi.ingsw.model;

public class GrannyGrassEffect implements Effect {
    private int noEntryTiles = 4;

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        if(noEntryTiles > 0 && noEntryTiles <= 4) {
            board.getArchipelago(archiIndex).setNoEntryTile(true);
            noEntryTiles--;
        } else {
            //print("no more tiles available, impossible to activate the effect")
        }
    }

    public void putBackTile() {
        noEntryTiles++;
    }

}