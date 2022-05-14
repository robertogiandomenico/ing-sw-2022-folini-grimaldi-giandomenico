package it.polimi.ingsw.view.utilities.lightclasses;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.GrannyGrassEffect;
import it.polimi.ingsw.model.effects.MonkEffect;
import it.polimi.ingsw.model.effects.SpoiledPrincessEffect;
import it.polimi.ingsw.model.effects.JesterEffect;

public class LightCharacter {
    private int cost;
    private final String name;
    private int noEntryTiles = -1;
    private Student[] students;

    LightCharacter(GameCharacter c){
        this.cost = c.getCost();
        this.name = c.getName();

        switch (this.name){
            case "GrannyGrass":
                this.noEntryTiles = ((GrannyGrassEffect)c.getEffect()).getNoEntryTiles();
                break;
            case "SpoiledPrincess":
                this.students = ((SpoiledPrincessEffect)c.getEffect()).getStudents();
                break;
            case "Monk":
                this.students = ((MonkEffect)c.getEffect()).getStudents();
                break;
            case "Jester":
                this.students = ((JesterEffect)c.getEffect()).getStudents();
                break;
            default: break;
        }
    }


    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public int getNoEntryTiles() {
        return noEntryTiles;
    }

    public Student[] getStudents() {
        return students;
    }
}
