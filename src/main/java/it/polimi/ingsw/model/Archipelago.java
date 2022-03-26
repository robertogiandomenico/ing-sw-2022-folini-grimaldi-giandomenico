package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;

public class Archipelago {
    private List<Island> islands;
    private boolean motherNature;
    private boolean noEntryTile;
    private TeamColor towerColor;

    public Archipelago(List<Student> students, boolean motherNature){
        createFirstIsland(students);
        this.motherNature = motherNature;
        noEntryTile = false;
        towerColor = null;
    }

    public void addIsland(Island island){
        islands.add(island);
    }

    private void createFirstIsland(List<Student> students){
        islands = new ArrayList<Island>();
        Island island = new Island(students);
        addIsland(island);
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

    public int getTotalStudents(Color color){
        int count=0;
        for(Island island : islands) count+=island.getStudentNumber(color);
        return count;
    }

}

