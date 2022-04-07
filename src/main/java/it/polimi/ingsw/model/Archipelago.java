package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;

public class Archipelago {
    private final List<Island> islands;
    private boolean motherNature;
    private boolean noEntryTile;
    private TeamColor towerColor;

    public Archipelago(Student student, boolean motherNature){
        islands = new ArrayList<Island>();
        createFirstIsland(student);
        this.motherNature = motherNature;
        noEntryTile = false;
    }

    private void createFirstIsland(Student student){
        Island island = new Island(student);
        addIsland(island);
    }

    public void addIsland(Island island){
        islands.add(island);
    }

    public int getTotalStudents(Color color){
        int count=0;
        for(Island island : islands)
            count+=island.getStudentNumber(color);
        return count;
    }

    public int countIslands() {
          return islands.size();
    }

    public void setMotherNature(boolean motherNature){
        this.motherNature = motherNature;
    }

    public boolean isMNPresent(){
        return motherNature;
    }

    public void setNoEntryTile(boolean noEntryTile){
        this.noEntryTile = noEntryTile;
    }

    public boolean isNoEntryTilePresent(){
        return noEntryTile;
    }

    public void setTowerColor(TeamColor towerColor){
        this.towerColor = towerColor;
    }

    public TeamColor getTowerColor(){
        return towerColor;
    }
}