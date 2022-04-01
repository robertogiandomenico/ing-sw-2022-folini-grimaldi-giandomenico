package it.polimi.ingsw.model;

public class GrannyGrassEffect implements Effect {
    private int noEntryTiles = 4;

    public void applyEffect() {
        // TODO: implement effect
    }

    public void putBackTile() {
        noEntryTiles++;
    }

}